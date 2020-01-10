package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.lang.Math;

public class Account {

    public static final byte CHECKING = 0;
    public static final byte SAVINGS = 1;
    public static final byte MAXI_SAVINGS = 2;

    private final byte accountType; // The account type (checking, savings or maxi savings)
    public List<Transaction> transactions; // List of transactions that have taken place in the account


    public Account(byte accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        deposit(amount, LocalDate.now());
    }

    public void withdraw(double amount) {
        withdraw(amount, LocalDate.now());
    }

    public void deposit(double amount, LocalDate date) {
        // Deposit (add) an amount of money to the account, date only to be used for testing
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        this.transactions.add(new Transaction(amount, date));
    }

    public void withdraw(double amount, LocalDate date) {
        // Withdraw (take out) an amount of money from the account, date only to be used for testing
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        this.transactions.add(new Transaction(-amount, date));
    }

    private BigDecimal getInterestRate(double currentSum, int daysLastWithdrawal) {
        // Returns the interest rate appropriate to the conditions and account type.
        BigDecimal interestRate = new BigDecimal("0");
        switch(this.accountType){
            case CHECKING:
                interestRate = new BigDecimal(1.001);
                break;
            case SAVINGS:
                if (currentSum <= 1000) {
                    interestRate = new BigDecimal(1.001);
                } else {
                    interestRate = new BigDecimal(1.002);
                }
                break;
            case MAXI_SAVINGS:
                if (daysLastWithdrawal <= 10) {
                    interestRate = new BigDecimal(1.001);
                } else {
                    interestRate = new BigDecimal(1.05);
                }
                break;
        }
        return interestRate;

    }

    private String getNumberOfDaysInYear(LocalDate date) {
        /* To be used in case current year is a leap year. If leap years were not taken into account
           bank would have to pay 0.27% (of the set interest rate) more in a year. */
        if (date.isLeapYear())
            return "366";
        return "365";
    }

    public double getBalance(){
        return getBalance(LocalDate.now());
    }

    public double getBalance(LocalDate date){
        /* Account starts at $0, interests are calculated from the moment there's funds in the account, that
          is when there's a first fund deposit. Since interests are compounded and accrued on a daily basis,
          for each day there needs to be an update of the interests added to the total sum.
          We also assume that interests are paid at the end of the day (at midnight for example).
          The date parameter is only to be used for testing purposes. In a normal scenario, the date would be
          the current date.*/

        // If date is before the latest transaction, the function will not work properly.
        if (date.isBefore(this.transactions.get(this.transactions.size() - 1).transactionDate))
            throw new IllegalArgumentException("date cannot be before last transaction.");

        // Initializing parameters
        LocalDate calcDate = this.transactions.get(0).transactionDate;
        double totalAmount = 0;
        int i = 0;
        int daysLastWithdrawal = 0;

        BigDecimal interestRate;
        String numOfDaysInYear;

        // In case there were multiple transactions the same day.
        while (i < this.transactions.size() && calcDate.isEqual(this.transactions.get(i).transactionDate)) {
            totalAmount += this.transactions.get(i).amount;
            i++;
        }

        // Going through all of the account's transactions.
        while ( i < this.transactions.size()) {
            while (calcDate.isBefore(this.transactions.get(i).transactionDate)){
                interestRate = getInterestRate(totalAmount, daysLastWithdrawal);
                numOfDaysInYear = getNumberOfDaysInYear(calcDate);
                BigDecimal exponent = new BigDecimal("1")
                        .divide(new BigDecimal(numOfDaysInYear), 10, RoundingMode.HALF_EVEN);
                totalAmount *= Math.pow(interestRate.doubleValue(), exponent.doubleValue());
                calcDate = calcDate.plusDays(1);
                daysLastWithdrawal ++;
            }
            while (i < this.transactions.size() && calcDate.isEqual(transactions.get(i).transactionDate)) {
                totalAmount += this.transactions.get(i).amount;
                if (this.transactions.get(i).amount < 0) daysLastWithdrawal = 0;
                i++;
            }
            interestRate = getInterestRate(totalAmount, daysLastWithdrawal);
            numOfDaysInYear = getNumberOfDaysInYear(calcDate);
            BigDecimal exponent = new BigDecimal("1")
                    .divide(new BigDecimal(numOfDaysInYear), 10, RoundingMode.HALF_EVEN);
            totalAmount *= Math.pow(interestRate.doubleValue(), exponent.doubleValue());
            calcDate = calcDate.plusDays(1);
            daysLastWithdrawal ++;
        }
        // Once all transactions have been gone through to the latest, we can then calculate interests until the date
        while (calcDate.isBefore(date)) {
            interestRate = getInterestRate(totalAmount, daysLastWithdrawal);
            numOfDaysInYear = getNumberOfDaysInYear(calcDate);
            BigDecimal exponent = new BigDecimal("1")
                    .divide(new BigDecimal(numOfDaysInYear), 10, RoundingMode.HALF_EVEN);
            totalAmount *= Math.pow(interestRate.doubleValue(), exponent.doubleValue());
            calcDate = calcDate.plusDays(1);
            daysLastWithdrawal ++;
        }

        return totalAmount;
    }

    public double interestEarned() {
        return interestEarned(LocalDate.now());
    }

    public double interestEarned(LocalDate date) {
        // Calculates the amount of interest earned on that account.
        double balance = getBalance(date);
        for (Transaction transaction : this.transactions) {
            balance -= transaction.amount;
        }
        return balance;
    }

    public int getAccountType() {
        // Returns the account type since account type is private.
        return this.accountType;
    }

}

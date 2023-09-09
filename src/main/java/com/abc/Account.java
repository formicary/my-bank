package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Represents a bank account with various types (CHECKING, SAVINGS,
 * MAXI_SAVINGS).
 */
public class Account {

    // Account types
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    // Interest rates
    public static final double CHECKING_INTEREST_RATE = 0.001;
    public static final double SAVINGS_LOW_INTEREST_RATE = 0.001;
    public static final double SAVINGS_HIGH_INTEREST_RATE = 0.002;
    public static final double MAXI_SAVINGS_LOW_INTEREST_RATE = 0.001;
    public static final double MAXI_SAVINGS_HIGH_INTEREST_RATE = 0.05;

    // Interest rate thresholds
    public static final double SAVINGS_THRESHOLD = 1000.0;
    public static final double MAXI_SAVINGS_FIRST_THRESHOLD = 1000.0;
    public static final double MAXI_SAVINGS_SECOND_THRESHOLD = 1000.0;

    // Number of days for Maxi-Savings interest rate calculation
    public static final int MAXI_SAVINGS_WITHDRAWAL_DAYS = 10;

    private final int accountType;
    private double balance;
    private Date lastWithdrawalDate;
    public List<Transaction> transactions;

    /**
     * Constructs an Account with the specified account type.
     *
     * @param accountType The type of the account (CHECKING, SAVINGS, MAXI_SAVINGS).
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.0;
        this.lastWithdrawalDate = null;
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param amount The amount to deposit. Must be greater than zero.
     * @throws IllegalArgumentException if the amount is not greater than zero.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    /**
     * Withdraws the specified amount from the account.
     *
     * @param amount The amount to withdraw. Must be greater than zero.
     * @throws IllegalArgumentException if the amount is not greater than zero.
     */
    public void withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
            lastWithdrawalDate = DateProvider.getInstance().now();
        }
    }

    /**
     * Calculates the interest earned on the account based on its type and balance.
     *
     * @return The interest earned.
     */
    public double interestEarned() {
        double balance = getBalance();
        double annualInterest = 0.0;

        switch (accountType) {
            case CHECKING:
                annualInterest = balance * CHECKING_INTEREST_RATE;
                break;
            case SAVINGS:
                annualInterest = calculateSavingsInterest(balance);
                break;
            case MAXI_SAVINGS:
                annualInterest = calculateMaxiSavingsInterest(balance);
                break;
            default:
                annualInterest = balance * CHECKING_INTEREST_RATE; // for unknown account types
        }

        return annualInterest;
    }

    /**
     * Calculates the sum of all transactions associated with the account.
     *
     * @return The sum of all transactions.
     */
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    /**
     * Gets the account type.
     *
     * @return The account type (CHECKING, SAVINGS, MAXI_SAVINGS).
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * Gets the current balance of the account.
     *
     * @return The account balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Calculates the daily interest rate for Savings accounts based on the balance.
     *
     * @param balance The current balance of the account.
     * @return The calculated daily interest rate.
     */
    private double calculateSavingsInterest(double balance) {
        if (balance <= SAVINGS_THRESHOLD) {
            return balance * SAVINGS_LOW_INTEREST_RATE;
        } else {
            double interest = SAVINGS_THRESHOLD * SAVINGS_LOW_INTEREST_RATE; // Interest on the first $1,000
            interest += (balance - SAVINGS_THRESHOLD) * SAVINGS_HIGH_INTEREST_RATE; // Interest on the amount over
                                                                                    // $1,000
            return interest;
        }
    }

    /**
     * Calculates the daily interest rate for Maxi-Savings accounts based on
     * withdrawal
     * history.
     *
     * @param balance The current balance of the account.
     * @return The calculated daily interest rate.
     */
    private double calculateMaxiSavingsInterest(double balance) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateProvider.getInstance().now());
    
        // Set the calendar to x days ago
        calendar.add(Calendar.DAY_OF_YEAR, -MAXI_SAVINGS_WITHDRAWAL_DAYS);
    
        double dailyRate;
        if (lastWithdrawalDate == null || lastWithdrawalDate.before(calendar.getTime())) {
            // Return the higher interest rate if there were no withdrawals in the past x days
            dailyRate = MAXI_SAVINGS_HIGH_INTEREST_RATE / getDaysInYear();
        } else {
            // Return the lower interest rate if there were withdrawals in the past x days
            dailyRate = MAXI_SAVINGS_LOW_INTEREST_RATE / getDaysInYear();
        }
    
        // Calculate compound interest over a year
        double annualInterest = balance * (Math.pow(1 + dailyRate, getDaysInYear()) - 1);
        return annualInterest;
    }
    

    /**
     * Gets the number of days in a year, considering leap years.
     *
     * @return The number of days in a year.
     */
    private int getDaysInYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateProvider.getInstance().now());
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }
}

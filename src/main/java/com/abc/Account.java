package com.abc;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

enum AccountType {
    CHECKING,
    SAVINGS,
    MAXI_SAVINGS
}

enum CompoundInterestPeriod {
    DAILY,
    WEEKLY
}

public class Account {

    private AccountType accountType;
    private List<Transaction> transactions;

    private double balance;
    private Date latestAccrueDate = null;

    private double interestEarned;
    private CompoundInterestPeriod interestPeriod;

    private boolean firstThousand = true;

    //Instantiate a new Account with Daily compound interest rate
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        interestPeriod = CompoundInterestPeriod.DAILY;
    }

    //Instantiate a new Account with the specified compound interest period
    public Account(AccountType accountType, CompoundInterestPeriod interestPeriod) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.interestPeriod = interestPeriod;
    }

    //Balance getter
    public double getBalance(Date date) {
        updateInterest(date);
        return balance;
    }

    //Getter for the transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    //Deposit money into the account on the given date. The date should new Date.now() but for testing purposes
    //and flexibility the date can be specified
    public void deposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            //Date now = DateProvider.getInstance().now();

            //Update the earned compound interest and balance so far
            updateInterest(date);

            //Add the new amount to the balance and save it in a transaction
            balance += amount;
            transactions.add(new Transaction(amount,date));
        }
    }

    //Withdraw money from the account on the given date. The date should new Date.now() but for testing purposes
    //and flexibility the date can be specified.
    //Allows for negative balance
    public void withdraw(double amount,  Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            //Date now = DateProvider.getInstance().now();

            //Update the earned compound interest and balance so far
            updateInterest(date);

            //Subtract the amount from the balance and save it in a transaction
            balance -= amount;
            transactions.add(new Transaction(-amount, date));
        }
    }

    //Check if there are any withdrawals in the specified time period
    public Date getLastWithdrawalDate(Date start, Date end){
        Date withdrawalDate = null;
        boolean hasWithdrawals = false;
        for(Transaction t : transactions){

            if(t.getTransactionDate().before(start) || t.getTransactionDate().after(end)){
                break;
            }

            if(t.getAmount() < 0){
                withdrawalDate = t.getTransactionDate();
                break;
            }

        }

        return withdrawalDate;
    }

    //Return the earned compound interest until the date specified.
    //This is for testing, when deployed, it should be Date.now()
    public double getCompoundInterest(Date date){
        updateInterest(date);
        return interestEarned;
    }

    //Calculate the simple interest earned for the given number of years
    public double simpleInterestEarned(int years) {

        //No interest if balance is negative
        if(balance < 0){
            return 0;
        }
        double interest = 0.0;
        switch(accountType){
            case CHECKING:
                interest = balance * 0.001;
                break;
            case SAVINGS:
                if (balance <= 1000)
                    interest = balance * 0.001;
                else
                    interest = 1 + (balance-1000) * 0.002;
                break;
            case MAXI_SAVINGS:
                //Check if any withdrawals in the past 10 days
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -10);
                boolean hasWithdrawals = getLastWithdrawalDate(cal.getTime(), DateProvider.getInstance().now()) != null;

                if(hasWithdrawals)
                    interest = balance * 0.001;
                else
                    interest = balance * 0.05;
                break;
        }

        return interest * years;
    }

    //Sum the amounts of all the transactions
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    //Get the account type
    public AccountType getAccountType() {
        return accountType;
    }

    //Update the interest earned between the given date and the last date it has been updated
    private void updateInterest(Date date){

        //Don't update if the balance is negative or there is no previous accrue date
        if(balance <= 0 || latestAccrueDate == null){
            latestAccrueDate = date;
            return;
        }

        //Check that the given date is valid
        if (date.before(latestAccrueDate))
            throw new DateTimeException("The specified date cannot be before the last interest accrue date!");

        //Get the time difference between the previous date and the current date
        long diff = date.getTime() - latestAccrueDate.getTime();

        long denominator = 0;

        //Calculate the number of days or weeks between the two dates
        long diffPeriod = 0;
        int n = 0;
        switch (interestPeriod){
            case DAILY:
                denominator = (24 * 60 * 60 * 1000);
                n = 365;
                break;
            case WEEKLY:
                denominator = (7 * 24 * 60 * 60 * 1000);
                n = 52;
                break;
        }

        diffPeriod = diff / denominator;

        //Calculate the interest earned on the balance
        double balanceWithNewInterest = 0.0;
        switch (accountType){
            case CHECKING:
                balanceWithNewInterest = balance * Math.pow(1 + 0.001 / n, diffPeriod);
                break;
            case SAVINGS:
                if (balance <= 1000 && firstThousand)
                    balanceWithNewInterest = balance * Math.pow(1 + 0.001 / n, diffPeriod);
                else {
                    if(firstThousand){
                        balanceWithNewInterest = 1000 * Math.pow(1 + 0.001 / n, diffPeriod);
                        balanceWithNewInterest += (balance - 1000) * Math.pow(1 + 0.002 / n, diffPeriod);
                    }
                    else {
                        balanceWithNewInterest = balance * Math.pow(1 + 0.002 / n, diffPeriod);
                    }

                    firstThousand = false;
                }
                break;
            case MAXI_SAVINGS:

                //Check if any withdrawals in the period for calculating the interest, the interest for those 10 days is 0.1% and then it recovers to 5%
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -(int)diffPeriod);
                Date lastWithdrawalDate = getLastWithdrawalDate(cal.getTime(), DateProvider.getInstance().now());

                if(lastWithdrawalDate == null){
                    balanceWithNewInterest = balance * Math.pow(1 + 0.05 / n, diffPeriod);
                }
                else {

                    double maxPeriodWithLowInterest = 0;
                    if(interestPeriod == CompoundInterestPeriod.DAILY){
                        maxPeriodWithLowInterest = 10;
                    }
                    else if(interestPeriod == CompoundInterestPeriod.WEEKLY){
                        maxPeriodWithLowInterest = 10/7d;
                    }

                    //Time diff between the latest time the interst has been updated and the last withdrawal time
                    long withdrawalDiff = (latestAccrueDate.getTime() - lastWithdrawalDate.getTime()) / denominator;

                    if (withdrawalDiff > maxPeriodWithLowInterest) {
                        balanceWithNewInterest = balance * Math.pow(1 + 0.05 / n, diffPeriod);
                    } else {
                        //Get the number of days or weeks in this period that will have the lower interest
                        double periodWithLowInterest = maxPeriodWithLowInterest - withdrawalDiff;

                        //Calculate teh interest for the days/weeks with low interest
                        balanceWithNewInterest = balance * Math.pow(1 + 0.001 / n, periodWithLowInterest);

                        //Calculate the normal interest for the rest of the days/weeks
                        balanceWithNewInterest = balanceWithNewInterest * Math.pow(1 + 0.05 / n, diffPeriod - periodWithLowInterest);
                    }

                }
                break;
        }

        //Add the new amount to the total interest earned
        interestEarned += balanceWithNewInterest - balance;

        //Update the new balance
        balance = balanceWithNewInterest;

        //Save the current date as the new accrue date
        latestAccrueDate = date;
    }

}

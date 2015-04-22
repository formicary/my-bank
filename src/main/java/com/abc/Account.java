package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        double amountInAccount = sumTransactions(); //get total amount in account
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amountInAccount < amount) {
            throw new IllegalArgumentException("amount must be less than total amount in account");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    //transfer between accounts
    public void transfer(Account to, double amount) {
        withdraw(amount);
        to.deposit(amount);
    }

    public double interestEarned() {
        double amount = 0.0; //counter for total amount
        double totalInterest = 0.0; //counter for total interest

        Date prevDate = null;
        for (Transaction t : transactions) { //loop through every transaction in account
            int numDaysBetween = 0;
            double interest; //counter for this steps current interest
            if (prevDate != null) { //get days between last transaction and this transaction
                numDaysBetween = getDaysBetween(t.transactionDate, prevDate);
            }
            prevDate = t.transactionDate;

            //Get current steps interest, create a new total amount with compounded interest
            interest = interestRate(amount, numDaysBetween);
            amount += interest + t.amount;
            totalInterest += interest;

        }

        //Get extra interest from last transaction to current time
        int numDaysPresent = getDaysBetween(Calendar.getInstance().getTime(), transactions.get(transactions.size() - 1).transactionDate);
        totalInterest += interestRate(amount, numDaysPresent);
        return totalInterest;
    }

    public double interestRate(double amount, int days) {
        switch(accountType){
            case SAVINGS:
                //Rate of 0.1% for the first $1,000 then 0.2%
                if (amount <= 1000)
                    return compoundInterest(.001, days, amount);
                else
                    return compoundInterest(.001, days, 1000) + compoundInterest(.002, days, amount - 1000);
            case MAXI_SAVINGS:
                //Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
                if (checkIfTransactionsExist(10))
                    return compoundInterest(.001, days, amount);
                else
                    return compoundInterest(.05, days, amount);
            default:
                //Flat rate of 0.1%
                return compoundInterest(.001, days, amount);
        }
    }

    private double compoundInterest (double rate, int days, double amount) {
        //Returns just the interest gained from amount based on rate and days
        return amount * (Math.pow((1 + rate/365),days) - 1);
    }

    public int getDaysBetween(Date firstDate, Date secondDate){
        //Gets rounded down num days in between two dates
        return (int)( (firstDate.getTime() - secondDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    private boolean checkIfTransactionsExist(int periodDays) {
        //loop through all transactions, if transaction date in the past n days, return true if
        //exists, else false
        Calendar cal = Calendar.getInstance(); //get current datetime to manipulate
        cal.add(Calendar.DATE, -periodDays); //subtract the period to check in
        Date minDate = cal.getTime(); //assign new minimum date to query
        for (Transaction t : transactions) {
            if (minDate.after(t.transactionDate)) {
                return false;
            }
        }
        return true;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}

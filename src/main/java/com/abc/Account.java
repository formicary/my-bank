package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class Account {

    private AccountType accountType;
    private List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
    * Sums the amounts of every transaction in the account to get the balance.
    *
    * @return The net amount that has been transacted.
    */
    public double getBalance() {
        double balance = 0.0d;
        for (Transaction t : transactions)
            balance += t.getAmount();
        return balance;
    }

    /**
    * Gets the type of the account.
    *
    * @return The account type: Checking, Savings or Maxi Savings.
    */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
    * Gets the transactions made by the account.
    *
    * @return The list of transactions.
    */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
    * Deposits an amount onto the account.
    *
    * @param amount The amount to be deposited.
    */
    public void deposit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero.");
        else
            transactions.add(new Transaction(amount));
    }

    /**
    * Withdraws an amount from the account.
    *
    * @param amount The amount to be withdrawn.
    */
    public void withdraw(double amount) {
        double balance = getBalance();
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero.");
        else if (balance < amount)
            throw new IllegalArgumentException("Not enough funds for withdrawal.");
        else
            transactions.add(new Transaction(-amount));
    }

    /**
    * Calculates the interest earned by the account, depending on the account type.
    *
    * @return The amount earned from the interest rate.
    */
    public double interestEarned() {
        Transaction prevTransaction = transactions.get(0);
        double balance = prevTransaction.getAmount();
        double interest = 0.0d;

        // Accrue interest for every day since the first transaction
        for (int i = 1; i < transactions.size(); i++) {
            int daysInBetween = daysInBetween(prevTransaction.getDate(), transactions.get(i).getDate());

            switch(accountType) {

                case SAVINGS:
                    // Rate of 0.1% for the first $1,000 and then 0.2%
                    if (balance <= 1000)
                        interest += balance * 0.001 * (daysInBetween / 365);
                    else
                        interest += 1 + (balance-1000) * 0.002 * (daysInBetween / 365);
                    break;

                case MAXI_SAVINGS:
                    // Rate of 0.1% if there was a withdrawal in the last 10 days, otherwise 5%
                    if (prevTransaction.getAmount() < 0 && daysInBetween < 10)
                        interest += balance * 0.001 * (daysInBetween / 365);
                    else
                        interest += balance * 0.5 * (daysInBetween / 365);
                    break;
            
                default:
                    // Flat rate of 1%
                    interest += balance * 0.001 * (daysInBetween / 365);
                }

            balance += transactions.get(i).getAmount();
            prevTransaction = transactions.get(i);
        }

        // Also accrue the interest since the last transaction
        Date now = Calendar.getInstance().getTime();
        int daysSinceLast = daysInBetween(prevTransaction.getDate(), now);
        
        switch(accountType) {

            case SAVINGS:
                // Rate of 0.1% for the first $1,000 and then 0.2%
                if (balance <= 1000)
                    interest += balance * 0.001 * (daysSinceLast / 365);
                else
                    interest += 1 + (balance-1000) * 0.002 * (daysSinceLast / 365);
                break;

            case MAXI_SAVINGS:
                // Rate of 0.1% if there was a withdrawal in the last 10 days, otherwise 5%
                if (prevTransaction.getAmount() < 0 && daysSinceLast < 10)
                    interest += balance * 0.001 * (daysSinceLast / 365);
                else
                    interest += balance * 0.5 * (daysSinceLast / 365);
                break;
        
            default:
                // Flat rate of 1%
                interest += balance * 0.001 * (daysSinceLast / 365);
        }

        return interest;
    }

    /**
    * Gets the number of days between two dates.
    *
    * @param firstDate The first date.
    * @param secondDate The date to be compared with the first one.
    * @return The time difference between the two dates, given in days.
    */
    public int daysInBetween(Date firstDate, Date secondDate) {
        long diff_ms = secondDate.getTime() - firstDate.getTime();  // Difference in milliseconds
        long diff_days = diff_ms / (1000 * 60 * 60 * 24);  // Convert from milliseconds to days
        return (int) diff_days;
    }

}

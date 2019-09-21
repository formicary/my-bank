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
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero.");
        else
            transactions.add(new Transaction(-amount));
    }

    /**
    * Calculates the interest earned by the account, depending on the account type.
    *
    * @return The amount earned from the interest rate.
    */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){

            case SAVINGS:
                // Rate of 0.1% for the first $1,000 and then 0.2%
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;

            case MAXI_SAVINGS:
                Date now = Calendar.getInstance().getTime();
                for (int i = transactions.size(); i>=0; i--) {
                    // If there has been a withdrawal within the last 10 days, the rate is 0.1%
                    Transaction t = transactions[i];
                    if (t.getAmount() < 0 && daysBetween(t.getTransactionDate(), now) < 10)
                        return amount * 0.001;
                }
                return amount * 0.5;

            default:
                return amount * 0.001;
        }
    }

    /**
    * Sums the amounts of every transaction in the account.
    *
    * @return The net amount that has been transacted.
    */
    public double sumTransactions() {
        double amount = 0.0d;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
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
    * Gets the number of days between two dates.
    *
    * @param firstDate The first date.
    * @param secondData The date to be compared with the first one.
    * @return The time difference between the two dates, given in days.
    */
    public AccountType daysBetween(Date firstDate, Date secondDate) {
        long diff_ms = secondDate.getTime() - firstDate.getTime();
        return diff_ms / (1000 * 60 * 60 * 24);  // Convert from milliseconds to days
    }

}

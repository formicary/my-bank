package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the object for the Account.
 * It contains an ArrayList of Transactions which describe the activity inside the account.
 * Functions for deposit and withdraw add new transactions.
 * @author Matthew Howard - <a href="mailto:m.o.howard@outlook.com">m.o.howard@outlook.com</a>
 */

public class Account {
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private double balance = 0.0;

    private final int accountType;
    public List < Transaction > transactions;

    /**
     * The constructor for an Account. It takes an integer representing which type of Account it should be.
     * @param accountType An integer representing one of {@value #CHECKING} ,{@value #SAVINGS}  or {@value #MAXI_SAVINGS}.
     *                    Any other value will default to {@value #CHECKING}.
     */
    public Account(int accountType) {
        if (accountType < 0 || accountType > 2) {
            accountType = CHECKING;
        }
        this.accountType = accountType;
        this.transactions = new ArrayList < Transaction > ();
    }

    /**
     * This function creates a new transaction adding a double representing money to the Account's balance..
     * The balance is also updated during this process.
     * @param amount    The amount of money to be deposited into the Account.
     *                  This value is not allowed to have more than 2 decimal places as it is impossible to have 0.001 dollars, for example.
     */
    public void deposit(double amount) {
        if (Double.toString(amount).length() - Double.toString(amount).indexOf('.') - 1 > 2) {
            throw new IllegalArgumentException("Transactions with more than 2 decimal points are disallowed");
        }
        if (amount < 0.01) {
            throw new IllegalArgumentException("amount must be at least 0.01");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    /**
     * This function creates a new transaction removing a given double representing money from the Account's balance.
     * It is not possible to use this function to go below 0.00 balance
     * @param amount The amount of money to be withdrawn from the Account.
     *                  This value is not allowed to have more than 2 decimal places as it is impossible to have 0.001 dollars, for example.
     */
    public void withdraw(double amount) {
        if (Double.toString(amount).length() - Double.toString(amount).indexOf('.') - 1 > 2) {
            throw new IllegalArgumentException("Transactions with more than 2 decimal points are disallowed");
        }
        if (amount < 0.01) {
            throw new IllegalArgumentException("amount must be at least 0.01");
        } else if (balance - amount < 0.0) {
            throw new IllegalArgumentException("insufficient funds");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    /**
     * This function returns the balance of this account.
     * @return A double representing the amount of money in this account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * This function calculates the amount of interest that will be generated per year given the type of account this is and the amount of money deposited.
     * @return A double representing the amount of interest that will be generated per year given the type of account and amount of money deposited
     */
    public double interestEarnedPerYear() {
        switch (accountType) {
            case SAVINGS:
                if (balance <= 1000) {
                    return balance * 0.001; //0.1% interest
                } else {
                    //1 is the 0.1% interest on the 1000 and then work out 0.2% times however much is after 1000
                    return 1 + ((balance - 1000) * 0.002);
                }
            case MAXI_SAVINGS:
                //check if the last transaction happened 10 days ago
                if (checkTenDaysBack()) {
                    return balance * 0.05; //5% interest
                } else {
                    return balance * 0.001; // 0.1% interest
                }

                //the default state of the account is CHECKING
            default:
                return balance * 0.001;
        }
    }

    /**
     * This function automatically calculates and adds the correct amount of interest for that day
     */
    public void addDailyInterest(){
        double interest = interestEarnedPerYear() /365;
        transactions.add(new Transaction(interest));
        balance += interest;
    }

    /**
     * This function returns true if the most recent transaction happened more than 10 days ago.
     * @return A boolean value representing whether or not the most recent transaction happened more than 10 days ago or not
     */
    public boolean checkTenDaysBack() {
        Date transactionDate = transactions.get(transactions.size() - 1).getTransactionDate();
        return DateProvider.getInstance().getTenDaysAgo().after(transactionDate);
    }

    /**
     * This function loops through the LinkedList of Transactions and returns the sum.
     * @return  A double representing the sum of all Transactions. This is effectively the same as the balance.
     */
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    /**
     * A function to get the Account's type
     * @return An integer representing one of {@value #CHECKING} ,{@value #SAVINGS}  or {@value #MAXI_SAVINGS}
     */
    public int getAccountType() {
        return accountType;
    }

}
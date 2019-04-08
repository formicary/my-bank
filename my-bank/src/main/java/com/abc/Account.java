package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import java.util.Calendar;


/**
* This class is a representation of a bank account that 
* a bank customer can open. There are 3 different types of accounts -
* Checking, Savings and Maxi-Savings account.
*
* An account has a type and a list with transactions.
*
*/
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    /**
     * Constructor for creating an Account object.
     * @param accountType 0,1 or 2, depending on the type of account that will be constructed
     */
    public Account(int type) {
        if (type == 0 || type == 1 || type == 2)
            this.accountType = type;
        else
            throw new IllegalArgumentException("Error! Account type argument must be 0, 1 or 2.");
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Returns the type of account.
     * @return the type of account
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * Returns the list with transactions for the account.
     * @return the list of transactions
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }


    /**
     * Deposits a set amount of money to the account.
     * @param amount the amount of money to be deposited
     */
    public void deposit(double amount) {
        if (amount <= 0) 
            throw new IllegalArgumentException("Error! You can not deposit a negative amount of money.");
        
        else 
            transactions.add(new Transaction(amount));
    }

    /**
     * Withdraws a set amount of money from the account.
     *  @param amount the amount of money to be withdrawn
     */
    public void withdraw(double amount) {
        if (amount <= 0) 
            throw new IllegalArgumentException("Error! You can not withdraw 0 or less dollars.");
        
        else if (amount > this.currentBalance()) 
            throw new IllegalArgumentException("Error! You can not withdraw more money than you have in the bank!");
        
        else 
            transactions.add(new Transaction(-amount));
    }

    /**
     * Calculates the total interest earned by the bank customer.
     * @return the amount of interest
     */
    public double interestEarned() {
        double amount = currentBalance();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (this.recentWithdrawals())
                    return amount * 0.001;
                return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    /**
     * Calculates the current balance of this account.
     * @return the current amount of money in the account
     */
    public double currentBalance() {
        double amount = 0.0;

        if (!(transactions.isEmpty())) 
            for (Transaction t: transactions) 
                amount += t.getAmount();
        return amount;
    }

    /**
     * Shows the transaction history of this account in a summarized fashion.
     * @return a string with the transaction summary
     */
    public String transactionSummary() {
        // Use a StringBuilder instead of regular string concatenation in order to avoid all the copying
        StringBuilder summaryBuilder = new StringBuilder();
        
        for (Transaction t: this.getTransactions()) 
            summaryBuilder.append("\n\nTransaction amount: " + t.getAmount() + "\nTransaction type: " + t.transactionType() + "\nTransaction date: " + t.getTransactionCalendar().getTime());
        
        return summaryBuilder.toString();
    }

    /**
     * Checks if there have been any withdrawals in the past 10 days. 
     * @return true or false depending on weather there have been recent withdrawals
     */
    public boolean recentWithdrawals() {
        // Get the time it was 10 days before the current time 
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-10);
        
        for (Transaction t: this.getTransactions()) 
            if (t.transactionType().equals("withdrawal") && t.getTransactionCalendar().compareTo(calendar) > 0) 
                return true;
        return false;
    }

}

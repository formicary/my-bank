package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to handle the accounts of a customer
 */
public class Account {

    //The different types of accounts
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private final String  accountName;
    private List<Transaction> transactions;     // A list containing all the transactions in relation to this account

    /**
     * Constructor for the account class
     * @param accountType indicate what kind of account is being created
     * @param accountName give the account a name
     */
    public Account(int accountType, String accountName) {
        this.accountType = accountType;
        this.accountName = accountName;
        this.transactions = new ArrayList<Transaction>();
    }


    /**
     * Function to deposit money into an account
     * @param amount the amount of money to be deposited into an account
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Function to withdraw money from an account
     * @param amount the amount of money to be withdraw from an account
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Function that calculates amount of interest is earned by an account
     * @return the interest earned by account
     */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (withdrawalDateComparison(retrieveLastWithdrawal(), 10))
                    return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    /**
     * Function that calculates total amount of money in an account
     * @return the total amount of money in an account
     */
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction transaction: transactions)
            amount += transaction.amount;
        return amount;
    }


    /**
     * Function that allows customer to transfer money from one of their accounts to another
     * @param customer the customer making the transfer
     * @param fromAccountName name of the account that the money is being transferred from
     * @param toAccountName name of the account that the money is being transferred to
     * @param amount the amount of money being transferred
     */
    public void transfer(Customer customer, String fromAccountName, String toAccountName, double amount){
        Account fromAccount = customer.retrieveAccount(fromAccountName);
        Account toAccount = customer.retrieveAccount(toAccountName);
        if(fromAccount.sumTransactions() < amount){
            throw new IllegalArgumentException("insufficient funds in account");
        }
        else {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
    }

    /**
     * Function that determines whether a withdrawal has been made between a certain period
     * @param transactionDate the date a withdrawal was made
     * @param days number of days to compare transaction to
     * @return boolean that checks if the account has a withdrawal in the given number of days
     */
    private boolean withdrawalDateComparison(Date transactionDate, int days){
        long currentMillis = new Date().getTime();
        long millisInDays = days * 24 * 60 * 60 * 1000;
        boolean result = transactionDate.getTime() < (currentMillis - millisInDays);
        return result;
    }

    /**
     * Function that find the last withdrawal made in an account
     * @return Date the last withdrawal was made
     */
    private Date retrieveLastWithdrawal(){
        double transactionAmount = 0;
        Transaction currentTransaction = null;
        int i = transactions.size()-1;
        while (transactionAmount >= 0){
            if(i >= 0){
                transactionAmount = transactions.get(i).getAmount();
                currentTransaction = transactions.get(i);
                i--;
            }
            else{
                break;
            }
        }
        if(transactionAmount < 0){
            return currentTransaction.getTransactionDate();
        }
        else{
            return new Date(0);
        }
    }

    /**
     * Getter for the account type
     * @return account type
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * Getter for the list of transactions
     * @return list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Getter for the account name
     * @return account name
     */
    public String getAccountName() {
        return accountName;
    }
}

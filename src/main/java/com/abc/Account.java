package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions; //records a list of transactions

    /**
     * Specify the type of account to create
     * @param accountType - Checking, Savings or MaxiSavings
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Create a new deposit transaction with the amount specified
     * @param amount - amount to deposit
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            getTransactions().add(new Transaction(amount));
        }
    }

    /**
     * Create a new transaction with the amount specified
     * @param amount - amount to withdraw 
     */
    public void withdraw(double amount) {
     if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
            getTransactions().add(new Transaction(-amount));
    }
    }

    /**
     * Calculate interest earned for the account
     * @return the amount of interest earned
     */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                //if no withdrawls in past 10 days 
                boolean withdrawalsPast10Days = transactionsCreatedPast10Days();
                
                if(withdrawalsPast10Days){
                    return amount * 0.001;
                }else{
                    return amount *0.05;
                }
            default:
                return amount * 0.001;
        }
    }
    
    
    /**
     * Method can be called daily to add the current interest to the account (in the form of a transaction)
     */
    public void addInterestToAccount(){
        deposit(interestEarned());
    }
    
    /**
     * Loop through all transactions. Check if any transactions have been submitted in the past 10 days.
     * @return 
     */
    public boolean transactionsCreatedPast10Days(){
        
        Date todaysDate = DateProvider.getInstance().now();
        boolean transactionsCreated = false;
        
        for(Transaction t : getTransactions()){
            Date checkDate = t.getTransactionDate();
            long diff = todaysDate.getTime() - checkDate.getTime();  
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            if(diff < 10){
                return true;
            }
        }
        
        return transactionsCreated;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    /**
     * Loop through all transactions and sum the total amount
     * @param checkAll -//TODO: 
     * @return 
     */
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: getTransactions())
            amount += t.amount;
        return amount;
    }

    /**
     * Get the account type
     * @return - integer representing account type
     */
    public int getAccountType() {
        return accountType;
    }
    
 
    /**
     * @return the transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    

}

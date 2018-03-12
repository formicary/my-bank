package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Account {

    private final int accountType; // 0 = Checking, 1 = Savings, 2 = Maxi
    private List<Transaction> transactions;
    private double balance;
    private double interestPaid;
    private Date lastWithdrawal;

    // accountType must be 0,1 or 2.
    public Account(int accountType) {
        if(accountType < 0 || accountType > 2){
            throw new IllegalArgumentException("Invalid account type.");
        }
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.0;
        this.interestPaid = 0.0;
        this.lastWithdrawal = null;
    }

    // Given account type, returns the name for the account
    public String accountTypeName(){
        if(accountType == 0){
            return "Checking Account";
        } else if(accountType == 1){
            return "Savings Account";
        } else{
            return "Maxi-Savings Account";
        }
    }

    // Deposit funds
    public void deposit(double amount){
          transactions.add(new Transaction(amount, "deposit"));
          balance += amount;
    }

    // Withdraw funds unless it exceeds balance
    public void withdraw(double amount){
        if(balance < amount){
            throw new IllegalArgumentException("You do not have the necessary funds.");
        } else{
            transactions.add(new Transaction(amount, "withdrawal"));
            balance -= amount;
            lastWithdrawal = new Date();
        }
    }
    // Return the value of a single days worth of interest based on account type
    public double interest(){
        switch(accountType) {
            case 0: // Checking
                return balance * 0.001;

            case 1: // Savings
                if (balance <= 1000) {
                    return balance * 0.001;
                }
                return 1 + (balance - 1000) * 0.002;

            default: // Maxi
                if((lastWithdrawal == null || (new Date().getTime() - lastWithdrawal.getTime())/(1000*60*60*24) > 10)){
                    /* Calculates the number of milliseconds between today and the last withdrawal.
                     * Then converts milliseconds to days by dividing by (1000*60*60*24).
                     * The number we're left with is the number of days since the last withdrawal)*/
                    return balance * 0.05;
                }
                else{
                    return balance * 0.001;
                }
        }
    }
    // Updates balance by paying compound interest over given number of days
    public void payInterest(int days){
        for(int day=0; day < days; day++){
            double amount = interest();
            balance += amount;
            interestPaid += amount;
        }
    }

    // Getters

    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestPaid() {
        return interestPaid;
    }
}

package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Abstract class for creating the three types of account
 */
public abstract class Account {

    public List<Transaction> transactions;

    protected int accountType = 0;
    private double accountBalance = 0.0;

    protected String accountTypeString;

    protected Customer owner;

    protected Date lastWithdrawal;

    public Account(Customer owner){
        this.transactions = new ArrayList<Transaction>();
        this.owner = owner;
    }

    // public int getAccountType(){ return accountType; }

    public String getAccountTypeString() { return this.accountTypeString; }

    public double getAccountBalance(){ return this.accountBalance; }

    // same process across accounts, so no need for repeated implementations via abstract class
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("error: amount must be greater than zero");
        } else {
            createNewTransactionRecord(amount, Transaction.DEPOSIT);
            this.addFunds(amount);
        }
    }

    // same process across accounts, so no need for repeated implementations via abstract class
    // can't go into overdraft on any account (even if in reality you can) for simplicity reasons
    public void withdraw(double amount) {

        if (amount <= 0.0) {

            throw new IllegalArgumentException("error: amount must be greater than zero");

        } else if(!this.hasSufficientFunds(amount)){

            throw new IllegalArgumentException("error: insufficient funds for withdrawal");

        } else {

            createNewTransactionRecord(-amount, Transaction.WITHDRAWAL);
            this.deductFunds(amount);
        }
    }

    protected void createNewTransactionRecord(double amount, int type){
        transactions.add(new Transaction(amount, type));
        if(type == Transaction.WITHDRAWAL) updateLatestWithdrawal();
    }

    // assumption is that the latest transaction made is also the most recent
    private void updateLatestWithdrawal() {
        this.lastWithdrawal = this.transactions.get(this.transactions.size() - 1).transactionDate;
    }


    protected String getAccountStatement(){
        String s = this.getAccountTypeString() + " Account\n";

        //Now total up all the transactions
        for (Transaction t : this.transactions) {
            s += "  " + t.getTypeString() + " " + CurrencyFormat.toDollars(t.amount) + "\n";
        }
        s += "Total " + CurrencyFormat.toDollars(this.getAccountBalance());
        return s;
    }

    // interest rate calculation is dependent on the type of account
    public abstract double interestEarnedAnnum();

    public boolean hasSufficientFunds(double amount){
        return (this.accountBalance - amount >= 0.00);
    }

    protected void addFunds(double amount){
        this.accountBalance += amount;
    }

    protected void deductFunds(double amount){
        this.accountBalance -= amount;
    }
}
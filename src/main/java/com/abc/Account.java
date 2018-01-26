package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the object for the Account.
 * It contains an ArrayList of Transactions which describe the activity inside the account.
 * Functions for deposit and withdraw add new transactions.
 * @author Matthew Howard
 */

public class Account {
    //TODO: change these to be enums
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private double balance = 0.0;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if(Double.toString(amount).length() - Double.toString(amount).indexOf('.') - 1 > 2){
            throw new IllegalArgumentException("Transactions with more than 2 decimal points are disallowed");
        }
        if (amount < 0.01) {
            throw new IllegalArgumentException("amount must be at least 0.01");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if(Double.toString(amount).length() - Double.toString(amount).indexOf('.') - 1 > 2){
            throw new IllegalArgumentException("Transactions with more than 2 decimal points are disallowed");
        }
        if (amount < 0.01) {
            throw new IllegalArgumentException("amount must be at least 0.01");
        } else if (balance-amount<0.0) {
            throw new IllegalArgumentException("insufficient funds");
        }else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    /* Matt is floating this idea about
    public double getBalance(){
        return balance;
    }

    public double getBalanceWithInterest(){
        return balance + interestEarned();
    }
    */

    public double interestEarned() {
        switch(accountType){
            case SAVINGS:
                if (balance <= 1000)
                    return balance * 0.001;
                else
                    return 1 + (balance-1000) * 0.002;
                //TODO: this came commented out, whats that about?
//            case SUPER_SAVINGS:
//                if (balance <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (balance <= 1000)
                    return balance * 0.02;
                if (balance <= 2000)
                    return 20 + (balance-1000) * 0.05;
                return 70 + (balance-2000) * 0.1;
            //the default state of the account is CHECKING however this isn't immediately clear
            default:
                return balance * 0.001;
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        //TODO: checkAll never used ??
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }



}

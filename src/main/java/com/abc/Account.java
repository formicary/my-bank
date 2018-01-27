package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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

    public double getBalance(){
        return balance;
    }

    public double interestEarned() {
        switch(accountType){
            case SAVINGS:
                if (balance <= 1000) {
                    return balance * 0.001;
                }
                else {
                    //1 is the 0.1% interest on the 1000 and then work out 0.2% times however much is after 1000
                    return 1 + ((balance - 1000) * 0.002);
                }
            case MAXI_SAVINGS:
                if (checkTenDaysBack()) {
                    return balance* 0.05;
                }else {
                    return balance * 0.001;
                }

            //the default state of the account is CHECKING
            default:
                return balance * 0.001;
        }
    }

    public boolean checkTenDaysBack(){
        Date transactionDate = transactions.get(transactions.size()-1).getTransactionDate();
        return DateProvider.getInstance().getTenDaysAgo().after(transactionDate);
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

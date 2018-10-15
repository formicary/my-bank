package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if(amount < sumTransactions()){
            if(amount <= 0){
                throw new IllegalArgumentException("amount must be greater than zero");
            }
            else{
                transactions.add(new Transaction(-amount));
            }
        }
        else{
            System.out.println("Not enough funds.");
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (!(new DateProvider().tenDayCheck(new DateProvider().now())))
                    return amount * 0.05;
                else
                    return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public Transaction getLastDeposit(){
        for(Transaction t: transactions)
            if (t.type==t.DEPOSIT){
                return t;
            }
        System.out.println("No deposits found");
        return null;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    public int getNumberOfTransactions() {
        return transactions.size();
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}

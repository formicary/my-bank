package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

    public enum Type{
      CHECKING, SAVINGS, MAXI_SAVINGS
    }

    private Type accountType;
    public List<Transaction> transactions;

    public Account(Type accountType) {
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
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                return (amount <= 1000 ? amount * (0.001/365) : 1 + (amount-1000) * (0.002/365));
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            /*case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;*/
            case MAXI_SAVINGS:
                boolean check = false;
                Date rightnow = DateProvider.getInstance().now();
                for (Transaction trans:transactions){
                    if(trans.amount < 0){
                        check = check || withinTenDays(rightnow, trans.timestamp());
                    }
                }
                return (check ? amount * (0.001/365) : amount * (0.05/365));
            default:
                return amount * (0.001/365);
        }
    }

    private boolean withinTenDays(Date current, Date then){
      return ((current.getTime() - then.getTime()) / (24*60*60*1000)) < 10;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public Type getAccountType() {
        return accountType;
    }

}

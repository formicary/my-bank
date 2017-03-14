package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private int daysSinceLastWithdraw;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        daysSinceLastWithdraw = 11;
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
            daysSinceLastWithdraw = 0;
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount < 0)
                    return 0;
                else if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (amount < 0)
                    return 0;
                else if (daysSinceLastWithdraw < 10)
                    return amount * 0.001;
                else
                    return amount * 0.05;
            default:
                if (amount <0)
                    return 0;
                else
                    return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions){
            amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    //Added Feature Change Maxi-Savings accounts 
    public void endOfDay(){
        daysSinceLastWithdraw++;
    }
    //Added Feature Change Maxi-Savings accounts 
    public int getDaysSinceLastWithdraw(){
        return daysSinceLastWithdraw;
    }
}

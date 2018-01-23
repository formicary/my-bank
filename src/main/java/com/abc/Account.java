package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    //types of accounts
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    //initialise variables for constructor
    private final int accountType;
    public List<Transaction> transactions;

    //constructor
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    //deposit an amount, throw error if amount is less than 0
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    //withdraw an amount, throw error if amount is less than 0
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    //work out the total interest earned for each type of account
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                double lastOne = transactions.get(transactions.size()-1).getAmount();
                long todayDate = DateProvider.getInstance().now().getTime();
                long transactionDate = transactions.get(transactions.size()-1).getDate().getTime();
                long diff = todayDate - transactionDate;
                long diffDays = diff / (24*60*60*1000);
                if(lastOne < 0 && diffDays <= 8.64e+8){
                    return amount * 0.001;                   
                } else {
                    return amount * 0.05;
                }
            default:
                return amount * 0.001;
        }
    }

    //if transactions exist, sum them up to reach current balance
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    //if transactions exist, sum them up
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    //get the account type
    public int getAccountType() {
        return accountType;
    }

}

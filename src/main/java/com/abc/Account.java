package com.abc;

import java.util.*;

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
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    //days used 360 for financial market
    public double interestEarned() {

        double amount = 0;
        double totalInterest = 0;
        Date now = DateProvider.getInstance().now();

        switch(accountType){

            case SAVINGS:

                for (int i = 0; i < transactions.size(); i++) {

                    amount += transactions.get(i).amount;

                    if (i == transactions.size() - 1) {
                        if (amount <= 1000) {
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), now) * (0.001 / 360);
                        } else {
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), now) * (0.002 / 360);
                        }
                    } else {
                        if (amount <= 1000) {
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()) * (0.001 / 360);
                        } else {
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()) * (0.002 / 360);
                        }
                    }
                }

                return totalInterest;

            case MAXI_SAVINGS:

                for (int i = 0; i < transactions.size(); i++) {

                    amount += transactions.get(i).amount;

                    if (i == transactions.size() - 1) {
                        if (DateProvider.getInstance().now().compareTo(transactions.get(i).getTransactionDatePlus10Days()) >= 0) {
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), now) * (0.05 / 360);
                        } else
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), now) * (0.001 / 360);
                    } else {
                        if (transactions.get(i).getTransactionDate().compareTo(transactions.get(i + 1).getTransactionDatePlus10Days()) < 0) {
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()) * (0.05 / 360);
                        } else
                            totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), transactions.get(i + 1).getTransactionDate()) * (0.001 / 360);
                    }
                }
                return totalInterest;

            default:

                for (int i = 0; i < transactions.size(); i++) {

                    amount += transactions.get(i).amount;
                    totalInterest += amount * daysBetween(transactions.get(i).getTransactionDate(), now) * (0.001 / 360);

                }
                return totalInterest;
        }
    }

    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }


    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }


    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }


}

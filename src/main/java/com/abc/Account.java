package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions; //make private, and only accessible from getter

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
        }
        else if(amount > this.sumTransactions()){       //additional check for invalid amount
            throw new IllegalArgumentException("insufficient funds");
        }
         else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();

        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                } else {
                    return 1 + (amount - 1000) * 0.002;
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:  //changing maxi_savings interest calculation
                Transaction lastWith = null;
                for (int i = 1; i < transactions.size(); i++) {
                    if (transactions.get(i).getAmount() < transactions.get(i - 1).getAmount()) {    //check for withdrawl
                        lastWith = transactions.get(i);
                    }
                }   //check for last withdrawl date by comparing balances

                if (lastWith == null) { //ASSUMPTION : if never withdrawn, assume that they interest days accumulate from account creation
                    lastWith = transactions.get(0);
                }

                if (this.daysBetweenNow(lastWith.getDate()) > 10) {
                    return amount * 0.05;
                } else {
                    return amount * 0.001;
                }

            /*  if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;*/  //old way of working out maxi account interest
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() { //remove checkAll argument not used
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    private long daysBetweenNow(Date a) {   //new method to calculate the days between transaction, and today - used for maxi savings
        Date today = new Date();
        long difference = (today.getTime() - a.getTime()) / 86400000;   //minus each date to get seconds, / by total seconds to get days
        return Math.abs(difference);
    }
    
    public List<Transaction> getTransactions(){ //getter for transactions, public to access only transactions list
        return transactions;
    }
}

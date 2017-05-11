package com.abc;

import java.util.ArrayList;
import java.util.Date;
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
        if (amount <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");
        else
            transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");
        else
            transactions.add(new Transaction(-amount));
    }

    //Interest earned is now performed daily instead of anually
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001/365;
                else
                    return 1000 * 0.001/365 + ((amount-1000) * 0.002/365);
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (hasWithdrawnLastTen())
                    return amount * 0.05/365;
                else
                    return amount * 0.001/365;
            default:
                return amount * 0.001/365;
        }
    }

//    public void addInterest(){
//        deposit(interestEarned());
//    }

    public double sumTransactions() {
        double amount = 0.0;
        for(Transaction t: transactions)
                amount += t.amount;
        return amount;
    }

    //Returns a boolean False if the withdrawal has been performed within last 10 days
    private boolean hasWithdrawnLastTen(){
        Date now = DateProvider.getInstance().now();
        for ( Transaction t: transactions)
            if (t.amount < 0 &
                    t.getTransactionDate().after(DateProvider.getInstance().tenDaysAgo()))
                return false;
        return true;
    }

//    private boolean checkIfHasTransaction(Transaction transaction) {
//            return transactions.contains(transaction);
//            amount += t.amount;
//        return amount;
//    }

    public int getAccountType() {
        return accountType;
    }

}

package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;


public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final String accountName;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(String accountName, int accountType) {
        this.accountName = accountName;
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
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (checkWithdrawInThePast(10))
                    return amount * 0.05;
                else
                    return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public boolean checkWithdrawInThePast(int day) {
        Date date = DateProvider.getInstance().now();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -10);
        for (Transaction t: transactions) {
            if (t.amount < 0 && calendar.getTime().compareTo(t.transactionDate) < 0) {
                return false;
            }
        }
        return true;
    }

    public String getAccountName() {return accountName;}
    public int getAccountType() {
        return accountType;
    }

}

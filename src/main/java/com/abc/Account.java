package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;



    private int accountBalance;
    private final int accountType;
    private List<Transaction> transactions;
    private Date lastWithdrawDate;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountBalance = 0;

    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposits must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, "deposit"));
            accountBalance += amount;
        }
    }

    public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Withdrawals must be greater than zero");
    } else {
        Transaction trans = new Transaction(-amount, "withdrawal");
        transactions.add(trans);
        lastWithdrawDate = trans.getTransactionDate();
        accountBalance -= amount;
    }

    }

    public void transfer(double amount){
        transactions.add(new Transaction(amount, "Transfer"));
        accountBalance += amount;
    }

    public double interestEarned() {
        double amount = accountBalance;
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
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }


    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


    public int getAccountBalance() {
        return accountBalance;
    }

    public Date getLastWithdrawDate() {
        return lastWithdrawDate;
    }
}

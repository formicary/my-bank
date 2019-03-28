package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private double balance;
    private final Date created_at;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.balance = 0;
        this.created_at = DateProvider.getInstance().now();
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, Transaction.DEPOSIT));
            this.balance += amount;
        }
    }

    public void withdraw(double amount, boolean isTransfer) {
        if (getCurrentBalance() > amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("amount must be greater than zero");
            } else {
                if (isTransfer) transactions.add(new Transaction(-amount, Transaction.TRANSFER));
                else transactions.add(new Transaction(-amount, Transaction.WITHDRAW));
                this.balance -= amount;
            }
        } else {
            throw new IllegalArgumentException("Account doesn't have sufficient funds");
        }
    }


    public double interestEarned(Boolean isShowForTheWholeYear) {
        double amount = sumTransactions();
        long accountLifeTime = Math.round(
                (DateProvider.getInstance().now().getTime() - this.created_at.getTime()) / (24*60*60*1000)
        );
        double result;
        switch(this.accountType) {

            case SAVINGS:

                if (amount <= 1000) result = amount * 0.001;
                else result = 1000*0.001 + (amount-1000) * 0.002;

                if (isShowForTheWholeYear) return result;
                else return result/365 * accountLifeTime;

//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;

            case MAXI_SAVINGS:
                if (checkLastWithdraw()) result = amount*0.05;
                else result = amount*0.001;

                if (isShowForTheWholeYear) return result;
                else return result/365 * accountLifeTime;

            default:
                result = amount * 0.001;
                if (isShowForTheWholeYear) return result;
                else return result/365 * accountLifeTime;
        }
    }

    public double sumTransactions() {

        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction transaction: transactions)
            amount += transaction.getTransactionAmount();
        return amount;
    }

    private boolean checkLastWithdraw() {
        Date lastWithdrawDate = null;
        long withdrawCheck = 0;
        int i = transactions.size()-1;

        while(lastWithdrawDate == null && i>=0) {
            if (transactions.get(i).getTransactionType() == Transaction.WITHDRAW) {
                lastWithdrawDate = transactions.get(i).getTransactionDate();
            }
            i--;
        }
        if (lastWithdrawDate != null) {
            withdrawCheck = (DateProvider.getInstance().now().getTime() - lastWithdrawDate.getTime()) / (24 * 60 * 60 * 1000);
            if (withdrawCheck > 10) return true;
        }
        return false;
    }


    public int getAccountType() {
        return accountType;
    }

    public double getCurrentBalance() {
        return this.balance;
    }

    public Date getTimeCreated() {
        return this.created_at;
    }


    public List<Transaction> getAllTransactions() {
        return this.transactions;
    }
}

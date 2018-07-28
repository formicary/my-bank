package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public int getAccountType() {
        return accountType;
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
//            case MAXI_SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
            case MAXI_SAVINGS:
                int lastWithdrawalIndex = 0;
                for(int t = transactions.size()-1; t >= 0; t--){
                    if (transactions.get(t).amount < 0){ // if transaction is a withdrawal
                        lastWithdrawalIndex = t;
                    }
                }
                Date now = DateProvider.getInstance().now();
                Date lastTransaction = transactions.get(lastWithdrawalIndex).transactionDate;
                // if there have been no withdrawals or it has been more than 10 days since last withdrawal
                if (lastWithdrawalIndex == 0 || getDaysSinceLastTransaction(lastTransaction, now, TimeUnit.DAYS) > 10)
                    return amount * 0.05;
                else
                    return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        if(transactions.size() == 0) { return 0.0; } //check if there have been transactions
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public static long getDaysSinceLastTransaction(Date lastTrans, Date now, TimeUnit timeUnit){
        long diffInDays = now.getTime() - lastTrans.getTime();
        return timeUnit.convert(diffInDays,TimeUnit.MILLISECONDS);
    }
//    public long getDaysSinceLastTransaction(Date lastTrans, Date now) {
//        return (now.getTime() - lastTrans.getTime()) / (1000 * 60 * 60 * 24);
//    }

}

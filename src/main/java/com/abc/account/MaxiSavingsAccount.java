package com.abc.account;

import com.abc.Transaction;
import com.abc.util.DateProvider;
import com.abc.util.IDateProvider;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account {
    private static final String PRETTY_ACC_NAME = "Maxi Savings Account";
    private IDateProvider dateProvider;

    public MaxiSavingsAccount(IDateProvider dateProvider) {
        super();
        this.dateProvider = dateProvider;
    }

    @Override
    public double interestEarned() {
        double interest;
        double amount = sumTransactions();

        long daysSinceLastWithdrawal = getDaysSinceLastWithdrawal();



        if (daysSinceLastWithdrawal > 10) {
            interest = amount * 0.05;
        } else {
            interest = amount * 0.001;
        }

        return interest;
    }

    private long getDaysSinceLastWithdrawal() {
        List<Transaction> withdrawals = new ArrayList<Transaction>();
        int numberOfDays = Integer.MAX_VALUE;

        for(Transaction transaction : this.transactions){
            if(transaction.getAmount() < 0){
                withdrawals.add(transaction);
            }
        }

        if(!withdrawals.isEmpty()){
            Collections.sort(withdrawals, new Comparator<Transaction>() {
                public int compare(Transaction transaction, Transaction t1) {
                    return -(transaction.getTransactionDate().compareTo(t1.getTransactionDate()));
                }
            });

            long difference = this.dateProvider.getCurrentDate().getTime() - withdrawals.get(0).getTransactionDate().getTime();
            return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        }
        return numberOfDays;
    }

    @Override
    public String getPrettyAccountType() {
        return PRETTY_ACC_NAME;
    }

}

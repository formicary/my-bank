package com.accenture.accounts;

import com.accenture.DateProvider;
import com.accenture.Transaction;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount(long accountNumber){
        super(accountNumber);
    }

    @Override
    public double interestEarned() {
        double summedTransactions, amount;
        summedTransactions = sumTransactions();
        amount = getInterestRate(DateProvider.getInstance().now())*summedTransactions;
        return amount;
    }


    @Override
    public String getAccountType(){
        return "Maxi Savings Account";
    }


    public double getInterestRate(Date now){
        int days;
        Date lastTransactionDate = lastWithdrawal();

        if(null == lastTransactionDate)
            return 0.05;
        days = getDateDifference(now,lastTransactionDate);
        if(days>10)
            return 0.05;
        return 0.01;


    }

    private int getDateDifference(Date now, Date lastTransaction){
        long diffInMs, diffInDays;
        diffInMs= now.getTime() - lastTransaction.getTime();
        diffInDays = (TimeUnit.DAYS).convert(diffInMs,TimeUnit.MILLISECONDS);
        return (int)diffInDays;
    }


    private Date lastWithdrawal(){
        Date lastWithdrawalDate = null;
        for(Transaction transaction : getTransactions()){
            if(transaction.getAmount()<0)
                lastWithdrawalDate = transaction.getTransactionDate();
        }
        return lastWithdrawalDate;
    }


}

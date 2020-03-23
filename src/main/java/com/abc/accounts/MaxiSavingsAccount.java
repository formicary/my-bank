package com.abc.accounts;

import com.abc.DateProvider;
import com.abc.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MaxiSavingsAccount extends Account {
    //Maxi-Savings accounts = rate 5% if no withdrawals in 10 days otherwise 0.1%

    public MaxiSavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public double calculateInterest(double amount, Date date) {
        double rate;

        if(noWithdrawalsIn10Days(date)){
            rate = 0.05/365;
        }
        else{
            rate = 0.001/365;
        }

        return rate;
    }

    private boolean noWithdrawalsIn10Days(Date currentDay){
        DateProvider dateProvider = new DateProvider();
        Date currentDate = dateProvider.now();
        double difference = 0;

        //System.out.println("current date: " + currentDate);
        for (Transaction transaction: this.transactions) {
            //System.out.println("Transaction date: " + transaction.getTransactionDate());
            difference = dateProvider.calculateDifferenceInDays(transaction.getTransactionDate(), currentDay, Locale.getDefault());

            if(difference >= 0 && difference <= 10){
                //System.out.println("difference = " +  difference);
                return false;
            }
        }

        //System.out.println("difference = " +  difference);
        return true;
    }

    @Override
    public String getType() {
        return "Maxi Savings Account\n";
    }
}

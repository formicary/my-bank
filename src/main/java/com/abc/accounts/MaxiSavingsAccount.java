package com.abc.accounts;

import com.abc.DateProvider;
import com.abc.Transaction;
import com.abc.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MaxiSavingsAccount extends Account {
    //Maxi-Savings accounts = rate 2% for first 1000, 5% for next 1000, then 10%

    public MaxiSavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
        this.setRate(0.02);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        double finalAmount = 0;
        double rate = 0.02;

        if(Utils.isGraterThen1000(amount)){
            //first 1000 with rate 0.02

            finalAmount += 1000 * 0.02;
            amount -= 1000;

            if(Utils.isGraterThen1000(amount)){
                //second 1000 with rate 0.05 if no withdrawals in 10 days, otherwise 0.001
                if(noWithdrawalsIn10Days()){
                    rate = 0.05;
                    System.out.println("Idem v cykle");
                }
                else{
                    rate = 0.001;
                    System.out.println("Nejdem v cykle");
                }

                finalAmount += 1000 * rate;
                amount -= 1000;

                //other finances that left with rate 0.1
                return finalAmount + amount * 0.1;

            }
            else {
                return finalAmount + amount * 0.5;
            }
        }
        else{
            return amount * 0.2;
        }
    }

    private boolean noWithdrawalsIn10Days(){
        DateProvider dateProvider = new DateProvider();
        Date currentDate = dateProvider.now();
        double difference = 0;

        System.out.println("current date: " + currentDate);
        for (Transaction transaction: this.transactions) {
            System.out.println("Transaction date: " + transaction.getTransactionDate());
            difference = dateProvider.calculateDifferenceInDays(transaction.getTransactionDate(), currentDate, Locale.getDefault());

            if(difference <= 10){
                System.out.println("difference = " +  difference);
                return false;
            }
        }

        System.out.println("difference = " +  difference);
        return true;
    }

    @Override
    public String getType() {
        return "Maxi Savings Account\n";
    }
}

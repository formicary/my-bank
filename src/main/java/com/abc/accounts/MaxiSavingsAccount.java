package com.abc.accounts;

import com.abc.Transaction;
import com.abc.utils.Utils;

import java.util.ArrayList;

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

        if(Utils.isGraterThen1000(amount)){
            //first 1000 with rate 0.2

            finalAmount += 1000 * 0.2;
            amount -= 1000;

            if(Utils.isGraterThen1000(amount)){
                //second 1000 with rate 0.5

                finalAmount += 1000 * 0.5;
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

    @Override
    public String getType() {
        return "Maxi Savings Account\n";
    }
}

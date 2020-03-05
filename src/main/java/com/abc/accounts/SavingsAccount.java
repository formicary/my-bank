package com.abc.accounts;

import com.abc.Transaction;
import com.abc.utils.Utils;

import java.util.ArrayList;

public class SavingsAccount extends Account {
    //Savings accounts = rate 0.001 for first 1000 then 0.002

    public SavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
        this.setRate(0.001);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        double finalAmount = 0;

        if(Utils.isGraterThen1000(amount)){
            //first 1000 with rate 0.1

            finalAmount += 1000 * 0.001;
            amount -= 1000;

            return finalAmount + amount * 0.002;
        }
        else{
            return amount * 0.1;
        }
    }

    @Override
    public String getType() {
        return "Savings Account\n";
    }
}

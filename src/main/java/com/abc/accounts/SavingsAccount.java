package com.abc.accounts;

import com.abc.Transaction;
import com.abc.utils.Utils;

import java.util.ArrayList;

public class SavingsAccount extends Account {
    //Savings accounts = rate 0.001 for first 1000 then 0.002

    public SavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public double calculateInterest(double amount) {

        if(Utils.isGraterThen1000(amount)){
            return 0.002/365;
        }
        else{
            return (0.1/365);
        }
    }

    @Override
    public String getType() {
        return "Savings Account\n";
    }
}

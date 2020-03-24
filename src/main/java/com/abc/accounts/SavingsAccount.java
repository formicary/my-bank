package com.abc.accounts;

import com.abc.Transaction;
import com.abc.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class SavingsAccount extends Account {
    //Savings accounts = rate 0.001 for first 1000 then 0.002

    public SavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public double calculateInterest(double amount, Date date) {

        if(Utils.isGraterThen1000(amount)){
            return 0.002/DAYS_OF_YEAR;
        }
        else{
            return (0.1/DAYS_OF_YEAR);
        }
    }

    @Override
    public String getType() {
        return "Savings Account\n";
    }
}

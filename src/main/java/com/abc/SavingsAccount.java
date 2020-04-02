package com.abc;

import java.util.Date;

public class SavingsAccount extends Account {


    public double interestEarned() {
        double amount = sumTransactions();

        if (amount <= 1000)
            return amount * DateProvider.CalculateDailyCompound(0.001);
        else
            return amount * DateProvider.CalculateDailyCompound(0.002);
    
    }
}

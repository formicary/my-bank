package com.abc;

import java.util.Date;

public class CheckingAccount extends Account {
	
    public double interestEarned() {
        double amount = sumTransactions();
        return amount * DateProvider.CalculateDailyCompound(0.001);
    }

}

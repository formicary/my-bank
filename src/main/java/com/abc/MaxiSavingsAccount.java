package com.abc;

import java.util.Date;

public class MaxiSavingsAccount extends Account {

	
    public double interestEarned() {
    	Date lastWithdraw = this.lastWithdrawDate();
        double amount = sumTransactions();
        if (DateProvider.olderThanTenDays(lastWithdraw))
            return amount * DateProvider.CalculateDailyCompound(0.05);
        else 
            return amount * DateProvider.CalculateDailyCompound(0.001);
    }

}

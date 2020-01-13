package com.abc.accounts;

import java.util.Calendar;
import java.util.Date;

import com.abc.*;

public class MaxiSavingsAccount extends Account{
    
    private double interestRateMoreThan10Days = 0.05;
    private double interestRateLessThan10Days = 0.001;

    public MaxiSavingsAccount(AccountType accountType){
        super(accountType);
    }

    public double interestEarned() {
        if(transactions.isEmpty()){
            return 0;
        }
        int daysElapsed = getTransactionPeriod();
        if(daysElapsed == 0) daysElapsed = 1;

        for(int i = transactions.size()-1; i >= 0; i--){
            Transaction t = transactions.get(i);
            int dayDifference = DateProvider.getDayDifference(dateProvider.now(), t.getTransactionDate());

            if (dayDifference > 10){
                break;
            }
            else if(t.amount < 0){
                return calculateDailyCompoundInterest(interestRateLessThan10Days, daysElapsed);
            }

        }
        return calculateDailyCompoundInterest(interestRateMoreThan10Days, daysElapsed);

    }

    public String toString(){
        return "Maxi savings Account";
    } 

    
}
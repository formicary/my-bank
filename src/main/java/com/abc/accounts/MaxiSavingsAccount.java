package com.abc.accounts;

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
        //day difference between last transaction and current time
        int daysSinceLastTransaction = 
        DateProvider.getDayDifference(
            transactions.get(transactions.size()-1).getTransactionDate(),
             dateProvider.now());

        int daysElapsed = getTransactionPeriod();
        double transactionSum = sumTransactions();
        if(daysSinceLastTransaction >= 10){
            return transactionSum * Math.pow(interestRateMoreThan10Days, daysElapsed);
        }
        else{
            return transactionSum * Math.pow(interestRateLessThan10Days, daysElapsed);
        }
    }

    public String toString(){
        return "Maxi savings Account";
    } 
    
}
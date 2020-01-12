package com.abc.accounts;

public class CheckingAccount extends Account {
    /**
     * Flat interest rate of 0.1% per-annumm
     */
    private static double interestRate = 0.001;


    public CheckingAccount(AccountType accountType){
        super(accountType);
        currentInterest = 0.0;
    }
    
    public double interestEarned() {
        if(canCalculateInterest())
            return 0;
        
        int daysElapsed = dateProvider.getNumberOfElapsedDays();
        double dailyInterestRate = interestRate / dateProvider.getYearDays();
        double transactionSum = sumTransactions();
        double interestEarned = transactionSum * Math.pow(1 +dailyInterestRate, daysElapsed) - transactionSum;
        return interestEarned;
    }

    public String toString(){
        return "Checking Account";
    }
    
}
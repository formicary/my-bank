package com.abc.accounts;

public class SavingsAccount extends Account {

    /**
     * interest rate for the first $1000
     * */
    private double interestRate1 = 0.001;
    private double moneyCap1 = 1000.0;

    /**
     * interest rate after the initial $1000
     */
    private double interestRate2 = 0.002;

    public SavingsAccount(AccountType accountType){
        super(accountType);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        int daysElapsed = getTransactionPeriod();
        if(daysElapsed < 1) daysElapsed = 1;
        double transactionSum = sumTransactions();

        if(amount >= Double.MAX_VALUE)
        if (amount <= moneyCap1){
            return calculateInterest1(transactionSum, daysElapsed);
        }
        return (amount - moneyCap1) * Math.pow(interestRate2/dateProvider.getYearDays(), daysElapsed)
                 + calculateInterest1(moneyCap1, daysElapsed);
    }

    private double calculateInterest1(double transactionSum, int daysElapsed){
        return transactionSum * Math.pow(interestRate1/dateProvider.getYearDays(), daysElapsed);
    }


    public String toString(){
        return "Savings Account";
    }
    
}
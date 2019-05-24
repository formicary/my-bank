package com.accenture.accounts;

public class SavingsAccount extends Account {

    public SavingsAccount(long accountNumber){
        super(accountNumber);
    }


    public double interestEarned() {
        double summedTransactions;
        summedTransactions = sumTransactions();
        if (summedTransactions <= 1000)
            return summedTransactions * 0.001;
        else
            return 1 + (summedTransactions-1000) * 0.002;
    }

    public String getAccountType(){
        return "Savings Account";
    }

}

package com.accenture.accounts;

public class CheckingAccount extends Account {

    private final double interest = 0.001;

    public CheckingAccount(long accountNumber){
        super(accountNumber);
    }


    public double interestEarned() {
        double summedTransactions, amount;
        summedTransactions = sumTransactions();
        amount = summedTransactions * interest;
        return amount;
    }

    public String getAccountType(){
        return "Checking Account";
    }

}

package com.abc.account_types;

public class CheckingAccount extends BaseAccount{

    public String getAccountType(){
        return "Checking Account";
    }

    public double getInterestEarned(){
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        return amount * 0.0001;
    }
}

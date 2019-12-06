package com.abc.account_types;

public class SavingsAccount extends BaseAccount {
    public String getAccountType() {
        return "Savings Account";
    }

    public double getInterestEarned() {
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        return amount * 0.0001;
    }
}

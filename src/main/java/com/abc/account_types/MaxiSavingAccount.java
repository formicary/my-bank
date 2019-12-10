package com.abc.account_types;

import com.abc.Constants;

public class MaxiSavingAccount extends BaseAccount {
    public Constants.AccountTypes getAccountType() {
        return Constants.AccountTypes.MaxiSavingsAccount;
    }

    public String getAccountSummary() {
        return null;
    }

    public double getInterestEarned() {
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        if(amount <= 1000){
            return amount * 0.02;
        } else if (amount > 1000 && amount <= 2000){
            return amount * 0.05;
        } else {
            return amount * 0.1;
        }
    }
}

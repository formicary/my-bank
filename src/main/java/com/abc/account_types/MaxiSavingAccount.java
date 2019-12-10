package com.abc.account_types;

import com.abc.shared.Constants.AccountTypes;

public class MaxiSavingAccount extends BaseAccount {
    public AccountTypes getAccountType() { return AccountTypes.MaxiSavingsAccount; }

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

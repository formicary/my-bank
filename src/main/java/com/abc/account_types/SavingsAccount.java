package com.abc.account_types;

import com.abc.shared.Constants.AccountTypes;

public class SavingsAccount extends BaseAccount {

    public AccountTypes getAccountType() {
        return AccountTypes.SavingsAccount;
    }

    public double getInterestEarned() {
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        return amount * 0.0001;
    }
}

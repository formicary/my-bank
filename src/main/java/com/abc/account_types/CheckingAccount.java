package com.abc.account_types;

import com.abc.Constants;

public class CheckingAccount extends BaseAccount{
    public Constants.AccountTypes getAccountType() {
        return Constants.AccountTypes.CheckingAccount;
    }

    public double getInterestEarned(){
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        return amount * 0.0001;
    }
}

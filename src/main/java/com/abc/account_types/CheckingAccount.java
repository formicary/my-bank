package com.abc.account_types;

import com.abc.shared.Constants.AccountTypes;

public class CheckingAccount extends BaseAccount{
    public AccountTypes getAccountType() {
        return AccountTypes.CheckingAccount;
    }

    public double getInterestEarned(){
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        return calculateCompoundInterest(0.001, amount);
    }
}

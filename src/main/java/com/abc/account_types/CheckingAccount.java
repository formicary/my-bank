package com.abc.account_types;

import com.abc.shared.Constants.AccountTypes;

import static com.abc.shared.Methods.roundTo2dp;

public class CheckingAccount extends BaseAccount{
    public AccountTypes getAccountType() {
        return AccountTypes.CheckingAccount;
    }

    public double getInterestEarned(){
        double amount = sumTransactions();
        double compoundInterest = 0;

        if(amount < 0){
            return 0;
        }

        double interestRate = 0.001/365;

        for(int i = 0; i < 365; i++){
            double interestGained = amount * interestRate;
            compoundInterest += interestGained;
            amount += interestGained;
        }

        return roundTo2dp(compoundInterest);
    }
}

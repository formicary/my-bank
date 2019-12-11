package com.abc.account_types;

import com.abc.shared.Constants.AccountTypes;

import static com.abc.shared.Methods.roundTo2dp;

public class SavingsAccount extends BaseAccount {

    public AccountTypes getAccountType() {
        return AccountTypes.SavingsAccount;
    }

    public double getInterestEarned() {
        double amount = sumTransactions();
        double compoundInterest = 0;

        if(amount < 0){
            return 0;
        }

        for(int i = 0; i < 365; i++){
            if(amount < 1000){
                double interestGained = amount * (0.001/365);
                compoundInterest += interestGained;
                amount += interestGained;
            } else {
                double interestGained = (1000*(0.001/365)) + ((amount - 1000)* (0.002/365));
                compoundInterest += interestGained;
                amount += interestGained;
            }
        }

        return roundTo2dp(compoundInterest);
    }
}

package com.abc.account_types;

import com.abc.shared.Constants.AccountTypes;

import static com.abc.shared.Methods.roundTo2dp;

public class SavingsAccount extends BaseAccount {

    public AccountTypes getAccountType() {
        return AccountTypes.SavingsAccount;
    }

    public double getInterestEarned() {
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        if(amount < 1000){
            return calculateCompoundInterest(0.001, amount);
        } else {
            return calculateCompoundInterest(0.001, 1000) + calculateCompoundInterest(0.05, amount - 1000);
        }
    }
}

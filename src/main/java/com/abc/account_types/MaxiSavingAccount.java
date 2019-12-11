package com.abc.account_types;

import com.abc.shared.Constants.AccountTypes;

import java.util.Calendar;

public class MaxiSavingAccount extends BaseAccount {
    public AccountTypes getAccountType() { return AccountTypes.MaxiSavingsAccount; }

    public double getInterestEarned() {
        double amount = sumTransactions();

        if(amount < 0){
            return 0;
        }

        Calendar tenDaysAgo = Calendar.getInstance();
        tenDaysAgo.add(Calendar.DAY_OF_YEAR, -10);

        if(lastWithdrawal == null || lastWithdrawal.before(tenDaysAgo.getTime())){
            return calculateCompoundInterest(0.05, amount);
        } else {
            return calculateCompoundInterest(0.001, amount);
        }
    }
}

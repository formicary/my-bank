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
        return 0;
    }
}

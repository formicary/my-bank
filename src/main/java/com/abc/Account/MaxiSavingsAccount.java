package com.abc.Account;

import com.abc.DateProvider;
import com.abc.Transaction;

import java.util.Date;

public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    @Override
    protected double applyInterest(double amount, Date lastWithdrawal) {
        Date cutOffDate = DateProvider.getInstance().getPreviousDay(10);
        if(lastWithdrawal.after(cutOffDate)){
            return amount * (0.001 / 365);
        } else {
            return amount * (0.05 / 365);
        }
    }
}

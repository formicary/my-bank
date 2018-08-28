package com.abc.account;

import com.abc.branch.Customer;
import com.abc.util.ZeroAmountException;

/**
 * Created by sameen on 24/08/2018.
 */
public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount(Customer owner, double openingBalance) {
        super(owner, openingBalance);
    }

    public double interestEarned() {
        // 5% if no withdrawals in the past 10 days, otherwise 0.1%

        double amount = this.getBalance();

        long daysElapsed = this.daysElapsed();
        if (daysElapsed > 10) {
            return amount * 0.05;
        }

        return amount * 0.001;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.MAXI_SAVINGS;
    }
}

package com.abc.account;

import com.abc.branch.Customer;

/**
 * Created by sameen on 24/08/2018.
 */
public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount(Customer owner, double openingBalance) {
        super(owner, openingBalance);
    }

    public double interestEarned() {
        double amount = this.getBalance();
        if (amount <= 1000)
            return amount * 0.02;
        if (amount <= 2000)
            return 20 + (amount-1000) * 0.05;
        return 70 + (amount-2000) * 0.1;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.MAXI_SAVINGS;
    }
}

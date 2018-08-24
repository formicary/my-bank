package com.abc.account;

import com.abc.branch.Customer;

/**
 * Created by sameen on 24/08/2018.
 */
public class SavingsAccount extends Account {

    public SavingsAccount(Customer owner, double openingBalance) {
        super(owner, openingBalance);
    }

    public double interestEarned() {
        double amount = this.getBalance();
        if (amount <= 1000) {
            return amount * 0.001;
        }
        else {
            return 1 + (amount-1000) * 0.002;
        }
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }
}

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
            return amount * 0.001; // 0.1% for the first $1000
        }
        else {
            double enhancedInterestAmount = amount-1000;
            return 1 + enhancedInterestAmount * 0.002;
        }
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }
}

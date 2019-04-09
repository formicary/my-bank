package com.abc.accounts;

import com.abc.domain.Transaction;

import java.util.List;

/**
 * Saving Account Type.
 * Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%.
 */
public class SavingsAccount implements AccountType {

    /**
     * Singleton instance.
     */
    public static AccountType INSTANCE = new SavingsAccount();

    private SavingsAccount() {
    }

    @Override
    public String getName() {
        return "Savings Account";
    }

    @Override
    public double interestEarned(double amount, List<Transaction> transactions) {
        if (amount <= 1000) {
            return amount * 0.001;
        } else {
            return 1 + (amount - 1000) * 0.002;
        }
    }

}

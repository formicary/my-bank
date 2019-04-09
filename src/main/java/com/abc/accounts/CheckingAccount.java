package com.abc.accounts;

import com.abc.domain.Transaction;

import java.util.List;

/**
 * Checking Account Type.
 * Checking accounts have a flat rate of 0.1%.
 */
public class CheckingAccount implements AccountType {

    /**
     * Singleton instance.
     */
    public static AccountType INSTANCE = new CheckingAccount();

    private CheckingAccount() {
    }

    @Override
    public String getName() {
        return "Checking Account";
    }

    public double interestEarned(double amount, List<Transaction> transactions) {
        return amount * 0.001;
    }

}

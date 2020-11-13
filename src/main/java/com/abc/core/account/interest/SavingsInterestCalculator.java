package com.abc.core.account.interest;

import com.abc.core.account.Account;
import com.abc.core.account.AccountType;

public class SavingsInterestCalculator implements InterestCalculator {

    @Override
    public double calculateInterest(Account account) {
        if (account.getAccountType() != AccountType.SAVINGS) {
            throw new IllegalArgumentException("account must be of type: Savings Account");
        }
        if (account.sumOfTransactions() <= 1000) {
            return account.sumOfTransactions() * 0.001;
        }
        return 1 + 0.002 * (account.sumOfTransactions() - 1000);
    }
}

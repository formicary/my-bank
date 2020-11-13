package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;

public class MaxiSavingsInterestCalculator implements InterestCalculator {

    @Override
    public double calculateInterest(Account account) {
        if (account.getAccountType() != AccountType.MAXI_SAVINGS) {
            throw new IllegalArgumentException("account must be of type: Maxi Savings Account");
        }
        if (account.sumOfTransactions() <= 1000) {
            return account.sumOfTransactions() * 0.02;
        }
        else if (account.sumOfTransactions() <= 2000) {
            return 20 + 0.05 * (account.sumOfTransactions() - 1000);
        }
        return 70 + 0.1 * (account.sumOfTransactions() - 2000);
    }
}

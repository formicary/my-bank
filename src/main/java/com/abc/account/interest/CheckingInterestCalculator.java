package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;

public class CheckingInterestCalculator implements InterestCalculator {

    @Override
    public double calculateInterest(Account account) {
        if (account.getAccountType() != AccountType.CHECKING) {
            throw new IllegalArgumentException("account must be of type: Checking Account");
        }
        return account.sumOfTransactions() * 0.001;
    }
}

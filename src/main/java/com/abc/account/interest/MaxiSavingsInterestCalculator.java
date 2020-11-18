package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;

import java.time.LocalDate;

public class MaxiSavingsInterestCalculator implements InterestCalculator {

    @Override
    public double calculateInterest(Account account) {
        if (account.getAccountType() != AccountType.MAXI_SAVINGS) {
            throw new IllegalArgumentException("account must be of type: Maxi Savings Account");
        }
        if (withdrawalInPastTenDays(account)) {
            return 0.001 * account.sumOfTransactions();
        }
        return 0.05 * account.sumOfTransactions();
    }

    private boolean withdrawalInPastTenDays(Account account) {
        LocalDate today = LocalDate.now();
        LocalDate tenDaysAgo = today.minusDays(10);

        for (var transaction: account.getTransactions()) {
            if (transaction.getAmount() < 0 && transaction.getTransactionDate().isAfter(tenDaysAgo)) {
                return true;
            }
        }
        return false;
    }

}

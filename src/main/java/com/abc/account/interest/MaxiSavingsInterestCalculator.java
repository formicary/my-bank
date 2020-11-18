package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;

import java.time.LocalDate;

public class MaxiSavingsInterestCalculator implements InterestCalculator {

    @Override
    public double calculateDailyInterest(Account account) {
        if (account.getAccountType() != AccountType.MAXI_SAVINGS) {
            throw new IllegalArgumentException("account must be of type: Maxi Savings Account");
        }
        if (withdrawalInPastTenDays(account)) {
            return 0.001 * account.sumOfTransactions() / 365;
        }
        return 0.05 * account.sumOfTransactions() / 365;
    }

    private boolean withdrawalInPastTenDays(Account account) {
        LocalDate today = LocalDate.now();
        LocalDate tenDaysAgo = today.minusDays(10);

        return account.getTransactions().stream()
                .anyMatch(transaction -> transaction.getAmount() < 0 && transaction.getTransactionDate().isAfter(tenDaysAgo));
    }

}

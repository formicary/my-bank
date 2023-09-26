package com.abc.accenture.financial.items.account;

import com.abc.accenture.financial.items.Transaction;

import java.time.LocalDate;
import java.util.List;

public final class AccountMaxiSavings extends BaseAccountStructure implements Account {

    AccountMaxiSavings() {
        super();
    }

    @Override
    public double interestEarned(final double amount) {
        if (isNoWithdrawByPassDay(10)) {
            return amount * 0.005;
        }

        return Account.super.interestEarned(amount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.MAXI_SAVINGS;
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    private boolean isNoWithdrawByPassDay(final int untilOfDay) {
        LocalDate beforeTenDay = LocalDate.now().minusDays(untilOfDay);

        return transactions.stream()
                .filter(transaction -> beforeTenDay.isBefore(transaction.transactionDate()) || transaction.transactionDate().isEqual(beforeTenDay))
                .anyMatch(transaction -> transaction.amount() > 0);
    }
}

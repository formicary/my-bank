package com.abc.accenture.financial.items.account;

import com.abc.accenture.financial.items.Transaction;

import java.util.List;

public final class AccountSavings extends BaseAccountStructure implements Account {

    AccountSavings() {
        super();
    }

    @Override
    public double interestEarned(double amount) {
        if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount - 1000) * 0.002;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }
}

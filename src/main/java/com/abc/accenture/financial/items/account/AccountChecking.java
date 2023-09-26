package com.abc.accenture.financial.items.account;

import com.abc.accenture.financial.items.Transaction;

import java.util.List;

public final class AccountChecking extends BaseAccountStructure implements Account {

    AccountChecking() {
        super();
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CHECKING;
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }
}
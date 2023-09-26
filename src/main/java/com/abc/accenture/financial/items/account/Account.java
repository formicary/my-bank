package com.abc.accenture.financial.items.account;

import com.abc.accenture.financial.items.Transaction;

import java.util.List;

public interface Account {

    default double interestEarned(final double amount){
        return amount * 0.001;
    }
    AccountType getAccountType();

    List<Transaction> getTransactions();

}

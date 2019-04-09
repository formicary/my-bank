package com.abc.domain;

import com.abc.accounts.AccountType;

import java.util.List;
import java.util.function.Consumer;

public class MockAccountType implements AccountType {

    public String name = "Mock account";
    public double interest = 0.0d;
    public Consumer<Double> balanceValidator = balance -> {
    };
    public Consumer<List<Transaction>> transactionsValidator = transactions -> {
    };

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double interestEarned(double balance, List<Transaction> transactions) {
        balanceValidator.accept(balance);
        transactionsValidator.accept(transactions);
        return interest;
    }
}

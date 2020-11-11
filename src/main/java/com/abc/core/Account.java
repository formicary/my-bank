package com.abc.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Account {

    private final AccountType accountType;
    private final List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        validateAmount(amount);
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        // TODO: check if there is enough money on the account
        validateAmount(amount);
        transactions.add(new Transaction(-amount));
    }

    public double interestEarned() {
        double amount = sumOfTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount - 1000) * 0.05;
                return 70 + (amount - 2000) * 0.1;
            default:        // checking account
                return amount * 0.001;
        }
    }

    public double sumOfTransactions() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

}

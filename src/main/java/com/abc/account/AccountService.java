package com.abc.account;

import com.abc.transaction.Transaction;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private static AccountService instance;
    private final Map<AccountType, AccountStrategy> strategyMap;

    private AccountService() {
        strategyMap = new HashMap<>();
        strategyMap.put(AccountType.CHECKING, new CheckingAccountStrategy());
        strategyMap.put(AccountType.SAVINGS, new SavingsAccountStrategy());
        strategyMap.put(AccountType.MAXI_SAVINGS, new MaxiSavingsAccountStrategy());
        strategyMap.put(AccountType.SUPER_SAVINGS, new SuperSavingsAccountStrategy());
    }

    public static synchronized AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }

        return instance;
    }

    public void deposit(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction transaction = new Transaction(amount);
            account.getTransactions().add(transaction);
        }
    }

    public void withdraw(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction transaction = new Transaction(-amount);
            account.getTransactions().add(transaction);
        }
    }

    public double interestEarned(Account account) {
        double amount = sumTransactions(account);
        AccountType accountType = account.getAccountType();
        AccountStrategy strategy = strategyMap.get(accountType);
        if (strategy == null) {
            throw new IllegalStateException("Unknown account type: " + accountType);
        }

        return strategy.interestEarned(amount);
    }

    public double sumTransactions(Account account) {
        double amount = 0.0;
        for (Transaction transaction : account.getTransactions()) {
            amount += transaction.getAmount();
        }

        return amount;
    }
}

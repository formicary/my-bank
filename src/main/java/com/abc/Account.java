package com.abc;

import com.abc.types.AccountType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    private final AccountType accountType;
    private List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        transactions.add(new Transaction(-amount));
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                }
                return 1 + (amount - 1000) * 0.002;
            case MAXI_SAVINGS:
                if (hasWithdrawalInPastTenDays()) {
                    return amount * 0.001;
                }
                return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    public void transferTo(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (amount > sumTransactions()) {
            throw new IllegalArgumentException("amount must be smaller than balance");
        }
        transactions.add(new Transaction(-amount));
        account.transactions.add(new Transaction(amount));
    }

    public boolean hasWithdrawalInPastTenDays() {
        Date tenDaysAgo = new DateProvider().tenDaysAgo();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            if (t.getTransactionDate().after(tenDaysAgo) && t.getAmount() < 0) {
                return true;
            }
        }
        return false;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}

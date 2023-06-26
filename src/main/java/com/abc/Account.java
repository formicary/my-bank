package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account implements AccountInterface {

    public final AccountTypeEnum accountType;
    public List<Transaction> transactions;
    private double balance;

    public Account(AccountTypeEnum accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
    }

    public void transact(double amount) {
        transactions.add(new Transaction(amount));
        this.balance += amount;
    }

    public double interestEarned() {
        return 0;
    }

    public double sumTransactions() {
        return balance;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

}

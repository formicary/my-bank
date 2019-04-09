package com.mybank.entity;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final double YEARLY_COMPOUNDED = 1.0;
    public static final double DAILY_COMPOUNDED = 365.0;

    private final AccountType accountType;
    private final List<Transaction> transactions;
    private double noYearlyCompounded = YEARLY_COMPOUNDED;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public Account(AccountType accountType, double noYearlyCompounded) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.noYearlyCompounded = noYearlyCompounded;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getNoYearlyCompounded() {
        return noYearlyCompounded;
    }
}

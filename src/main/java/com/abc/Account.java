package com.abc;

import java.util.ArrayList;
import java.util.List;

// The account types used the long hand method using
// static final and int while enums are the better alternative.
enum AccountTypes {
    CHECKING, SAVINGS, MAXI_SAVINGS
}

public class Account {

    private final AccountTypes accountType;
    // I don't see the need for this to be initialised in constructor.
    // So I initialised it here out of the way.
    public List<Transaction> transactions = new ArrayList<>();

    public Account(AccountTypes accountType) {
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
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
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

}

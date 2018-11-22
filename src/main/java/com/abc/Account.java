package com.abc;

import java.math.BigDecimal;
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

    public void transfer(double amount, Account otherAccount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if ((sumTransactions().doubleValue() - amount) < 0) {
            // Added to make sure the transfer to makes sense.
            throw new IllegalArgumentException("not enough money in account");
        } else {
            this.transactions.add(new Transaction(-amount));
            otherAccount.transactions.add(new Transaction(amount));
        }
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
        } else if ((sumTransactions().doubleValue() - amount) < 0.01) {
            // Can be implemented to run a loan system instead of error here.
            throw new IllegalArgumentException("not enough money in account");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    // If the amount is larger then allowed with double.
    public void deposit(BigDecimal amount) {
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    // If the amount is larger then allowed with double.
    public void withdraw(BigDecimal amount) {
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if ((sumTransactions().subtract(amount)).doubleValue() < 0.01) {
            // Can be implemented to run a loan system instead of error here.
            throw new IllegalArgumentException("not enough money in account");
        } else {
            transactions.add(new Transaction(amount.negate()));
        }
    }

    public double interestEarned() {
        double amount = sumTransactions().doubleValue();
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

    public BigDecimal sumTransactions() {
        return checkIfTransactionsExist();
    }

    private BigDecimal checkIfTransactionsExist() {
        BigDecimal amount = new BigDecimal("0.0");
        for (Transaction t : transactions)
            amount = amount.add(t.getAmount());
        return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

}

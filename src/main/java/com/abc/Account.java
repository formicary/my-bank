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

    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount.doubleValue() <= 1000)
                    return amount.multiply(BigDecimal.valueOf(0.001));
                else
                    return (amount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.002)).add(BigDecimal.valueOf(1));
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                // This goes through all the transactions and can be optimised to start searching from a certain period
                for (Transaction t : transactions) {
                    // Checks if the transaction was a withdrawal and if it happened after the date 11 days in the past.
                    // This means the past 10 days but in a different way.
                    if (t.getAmount().doubleValue() < 0 && t.getTransactionDate().after(DateProvider.getDateFromNow(-11))) {
                        // The feature seems to want me to remove the already set interest rate generation method for Maxi-Savings so I removed it
//                        if (amount.doubleValue() <= 1000)
//                            return amount.multiply(BigDecimal.valueOf(0.02));
//                        if (amount.doubleValue() <= 2000)
//                            return (amount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.05)).add(BigDecimal.valueOf(20));
//                        return (amount.subtract(BigDecimal.valueOf(2000))).multiply(BigDecimal.valueOf( 0.1)).add(BigDecimal.valueOf(70));
                        return amount.multiply(BigDecimal.valueOf(0.01));
                    }
                }
                return amount.multiply(BigDecimal.valueOf(0.05));
            default:
                return amount.multiply(BigDecimal.valueOf(0.001));
        }
    }

    public BigDecimal sumTransactions() {
        return checkIfTransactionsExist();
    }

    private BigDecimal checkIfTransactionsExist() {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t : transactions)
            amount = amount.add(t.getAmount());
        return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

}

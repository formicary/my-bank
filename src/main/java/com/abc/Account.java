package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final String iban;
    private final int accountType;
    private final List<Transaction> transactions;

    public Account(String iban, int accountType) {
        this.iban = iban;
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount.negate()));
        }
    }

    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0)
                    return amount.multiply(BigDecimal.valueOf(0.001));
                else
                    return amount.subtract(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(0.002)).add(BigDecimal.ONE);
            case MAXI_SAVINGS:
                long daysSinceLastWithdrawal = daysSinceLastWithdrawal();
                if (daysSinceLastWithdrawal > 10 || daysSinceLastWithdrawal == -1)
                    return amount.multiply(BigDecimal.valueOf(0.05));
                else
                    return amount.multiply(BigDecimal.valueOf(0.001));
            default:
                return amount.multiply(BigDecimal.valueOf(0.001));
        }
    }

    public BigDecimal sumTransactions() {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t : transactions)
            amount = amount.add(t.getAmount());
        return amount;
    }

    private long daysSinceLastWithdrawal() {
        long days = -1;
        for (Transaction t : transactions) {
            if (t.isWithdrawal()) {
                long diff = LocalDate.now().until(t.getTransactionDate(), ChronoUnit.DAYS);
                if (diff > days) {
                    days = diff;
                }
            }
        }
        return days;
    }

    public String getIban() {
        return iban;
    }

    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

}

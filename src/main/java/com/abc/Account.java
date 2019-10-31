package com.abc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public final class Account {

    private final AccountType accountType;
    private final List<Transaction> transactions;
    private double balance;
    private double totalInterestEarned;
    private Instant lastAccrualDate;
    private final String accountName;


    private static final double CHECKING_BASE_INTEREST = 0.001/365;
    private static final double SAVING_BASE_INTEREST = 0.001/365;
    private static final double SAVING_SPECIAL_INTEREST = 0.002/365;
    private static final double MAXI_BASE_INTEREST = 0.05/365;
    private static final double MAXI_SPECIAL_INTEREST = 0.001/365;




    public Account(AccountType accountType, String accountName) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.lastAccrualDate = Instant.now();
        this.totalInterestEarned = 0;
        this.balance = 0;
        this.accountName = accountName;
    }

    /**
     * Default deposit, a transaction object with date Instant.now()
     * @param amount amount in $
     */
    public void deposit(double amount) {
        deposit(amount, Instant.now());

    }

    /**
     * For testing purposes, allow transactions to be created at future dates.
     * This method is synchronized to ensure multiple threads do not add the same
     * interest to the balance multiple times, or for interest earned to be missed
     * @param depositDate date of transaction
     */
    public synchronized void deposit(double amount, Instant depositDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        updateBalance(depositDate);
        this.balance += amount;
        this.transactions.add(new Transaction(amount, depositDate));

    }

    public void withdraw(double amount) {
        withdraw(amount, Instant.now());
    }

    public synchronized void withdraw(double amount, Instant withdrawDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (balance >= amount) {
            updateBalance(withdrawDate);
            balance -= amount;
            transactions.add(new Transaction(-amount, withdrawDate));

        } else {
            throw new IllegalArgumentException("Insufficient funds, current balance is " + String.format("$%,.2f", balance));

        }
    }

    /**
     * By finding the number of days between the current transaction being executed and the last
     *date interest was added, the amount of interest earned can be added to the balance
     */
    private void updateBalance(Instant updateDate) {
        long daysToAccrue = ChronoUnit.DAYS.between(lastAccrualDate, updateDate);

        while (daysToAccrue > 0) {
            double interestEarned = calculateAccrualRate(updateDate);
            balance += interestEarned;                                  //Interest is compounded daily
            totalInterestEarned += interestEarned;
            daysToAccrue--;
        }
        lastAccrualDate = updateDate;

    }

    public double getTotalInterestEarned() {
        return totalInterestEarned;
    }

    /**
     * Interest rate is calculated using the current balance and the account type
     * @param dateOfQuery date of transaction (needed to test for MAXI_SAVING accounts)
     * @return Interest to be added to the balance after 1 day
     */
    private double calculateAccrualRate(Instant dateOfQuery) {
        switch (accountType) {
            case CHECKING:
                return balance* CHECKING_BASE_INTEREST;
            case SAVINGS:
                if (balance <= 1000) {
                    return balance * SAVING_BASE_INTEREST;
                } else {
                    return (balance - 1000) * SAVING_SPECIAL_INTEREST;
                }
            case MAXI_SAVINGS:
                if (dateOfQuery.isAfter(lastAccrualDate.plus(10, ChronoUnit.DAYS))) {

                    return balance * MAXI_BASE_INTEREST;
                } else {

                    return balance * MAXI_SPECIAL_INTEREST;
                }
            default:
                throw new UnsupportedOperationException("Unknown account type");

        }

    }

    public double sumTransactions() {
        double amount = 0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;

    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }


    public double getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }
}

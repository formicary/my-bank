package com.abc.account;

import com.abc.Transaction;
import com.abc.TransactionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class Account {

    private static final Logger LOG = LogManager.getLogger(Account.class);

    private final List<Transaction> transactions = new ArrayList<>();

    public abstract String getType();

    public abstract double getRateByDate(LocalDateTime date);

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Deposits money to the account with current timestamp as transaction date
     */
    public void deposit(double amount) {
        deposit(amount, LocalDateTime.now());
    }

    /**
     * Deposits money to the account with specific transaction date
     */
    public void deposit(double amount, LocalDateTime transactionDate) {
        if (amount <= 0) {
            LOG.error("Deposit amount must be bigger than 0.");
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, TransactionType.DEPOSIT, transactionDate));
            LOG.debug("Deposit transaction with amount {} created", amount);
        }
    }

    /**
     * Withdraws money from the account with current timestamp as transaction date
     */
    public void withdraw(double amount) {
        withdraw(amount, LocalDateTime.now());
    }

    /**
     * Withdraws money from the account with specific transaction date
     */
    public void withdraw(double amount, LocalDateTime transactionDate) {
        if (amount <= 0) {
            LOG.error("Withdraw amount must be bigger than 0.");
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, TransactionType.WITHDRAWAL, transactionDate));
            LOG.debug("Withdraw transaction with amount {} created", amount);
        }
    }

    /**
     * Calculates earned interest of the account based on all transactions.
     * The rate is used based on the account instance
     */
    public double getInterestsEarned() {
        Iterator<Transaction> tIterator = getTransactions().stream().sorted(Comparator.comparing(Transaction::getTransactionDate)).iterator();
        Transaction t = tIterator.next();
        LocalDateTime lastTransactionDate = t.getTransactionDate();
        double balanceWithInterests = t.getAmount();

        while (tIterator.hasNext()) {
            Transaction currentTransaction = tIterator.next();
            long numberOfDays = Duration.between(currentTransaction.getTransactionDate(), lastTransactionDate).toDays();
            balanceWithInterests = calculateAndAddInterestsForPeriod(balanceWithInterests, getRateByDate(currentTransaction.getTransactionDate()), numberOfDays) + currentTransaction.getAmount();
            lastTransactionDate = currentTransaction.getTransactionDate();
        }
        balanceWithInterests = calculateAndAddInterestsForPeriod(balanceWithInterests, getRateByDate(LocalDateTime.now()), Duration.between(LocalDateTime.now(),lastTransactionDate).toDays());
        return balanceWithInterests - calculateAccountBalance();
    }

    /**
     * Calculates the current balance of the account
     * @return current balance
     */
    public double calculateAccountBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    /**
     * calculates interests earned for period
     */
    private double calculateAndAddInterestsForPeriod(double principal, double rate, long numberOfDays) {
        return principal*(Math.pow(1 + (rate/ 100 / 365), 365f * (Math.abs(numberOfDays)/365f)));
    }
}

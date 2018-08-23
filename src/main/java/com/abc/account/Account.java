package com.abc.account;

import com.abc.DateProvider;
import com.abc.transaction.InterestTransaction;
import com.abc.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.time.temporal.ChronoUnit.DAYS;

/** The class {@code Amount} represents a bank account. */
public abstract class Account {

    private final List<Transaction> transactions = new ArrayList<>();
    private LocalDateTime lastTimeInterestIncreased = DateProvider.getInstance().now();

    /** Initialise the {@code Account}. */
    public Account() {}

    /**
     * Get the daily interest earned.
     *
     * @param day The day that interest is being calculated for
     * @return The interest earned
     */
    protected abstract BigDecimal getDailyInterestEarned(final LocalDateTime day);

    /**
     * Get the name of the account type.
     *
     * @return The name of the account type
     */
    public abstract String getName();

    /** Recalculate the interest earned since the last time interest was calculated. */
    protected void recalculateInterest() {
        final LocalDateTime now = DateProvider.getInstance().now();
        final long daysToPayInterestFor = DAYS.between(lastTimeInterestIncreased, now);
        lastTimeInterestIncreased = now;
        for (long i = daysToPayInterestFor; i > 0; i--) {
            final LocalDateTime day = now.minusDays(i);
            final BigDecimal interestEarned = getDailyInterestEarned(day);
            if (interestEarned.compareTo(BigDecimal.ZERO) > 0) {
                increaseInterest(interestEarned);
            }
        }
    }

    private void increaseInterest(final BigDecimal amount) {
        transactions.add(new InterestTransaction(amount));
    }

    /**
     * Convert the yearly interest rate into a daily rate.
     *
     * @param yearlyRate The yearly rate of interest
     * @return The daily interest rate
     */
    protected BigDecimal convertToDailyRate(final BigDecimal yearlyRate) {
        return yearlyRate.divide(BigDecimal.valueOf(365), 7, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Get the transactions related to this account.
     *
     * @return The transactions
     */
    protected List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Get the interest earned.
     *
     * @return The interest earned
     */
    public BigDecimal getInterestEarned() {
        recalculateInterest();
        return transactions
                .stream()
                .filter(transaction -> transaction instanceof InterestTransaction)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Deposit to the account with the given amount.
     *
     * @param amount The amount to deposit
     */
    public void deposit(final BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Withdraw the given amount from the account.
     *
     * @param amount The amount to withdraw
     */
    public void withdraw(final BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount.negate()));
        }
    }

    /**
     * Transfer to the account.
     *
     * @param account The account to transfer to
     * @param amount The amount to transfer
     */
    public void transferTo(final Account account, final BigDecimal amount) {
        withdraw(amount);
        account.deposit(amount);
    }

    /**
     * Get the sum of the transactions on the account.
     *
     * @return The sum of transactions
     */
    public BigDecimal getTransactionsSum() {
        return transactions
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get the account's statement.
     *
     * @return The account's statement
     */
    public String getStatement() {
        String statement = getName() + "\n";
        double total = 0.0;
        for (final Transaction transaction : transactions) {
            if (!(transaction instanceof InterestTransaction)) {
                statement += "  " + getTransactionStatementString(transaction) + "\n";
                total += transaction.getAmount().doubleValue();
            }
        }
        statement += "Total " + toDollars(total);
        return statement;
    }

    private String getTransactionStatementString(final Transaction transaction) {
        return (transaction.getAmount().doubleValue() < 0 ? "withdrawal" : "deposit")
                + " "
                + toDollars(abs(transaction.getAmount().doubleValue()));
    }

    private String toDollars(double value) {
        return String.format("$%,.2f", value);
    }
}
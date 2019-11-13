package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.time.temporal.ChronoUnit.DAYS;

/** Abstract superclass representing a customer account. */
public abstract class Account {

    /** Error text displayed on attempt to deposit or withdraw an amount that is <= 0. */
    private static final String AMOUNT_LESS_THAN_ZERO_ERROR = "amount must be greater than zero";
    /** Denominator used for convert annual interest rate to daily interest rate. */
    private static final int DAYS_IN_YEAR_FOR_DAILY_INTEREST_RATE = 365;
    /** Auto-incremented id counter. */
    private static int idCounter = 0;
    /** All account transactions. */
    private List<Transaction> transactions;
    /** String representation of the Account subclass. */
    private String accountType;
    /** Account ID. */
    private int id;

    /**
     * Instantiates a new Account. Assigns unique ID.
     *
     * @param accountType the account subclass specified in the child constructor
     */
    public Account(String accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        this.id = ++idCounter;
    }

    /**
     * Deposit money into account.
     *
     * @param amount amount to deposit
     * @throws IllegalArgumentException if parameter is not greater than 0
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(AMOUNT_LESS_THAN_ZERO_ERROR);
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Withdraw money from account.
     *
     * @param amount amount to withdraw
     * @throws IllegalArgumentException if parameter is not greater than 0
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(AMOUNT_LESS_THAN_ZERO_ERROR);
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Converts parameter to US dollar String representation.
     *
     * @param amount the amount
     * @return amount formatted as a dollar String
     */
    private String toDollars(double amount) {
        return String.format("$%,.2f", abs(amount));
    }

    /**
     * Calculates the interest earned on the account using the interest rates specified in the
     * account subclass.
     *
     * @return the big decimal interest earned on the accounts
     */
    public BigDecimal interestEarned() {
        BigDecimal balance = BigDecimal.valueOf(0);

        int transactionIndex = 0;

        if (!(transactions == null || transactions.isEmpty())) {
            Collections.sort(transactions);
            LocalDate localDate;

            while (transactionIndex < transactions.size()) {
                localDate = transactions.get(transactionIndex).getTransactionDate().toLocalDate();
                do {
                    balance =
                            balance.add(
                                    BigDecimal.valueOf(
                                            transactions.get(transactionIndex).getAmount()));
                    ++transactionIndex;
                } while (transactionIndex < transactions.size()
                        && localDate.equals(
                                transactions
                                        .get(transactionIndex)
                                        .getTransactionDate()
                                        .toLocalDate()));

                LocalDate nextDate =
                        (transactionIndex == transactions.size())
                                ? LocalDateTime.now().toLocalDate()
                                : transactions
                                        .get(transactionIndex)
                                        .getTransactionDate()
                                        .toLocalDate();
                for (int i = 0; i < DAYS.between(localDate, nextDate); i++) {

                    balance = balance.add(dailyInterestEarned(balance));
                }
            }
        }

        return balance.subtract(BigDecimal.valueOf(getBalance()));
    }

    /**
     * Gets the account statement.
     *
     * @return the account statement formatted as a String table
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        // Column length 15 chars. Pad right with whitespace
        final String COLUMN_FORMAT = "%-15s";

        statement
                .append(String.format(COLUMN_FORMAT, "Date"))
                .append(String.format(COLUMN_FORMAT, "Time"))
                .append(String.format(COLUMN_FORMAT, "Transaction"))
                .append(String.format(COLUMN_FORMAT, "Amount"))
                .append(String.format(COLUMN_FORMAT, "Balance"))
                .append("\n");

        double total = 0;
        for (Transaction transaction : transactions) {
            total += transaction.getAmount();
            statement
                    .append(String.format(COLUMN_FORMAT, transaction.getDateString()))
                    .append(String.format(COLUMN_FORMAT, transaction.getTimeString()))
                    .append(String.format(COLUMN_FORMAT, transaction.getTransactionType()))
                    .append(String.format(COLUMN_FORMAT, toDollars(transaction.getAmount())))
                    .append(String.format(COLUMN_FORMAT, toDollars(total)))
                    .append("\n");
        }

        statement.replace(
                0,
                0,
                "Account: "
                        + accountType
                        + "(ID: "
                        + id
                        + ")\n"
                        + "Current Balance: "
                        + toDollars(total)
                        + "\nTransactions:\n");
        return statement.toString();
    }

    /**
     * Daily interest earned on the parameter balance. Used to calculate interest on account. Each
     * Account subclass implements this method using its own interest rates.
     *
     * @param balance balance of the account
     * @return the big decimal interest earned
     */
    abstract BigDecimal dailyInterestEarned(BigDecimal balance);

    /**
     * Gets the denominator to be used when converting annual interest rate to daily interest rate.
     *
     * @return the number of days in the year when calculating the daily interest rate
     */
    static double getDaysInYearForDailyInterestRate() {
        return DAYS_IN_YEAR_FOR_DAILY_INTEREST_RATE;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public double getBalance() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    /**
     * Gets account type.
     *
     * @return the string representation of the account subclass
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Gets all transactions.
     *
     * @return transactions transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    public String toString() {
        return "ID: " + id + ", Type: " + getAccountType();
    }
}

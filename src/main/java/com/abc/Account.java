package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account with various types (CHECKING, SAVINGS,
 * MAXI_SAVINGS).
 */
public abstract class Account {

    /**
     * Enumeration representing the type of the account (CHECKING, SAVINGS,
     * MAXI_SAVINGS).
     */
    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS
    }

    private final AccountType accountType;
    private LocalDate openedDate;
    private BigDecimal balance;
    private LocalDate lastWithdrawalDate;
    public List<Transaction> transactions;

    // Interest rates
    public static final BigDecimal CHECKING_INTEREST_RATE = new BigDecimal("0.001");
    public static final BigDecimal SAVINGS_LOW_INTEREST_RATE = new BigDecimal("0.001");
    public static final BigDecimal SAVINGS_HIGH_INTEREST_RATE = new BigDecimal("0.002");
    public static final BigDecimal MAXI_SAVINGS_LOW_INTEREST_RATE = new BigDecimal("0.001");
    public static final BigDecimal MAXI_SAVINGS_HIGH_INTEREST_RATE = new BigDecimal("0.05");

    // Interest rate thresholds
    public static final BigDecimal SAVINGS_THRESHOLD = new BigDecimal("1000.00");
    public static final BigDecimal MAXI_SAVINGS_FIRST_THRESHOLD = new BigDecimal("1000.00");
    public static final BigDecimal MAXI_SAVINGS_SECOND_THRESHOLD = new BigDecimal("2000.00");

    // Number of days for Maxi-Savings interest rate calculation
    public static final int MAXI_SAVINGS_WITHDRAWAL_DAYS = 10;

    /**
     * Constructs an Account with the specified account type.
     *
     * @param accountType The type of the account (CHECKING, SAVINGS, MAXI_SAVINGS).
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.openedDate = LocalDate.now(); // Set the opened date to the current date
        this.balance = BigDecimal.ZERO;
        this.lastWithdrawalDate = null;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param amount The amount to deposit. Must be greater than zero.
     * @throws IllegalArgumentException if the amount is not greater than zero.
     */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        } else {
            transactions.add(new Transaction(amount));
            balance = balance.add(amount);
        }
    }

    /**
     * Withdraws the specified amount from the account.
     *
     * @param amount The amount to withdraw. Must be greater than zero.
     * @throws IllegalArgumentException if the amount is not greater than zero or
     *                                  exceeds the balance.
     */
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        } else {
            transactions.add(new Transaction(amount.negate()));
            balance = balance.subtract(amount);
            lastWithdrawalDate = LocalDate.now();
        }
    }

    /**
     * Calculates the interest earned on the account based on its type and balance.
     *
     * @param balance The current balance of the account.
     * @return The interest earned.
     */
    public abstract BigDecimal getInterestRate(BigDecimal balance);

    /**
     * Calculates the compounded interest earned over a specified number of days.
     *
     * @param numberOfDays The number of days to calculate interest for.
     * @return The compounded interest earned.
     */
    public BigDecimal compoundedInterestEarned(int numberOfDays) {
        LocalDate date = LocalDate.now();
        int daysInCurrentYear = date.lengthOfYear();        
        
        BigDecimal annualInterestRate = getInterestRate(balance);
        int compoundingFrequency = daysInCurrentYear;
        BigDecimal one = new BigDecimal("1.00");
        
        BigDecimal dailyInterestRate = annualInterestRate.divide(new BigDecimal(compoundingFrequency), 10,
                RoundingMode.HALF_UP);
        BigDecimal accruedInterest = balance
                .multiply(one.add(dailyInterestRate).pow(numberOfDays))
                .subtract(balance);

        return (accruedInterest.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Calculates the sum of all transactions associated with the account.
     *
     * @return The sum of all transactions.
     */
    public BigDecimal sumTransactions() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Gets the account type.
     *
     * @return The account type (CHECKING, SAVINGS, MAXI_SAVINGS).
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Gets the current balance of the account.
     *
     * @return The account balance.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Gets the date when the account was opened.
     *
     * @return The opened date.
     */
    public LocalDate getOpenedDate() {
        return openedDate;
    }

    /**
     * Gets the date of the last withdrawal from the account.
     *
     * @return The last withdrawal date, or null if no withdrawals have been made.
     */
    public LocalDate getLastWithdrawalDate() {
        return lastWithdrawalDate;
    }
}

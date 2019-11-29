package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// Abstract class 'Account' inherited by specific types of account: Checking, Savings, Maxi-Savings
public abstract class Account {
    private static final String ILLEGAL_AMOUNT = "Amount must be greater than zero!";
    private static final String ILLEGAL_WITHDRAW = "Withdraw request larger than current balance: ";
    private static final AtomicLong ACCOUNT_ID = new AtomicLong(0);
    // All transactions under account will have a unique ID
    private final AtomicLong TRANSACTION_ID = new AtomicLong(0);
    // Account details
    private long accountId = 0;
    private final String accountType;
    // Store transactions for each customer
    private List<Transaction> transactions;
    // Variable to update account balance
    private BigDecimal balance;

    /**
     * Constructor for class, take a string for the account type
     * @param accountType
     */
    public Account(String accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = new BigDecimal("0");
        this.accountId = ACCOUNT_ID.getAndIncrement();
    }

    /**
     * Abstract implementation for interestEarned, calculations differ for each type of account
     * @return [BigDecimal] returns big decimal value for interest
     */
    public abstract BigDecimal interestEarned();
    /**
     * Add a new pre-declared transaction to the account
     * @param transaction
     */
    public void newTransaction(Transaction transaction) {
        double transactionAmount = transaction.getAmount();
        if(transactionAmount > 0) {
            deposit(transactionAmount);
        } else if(transactionAmount < 0) {
            withdraw(transactionAmount);
        } else {
            throw new IllegalArgumentException(ILLEGAL_AMOUNT);
        }
    }
    /**
     * Deposit an amount of money, throws exception if value is less or equal than 0;
     * @param amount
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(ILLEGAL_AMOUNT);
        } else {
            transactions.add(new Transaction(amount, TRANSACTION_ID.getAndIncrement()));
            balance = balance.add(BigDecimal.valueOf(amount));
        }
    }
    /**
     * Withdraw an amount of money, throws exception if value is less or equal than 0;
     * Also throws exception if user tries to withdraw more money than they currently
     * have on the account
     * @param amount
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(ILLEGAL_AMOUNT);
        } else {
            if(balance.subtract(BigDecimal.valueOf(amount)).compareTo(BigDecimal.valueOf(0)) < 0) {
                throw new IllegalArgumentException(ILLEGAL_WITHDRAW
                        + String.format("$%,.2f", balance.abs()));
            } else {
                transactions.add(new Transaction(-amount, TRANSACTION_ID.getAndIncrement()));
                balance = balance.subtract(BigDecimal.valueOf(amount));
            }
        }
    }

    // Getters
    /**
     * Returns account type
     * @return [String] string for the type of account
     */
    public String getAccountType() {
        return accountType;
    }
    /**
     * Returns list of transactions under this account
     * @return List<Transaction> list of transactions under thisa ccount
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }
    /**
     * Returns calculated balance for the account
     * @return [BigDecimal] returns big decimal value for current account balance
     */
    public BigDecimal getBalance() {
        return balance;
    }
    /**
     * Returns unique account ID
     * @return [long] returns unique int accountId
     */
    public long getAccountId() {
        return accountId;
    }

    // Setters
    /**
     * Update balance for the account
     * @param newBalance
     */
    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }
}

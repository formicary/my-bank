package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract Account class represents the core functionality of all Accounts.
 */
public abstract class Account {
    //Fixed interest rates
    static final BigDecimal INTEREST_F1 = new BigDecimal("0.001");    //0.1%
    static final BigDecimal INTEREST_F2 = new BigDecimal("0.002");    //0.2%
    //Balance required for higher interest rate
    static final BigDecimal PRIMARY_BALANCE = new BigDecimal("1000.00");
    static final BigDecimal SECONDARY_BALANCE = new BigDecimal("2000.00");

    //The account name (e.g. Checking Account)
    private final String accountName;
    //All transaction on this account
    private List<Transaction> transactions;
    //Money in this account
    private BigDecimal balance;

    //Unique account number
    private final String accountNum;

    /**
     * Initializes a new Account then generates a random account number.
     *
     * @param accountName the account name
     */
    public Account(String accountName) {
        this.accountName = accountName;
        this.transactions = new ArrayList<>();
        this.balance = BigDecimal.ZERO;

        this.accountNum = Helper.generateAccountNum();
    }

    private BigDecimal updateBalance() {
        //loop through all transactions and sum them up
        this.balance = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            this.balance = this.balance.add(transaction.getAmount());
        }
        //round here?
        this.balance = balance.setScale(2, RoundingMode.HALF_EVEN);
        return balance;
    }

    /**
     * Gets the current balance of this account.
     *
     * @return money in account
     */
    public BigDecimal getBalance() {
//        System.out.println(currencyFormat(balance));
        return balance;
    }

    /**
     * Add money to this Account. A new transaction is added to list of transaction.
     *
     * @param amount the amount to deposit
     */
    public boolean deposit(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            updateBalance();
            return true;
        }
    }

    /**
     * Withdraw from this Account. A new transaction is added to list of transaction.
     *
     * @param amount the amount to withdraw
     */
    public boolean withdraw(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            //ensure balance > amount to withdraw
            if (canWithdraw(amount)) {
                transactions.add(new Transaction(amount.negate()));
                updateBalance();
                return true;
            } else {
                System.err.printf("ERROR Account (%s)\nbalance: %s\ntrying to withdraw: %s\n",
                        accountNum, balance, amount.toString());
                return false;
            }
        }
    }

    /**
     * Returns true if the current balance is greater than the amount given.
     *
     * @param amount the amount to withdraw
     * @return true is balance > amount
     */
    public boolean canWithdraw(BigDecimal amount) {
        //throw new IllegalArgumentException("amount must be greater than balance");
        return amount.compareTo(balance) <= 0;
    }

    /**
     * Calculate interest earned depending on account type.
     *
     * @return interest earned
     */
    abstract BigDecimal interestEarned();

    /**
     * List of all transactions.
     *
     * @return transaction list
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Returns the name of the account. e.g. Savings Account
     *
     * @return name of current account
     */
    public String getAccountName() {
//        System.out.println(accountType);
        return accountName;
    }

    /**
     * Returns the String representation of the unique account number.
     *
     * @return current account number
     */
    public String getAccountNum() {
        return accountNum;
    }
}

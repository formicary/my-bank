package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * TASK
 * Different accounts have interest calculated in different ways:
 * -Checking accounts have a flat rate of 0.1%
 * -Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
 * -Maxi-Savings accounts have a rate of 2% for the first $1,000 then 5% for the next $1,000
 * then 10%
 * <p>
 * Additional
 * -Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the
 * past 10 days otherwise 0.1%
 * -Interest rates should accrue and compound daily (incl. weekends), rates above are per-annum
 */

/**
 * This class represents all Accounts a Customer can open.
 */
public abstract class Account {
    private final String accountType;
    private List<Transaction> transactions;

    private BigDecimal balance;

    /**
     * Initializes a new Account where the type matches the argument given.
     *
     * @param accountType the account id
     */
    public Account(String accountType) {
        System.out.println("Creating new Account...");
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal updateBalance() {
        //loop through all transaction
        //either add too or subtract based on the transaction type
        this.balance = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            this.balance = this.balance.add(transaction.getAmount());
        }
        this.balance = balance.setScale(2, RoundingMode.HALF_EVEN);
        return balance;
//        System.out.println("Total balance is now: " + currencyFormat(balance));
    }

    public BigDecimal getBalance() {
//        System.out.println(currencyFormat(balance));
        return balance;
    }

    /**
     * Add money to this Account. The transaction is added to list of transaction.
     *
     * @param amount amount in decimal
     */
    public void deposit(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            System.out.println(getAccountType() + " -> deposit: " + amount);
            transactions.add(new Transaction(amount));
            updateBalance();
        }
    }

    /**
     * Withdraw from this Account. The transaction is added to list of transaction.
     *
     * @param amount amount in decimal
     */
    public void withdraw(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            System.out.println(getAccountType() + " -> deposit: " + amount);
            transactions.add(new Transaction(amount.negate()));
            updateBalance();
        }
    }

    /**
     * Interest depends on account type.
     *
     * @return interest earned
     */
    abstract long interestEarned();

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
    public String getAccountType() {
//        System.out.println(accountType);
        return accountType;
    }

    public static String currencyFormat(BigDecimal n) {
        // TODO: 12/10/2019 Use helper method
        return NumberFormat.getCurrencyInstance().format(n);
    }
}

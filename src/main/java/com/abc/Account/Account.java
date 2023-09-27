package com.abc.account;

import static com.abc.utilities.AmountValidator.canWithdraw;
import static com.abc.utilities.AmountValidator.isNegativeAmount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abc.Transaction;
import com.abc.utilities.enums.AccountType;
import com.abc.utilities.enums.TransactionType;

/**
 *  Bank account template inherited by child classes
 */
public abstract class Account {
    private final AccountType accountType;
    private BigDecimal balance;
    public List<Transaction> transactions;

    /**
     * Initialises a new account with the given account type, an emptpty list of transactions and a balance of zero
     * @param accountType the type of account (Checking, Savings, Maxi Savings)
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = BigDecimal.ZERO;
    }

    /**
     * Gets the account type
     * @return account type
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Gets a list of transactions
     * @return transactions list
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Gets the account balance
     * @return account balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Deposits the given amount into the account
     * @param amount quantity to be deposited (must be greater than zero)
     */
    public void depositFunds(BigDecimal amount) {
        isNegativeAmount(amount);
        balance = balance.add(amount);
        transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
    }

    /**
     * Withdraws the given amount from the account
     * @param amount quantity to be withdrawn (must be greater than zero and must not exceed the account balance)
     */
    public void withdrawFunds(BigDecimal amount) {
        isNegativeAmount(amount);
        canWithdraw(this.getBalance(), amount);
        balance = balance.subtract(amount);
        transactions.add(new Transaction(amount.negate(), TransactionType.WITHDRAWAL));
    }

    /**
     * Calculates the interest earned according to the implementation of each individual account type
     */
    public abstract BigDecimal interestEarned();

    /**
     * Checks if any transactions currently exist
     * @return true if there are no transactions
     */
    public boolean checkIfTransactionsExist() {
        return transactions.isEmpty();
    }

    /**
     * Sums the values of all existing transactions to caluclate the total amount
     * @return the total amount (must equal the account balance)
     */
    public BigDecimal sumTransactions() {
        BigDecimal amount = BigDecimal.ZERO;

        if (checkIfTransactionsExist()) {
            return amount;
        }

        for (Transaction transaction : transactions) {
            amount = amount.add(transaction.getAmount());
        }
        return amount;
    }
}

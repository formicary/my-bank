package com.abc.account;

import com.abc.branch.Customer;
import com.abc.transaction.TransactionFactory;
import com.abc.transaction.TransactionType;
import com.abc.util.DateProvider;
import com.abc.util.ZeroAmountException;
import com.abc.transaction.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Account {

    private Customer owner; // an account must belong to a customer
    private double balance; // holds the current balance
    private List<Transaction> transactions; // holds all transaction made under this account
    private Date lastWithdrawal;

    // An account must be opened an account holder (owner) and an opening balance
    public Account(Customer owner, double openingBalance) {
        this.owner = owner;
        this.transactions = new ArrayList<Transaction>();
        this.balance = openingBalance;
        this.lastWithdrawal = DateProvider.getInstance().now();
    }

    /**
     * Calculates the interest for this account.
     * Note: Interest earned is variable, it is dependant upon account type.
     * @return amount earned
     */
    abstract public double interestEarned();
    abstract public AccountType getAccountType();

    /**
     * Add amount to this account's balance.
     * @param amount in GBP
     * @throws ZeroAmountException
     */
    public void deposit(double amount) throws ZeroAmountException {
        if (amount <= 0) {
            throw new ZeroAmountException("amount must be greater than zero");
        } else {
            transactions.add(new TransactionFactory().createTransaction(TransactionType.CREDIT, amount));
            this.updateBalance();
        }
    }

    /**
     * Subtract amount this account's balance.
     * @param amount in GBP
     * @throws ZeroAmountException
     */
    public void withdraw(double amount) throws ZeroAmountException {
        if (amount <= 0) {
            throw new ZeroAmountException("amount must be greater than zero");
        } else {
            transactions.add(new TransactionFactory().createTransaction(TransactionType.DEBIT, amount));
            lastWithdrawal = DateProvider.getInstance().now();
            this.updateBalance();
        }
    }

    /**
     * Adds to balance if transaction type is <code>CREDIT</code>
     * subtracts from balance if transaction type is <code>DEBIT</code>.
     */
    private void updateBalance() {
        double bal = 0.0;
        for (Transaction transaction : transactions) {
            switch (transaction.getType()) {
                case CREDIT:
                    bal += transaction.getAmount();
                    break;
                case DEBIT:
                    bal -= transaction.getAmount();
                    break;
            }
        }
        this.balance = bal;
    }

    /**
     * Computes the difference in days since date this method was invoked
     * and date of last withdrawal.
     * @return days since last withdrawal was made from this account
     */
    public long daysElapsed() {
        Date endDate = DateProvider.getInstance().now();
        long endTime = endDate.getTime();
        long startTime = this.lastWithdrawal.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        return diffDays;
    }

    /**
     * Computes the difference in days since date this method was invoked
     * and date of last withdrawal.
     * @return days since last withdrawal was made from this account
     */
    public long daysElapsed(Date startDate) {
        Date endDate = DateProvider.getInstance().now();
        long endTime = endDate.getTime();
        long startTime = startDate.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        return diffDays;
    }

    public Customer getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Date getLastWithdrawal() {
        return lastWithdrawal;
    }
}

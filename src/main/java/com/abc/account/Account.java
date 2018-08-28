package com.abc.account;

import com.abc.branch.Customer;
import com.abc.transaction.TransactionFactory;
import com.abc.transaction.TransactionType;
import com.abc.util.DateProvider;
import com.abc.util.ZeroAmountException;
import com.abc.transaction.Transaction;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

    private String id;
    private Customer owner; // an account must belong to a customer
    private double balance; // holds the current balance
    private List<Transaction> transactions; // holds all transaction made under this account
    private Date lastWithdrawal;

    // An account must be opened an account holder (owner) and an opening balance
    public Account(Customer owner, double openingBalance) {
        this.id = new UID().toString(); // generate a unique id for this account
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
            Transaction t = new TransactionFactory().createTransaction(TransactionType.CREDIT, amount);
            this.updateBalance(t);
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
            Transaction t = new TransactionFactory().createTransaction(TransactionType.DEBIT, amount);
            lastWithdrawal = DateProvider.getInstance().now();
            this.updateBalance(t);
        }
    }

    /**
     * Adds to balance if transaction type is <code>CREDIT</code>
     * subtracts from balance if transaction type is <code>DEBIT</code>.
     * @param t the transaction
     */
    private void updateBalance(Transaction t) {
        switch (t.getType()) {
            case CREDIT:
                this.balance += t.getAmount();
                break;
            case DEBIT:
                this.balance -= t.getAmount();
                break;
        }
        transactions.add(t);
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

    public String getId() {
        return id;
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

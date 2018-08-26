package com.abc.account;

import com.abc.branch.Customer;
import com.abc.transaction.TransactionFactory;
import com.abc.transaction.TransactionType;
import com.abc.util.DateProvider;
import com.abc.util.ZeroAmountException;
import com.abc.transaction.Transaction;

import java.util.ArrayList;
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
        try {
            this.deposit(openingBalance);
        } catch (ZeroAmountException e) {
            e.printStackTrace();
            System.err.println("Opening balance of account must be greater than zero!");
        }
        this.transactions = new ArrayList<Transaction>();
    }

    abstract public double interestEarned();
    abstract public AccountType getAccountType();

    /**
     * Add given amount of money to this account.
     * @param amount
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
     * Subtract given amount of money from this account.
     * @param amount
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

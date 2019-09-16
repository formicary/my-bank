package com.abc.Account;

import com.abc.Exception.InsufficientBalanceException;
import com.abc.Transaction;

import com.abc.Money;;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for processing and keeping reference to transactions and calculating balance.
 */
abstract public class Account {
    private List<Transaction> transactions;

    Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @return Account name according to type (e.g. Checking/Savings)
     */
    public abstract String getName();

    public abstract ArrayList<InterestRule> getInterestRules();

    /**
     * Processes a transaction if sufficient funds are available
     * @param t transaction
     * @throws InsufficientBalanceException
     */
    public void processTransaction(Transaction t) throws InsufficientBalanceException {
        Money balance = getBalance();
        // if transaction amount is less than 0, then insufficient balance
        if (balance.add(t.getAmount()).compareTo(new Money("0")) < 0)
            throw new InsufficientBalanceException();
        else transactions.add(t);
    }

    public Money getBalance() {
        Money balance = new Money("0");
        for (Transaction t: transactions)
            balance = balance.add(t.getAmount());
        return balance;
    }
}

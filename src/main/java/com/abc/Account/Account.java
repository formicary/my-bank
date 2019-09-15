package com.abc.Account;

import com.abc.Exception.InsufficientBalanceException;
import com.abc.Transaction;

import com.abc.Money;;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Calculates interest on balance using given rate on all funds above lowerBoundary
     * @param balance
     * @param rate
     * @param lowerBoundary
     * @throws IllegalArgumentException thrown when lowerBoundary is less than 0
     * @return
     */
    protected Money calculateInterest(Money balance, BigDecimal rate, Money lowerBoundary) throws IllegalArgumentException{
        if(lowerBoundary.compareTo(new Money("0.00")) < 0)
            throw new IllegalArgumentException("Lower boundary must be positive!");

        if (balance.compareTo(lowerBoundary) < 1)
            return new Money("0.00");
        return balance.subtract(lowerBoundary).multiply(rate);

    }

    /**
     * Calculates interest on balance using given rate on all funds between the lowerBoundary and upperBoundary
     * @param balance
     * @param rate
     * @param lowerBoundary
     * @param upperBoundary
     * @return
     * @throws IllegalArgumentException thrown when lowerBoundary is less than 0 OR upperBoundary < lowerBoundary
     */
    protected Money calculateInterest(Money balance, BigDecimal rate, Money lowerBoundary,
                                      Money upperBoundary) throws IllegalArgumentException {
        if(upperBoundary.compareTo(lowerBoundary) != 1)
            throw new IllegalArgumentException("Upper boundary must be greater than lower boundary!");
        if(lowerBoundary.compareTo(new Money("0.00")) < 0)
            throw new IllegalArgumentException("Lower boundary must be positive!");

        if (balance.compareTo(lowerBoundary) < 1)
            return new Money("0.00");
        if (balance.compareTo(upperBoundary) < 1)
            balance.subtract(lowerBoundary).multiply(rate);
        return upperBoundary.subtract(lowerBoundary).multiply(rate);
    }

    /**
     * Calculated according to account type, aggregating interest from calculateInterest method
     * @return
     */
    public abstract Money getTotalInterestEarned();

    public Money getBalance() {
        Money balance = new Money("0");
        for (Transaction t: transactions)
            balance = balance.add(t.getAmount());
        return balance;
    }
}

package com.abc;

import java.math.BigDecimal;
import java.util.*;


public abstract class Account {


    public final UUID accountId = UUID.randomUUID();


    public List<Transaction> transactions;
    public BigDecimal accountBalance;
    private Conversion numberToConvert;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        this.accountBalance = BigDecimal.valueOf(0);


    }


// add account to customers

    public abstract String getAccountSummary();

    public abstract BigDecimal getAccruedInterestForTimePeriod(int numOfDays);


    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("amount deposited must be greater than zero");
        } else {
            accountBalance = accountBalance.add(amount);
            transactions.add(new Transaction(amount, Transaction.AccountOperation.DEPOSIT));

            System.out.println(accountBalance);
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("amount withdrawn must be greater than zero");
        }
        if (amount.compareTo(accountBalance) == 1) {
            System.out.println(accountBalance);
            throw new IllegalArgumentException("There are insufficient funds in this account. Please try withdrawing less.");
        } else {
            accountBalance = accountBalance.subtract(amount);
            transactions.add(new Transaction(amount, Transaction.AccountOperation.WITHRAW));

        }
    }

    public BigDecimal getAccountBalance() {
        return this.accountBalance;
    }


    public abstract BigDecimal interestEarnedDaily();


    public BigDecimal sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = new BigDecimal(0.0);
        for (Transaction t : transactions)
            amount = amount.add(t.amount);
        return amount;
    }

    public abstract String getAccountType();

    public abstract BigDecimal interestAccrued(BigDecimal dailyInterest);


}

package com.abc.Accounts;

import com.abc.Transaction;
import com.abc.util.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

abstract public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    protected final int accountType;
    protected List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(Money amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) { // if amount deposited is less than/equal to 0
            throw new IllegalArgumentException("amount deposited must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(Money amount){

        if (amount.compareTo(BigDecimal.ZERO) <= 0) { // if amount withdrawn is less than/equal to 0
            throw new IllegalArgumentException("amount withdrawn must be greater than zero");
        }

        // check if the customer has enough funds to withdraw
        Money balance = sumTransactions();

        if (balance.compareTo(amount) < 0) { // the customer dot NOT have enough funds to withdraw
            throw new IllegalArgumentException("amount withdrawn must be less than or equal to account balance");
        }

        // customer can withdraw normally
        transactions.add(new Transaction((Money) amount.negate()));
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    abstract public Money interestEarned();

    public Money sumTransactions() {

        Money amount = (Money) BigDecimal.ZERO;

        for (Transaction t: transactions)
            amount = (Money) amount.add(t.getAmount());

        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}

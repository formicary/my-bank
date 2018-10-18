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

    protected Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(Money amount, int transactionType) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) { // if amount deposited is less than/equal to 0
            throw new IllegalArgumentException("amount deposited must be greater than zero");
        } else {

            transactions.add(new Transaction(amount, transactionType));
        }
    }

    public void withdraw(Money amount, int transactionType){

        if (amount.compareTo(BigDecimal.ZERO) <= 0) { // if amount withdrawn is less than/equal to 0
            throw new IllegalArgumentException("amount withdrawn must be greater than zero");
        }

        // check if the customer has enough funds to withdraw
        Money balance = sumTransactions();

        if (balance.compareTo(amount) < 0) { // the customer dot NOT have enough funds to withdraw
            throw new IllegalArgumentException("amount withdrawn must be less than or equal to account balance");
        }

        // customer can withdraw normally
        transactions.add(new Transaction(amount.negate(), transactionType));
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


    abstract public Money interestEarned();

    /**
     *
     * @return amount of interest earned in 1 day
     */
    abstract public Money dailyInterestEarned();

    /**
     * calculates the amount of money an account earns per day
     * using equation A = P(r/n)
     * where:
     * A = interest earned per day
     * P = principle amount
     * r = interest rate in decimal
     * n = number of times per year it will be calculated (constant 365)
     *
     * mainly a helper function to reduce repeated code
     *
     * @param P principle amount
     * @param r annual interest rate
     * @return
     */
    protected Money dailyInterestAtRate(Money P, Money r){
        final Money n = new Money("365");

        return P.multiply(r.divide(n));
    }

    public Money sumTransactions() {

        Money amount = Money.ZERO;

        for (Transaction t: transactions)
            amount = amount.add(t.getAmount());

        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}

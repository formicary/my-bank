package com.abc.accounts;

import com.abc.Transaction;
import com.abc.interestbehaviors.InterestBehavior;
import com.abc.util.Money;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private List<Transaction> transactions = new ArrayList<>();

    // Delegate for calculating the interest for an account
    private InterestBehavior interestBehavior;

    Account(InterestBehavior interestBehavior) {
        this.interestBehavior = interestBehavior;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Money interestEarned() {
        return interestBehavior.getInterest(this);
    }


    public void deposit(Money amount) {
        if (amount.isMinus() || amount.isZero()) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(Money amount) {
        if (amount.isMinus() || amount.isZero()) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount.negate()));
        }
    }


    public Money sumTransactions() {
        List<Money> transactionAmounts = new ArrayList<Money>();
        for (Transaction transaction : transactions) {
            transactionAmounts.add(transaction.getAmount());
        }

        return Money.sum(transactionAmounts, Money.DEFAULT_CURRENCY);
    }


//    private BigDecimal getPercentage(){
//        BigDecimal result = amountOne.multiply(PERCENTAGE);
//        result = result.divide(HUNDRED, ROUNDING_MODE);
//        return rounded(result);
//    }

}

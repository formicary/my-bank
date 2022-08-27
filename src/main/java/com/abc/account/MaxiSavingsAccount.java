package com.abc.account;

import com.abc.customer.Customer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account {

    MaxiSavingsAccount(Customer customer, AccountType accountType) {
        super(customer, accountType);
    }

    @Override
    public double calcInterestEarned() {
        double amount = getBalance();
        if (calcDayDiff() > 10) {
            return amount * 0.05;
        } else {
            return amount * 0.001;
        }
    }

    private long calcDayDiff() { // TODO: return with rate?
        Transaction transaction = getLastTransaction();
        if (transaction == null) {
            return Long.MAX_VALUE;
        }
        Date today = new Date();
        long diffInMillis = Math.abs(today.getTime() - transaction.getTransactionDate().getTime());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}

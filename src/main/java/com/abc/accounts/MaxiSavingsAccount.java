package com.abc.accounts;

import com.abc.Transaction;

import java.util.*;

public class MaxiSavingsAccount extends Account {

    private static double NO_RECENT_WITHDRAW_INTEREST_RATE = 0.05;
    private static double RECENT_WITHDRAW_INTEREST_RATE = 0.001;
    private static double LOWER_DAILY_INTEREST_RATE = RECENT_WITHDRAW_INTEREST_RATE / 365.0;
    private static double HIGHER_DAILY_INTEREST_RATE = NO_RECENT_WITHDRAW_INTEREST_RATE / 365.0;

    public MaxiSavingsAccount() {
        super();
    }

    @Override
    public double interestEarned() {
        Collections.sort(getTransactions());
        Deque<Transaction> transactionDeque = new ArrayDeque<Transaction>(getTransactions());

        Date firstTransaction = transactionDeque.getFirst().transactionDate;
        Calendar start = new GregorianCalendar();
        start.setTime(firstTransaction);
        Calendar end = Calendar.getInstance();

        double balance = 0.0;
        double totalInterest = 0.0;

        Calendar rateLimitedUntil = Calendar.getInstance();
        rateLimitedUntil.setTime(firstTransaction);
        rateLimitedUntil.add(Calendar.DAY_OF_YEAR, -1);

        int days = 0;
        for (Date d = firstTransaction; start.before(end);
             start.add(Calendar.DAY_OF_YEAR, 1), d = start.getTime()) {

            if (!transactionDeque.isEmpty()){
                Transaction nextTransaction = transactionDeque.getFirst();
                Date nextTransactionDate = nextTransaction.transactionDate;
                if (d.after(nextTransactionDate) || d.equals(nextTransactionDate)) {
                    balance += transactionDeque.removeFirst().amount;
                    if (nextTransaction.amount < 0) {
                        rateLimitedUntil.setTime(nextTransactionDate);
                        rateLimitedUntil.add(Calendar.DAY_OF_YEAR, 10);
                    }
                }
            }
            double dayInterest = d.before(rateLimitedUntil.getTime())
                    ? balance * LOWER_DAILY_INTEREST_RATE
                    : balance * HIGHER_DAILY_INTEREST_RATE;
            totalInterest += dayInterest;
            balance += dayInterest;
            days++;
        }
        System.out.println(days);
        return totalInterest;
    }

    // Abstract method implementation not used by this child class.
    protected double getDailyInterest(double balance) {
        return 0;
    }

    protected String getPrettyAccountType() {
        return "Maxi Savings Account\n";
    }


}

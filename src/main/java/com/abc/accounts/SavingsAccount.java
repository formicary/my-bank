package com.abc.accounts;

import com.abc.Transaction;

import java.util.*;

public class SavingsAccount extends Account {

    private static double TIER_1_INTEREST_RATE = 0.001;
    private static double TIER_2_INTEREST_RATE = 0.002;
    private static double TIER_1_DAILY_INTEREST_RATE = TIER_1_INTEREST_RATE / 365.0;
    private static double TIER_2_DAILY_INTEREST_RATE = TIER_2_INTEREST_RATE / 365.0;


    public SavingsAccount() {
        super();
    }

    public double interestEarned() {
        Collections.sort(getTransactions());
        Deque<Transaction> transactionDeque = new ArrayDeque<Transaction>(getTransactions());

        Date firstTransaction = transactionDeque.getFirst().transactionDate;
        Calendar start = new GregorianCalendar();
        start.setTime(firstTransaction);

        Calendar end = Calendar.getInstance();

        double balance = 0.0;
        double totalInterest = 0.0;

        for (Date d = firstTransaction; start.before(end);
             start.add(Calendar.DAY_OF_YEAR, 1), d = start.getTime()) {

            if (!transactionDeque.isEmpty()){
                Date nextTransaction = transactionDeque.getFirst().transactionDate;
                if (d.after(nextTransaction) || d.equals(nextTransaction)) {
                    balance += transactionDeque.removeFirst().amount;
                }
            }
            double dayInterest = balance <= 1000
                    ? balance * TIER_1_DAILY_INTEREST_RATE
                    : 1000 * TIER_1_DAILY_INTEREST_RATE + (balance - 1000) * TIER_2_DAILY_INTEREST_RATE;
            totalInterest += dayInterest;
            balance += dayInterest;

        }
        return totalInterest;
    }

    protected String getPrettyAccountType() {
        return "Savings Account\n";
    }
}

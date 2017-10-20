package com.abc.accounts;

import com.abc.DateProvider;
import com.abc.Transaction;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CheckingAccount extends Account {

    private static double FLAT_INTEREST_RATE = 0.001;
    private static double DAILY_INTEREST_RATE = FLAT_INTEREST_RATE / 365.0;

    public CheckingAccount() {
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
            double dayInterest = balance * DAILY_INTEREST_RATE;
            totalInterest += dayInterest;
            balance += dayInterest;

        }
        return totalInterest;
    }

    protected String getPrettyAccountType() {
        return "Checking Account\n";
    }

}

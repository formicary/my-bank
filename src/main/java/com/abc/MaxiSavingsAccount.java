/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author prarthana
 */
public class MaxiSavingsAccount extends Account {

    public static final int MAX_AMOUNT = 10;

    public MaxiSavingsAccount(Customer owner, int accountType) {
        super(owner, accountType);
    }

    @Override
    public double interestEarned(double amount) {
        return 0.0;
    }

    public double interestEarned(double amount, int days) {
        if (days <= MAX_AMOUNT) {
            return amount * 0.05 / DAYS_IN_A_YEAR;
        } else {
            return amount * 0.001 / DAYS_IN_A_YEAR;
        }
    }

    //Add 5% interest with no withdrawals in past 10 days otherwise add 0.1% interest
    @Override
    public double getTotal() {
        if (transactions.isEmpty()) {
            return 0.0;
        }

        Collections.sort(transactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                return t1.getTransactionDate().compareTo(t2.getTransactionDate());
            }
        });

        int daysSinceWithdrawal = MAX_AMOUNT;
        Transaction prevTransaction = transactions.get(0);
        double amount = prevTransaction.amount;
        if (prevTransaction instanceof DebitTransaction) {
            daysSinceWithdrawal = 0; //Counter for seeing how many days since a withdrawal
        }

        for (int i = 1; i < transactions.size(); i++) {
            Transaction tmp = prevTransaction;
            prevTransaction = transactions.get(i);
            int dayDiff = getDayDifference(tmp.getTransactionDate(), prevTransaction.getTransactionDate());

            for (int j = 0; j < dayDiff; j++) {

                amount += interestEarned(amount, daysSinceWithdrawal);
                if (daysSinceWithdrawal <= MAX_AMOUNT) {
                    daysSinceWithdrawal++;
                }

            }
            amount += prevTransaction.amount;
            if (prevTransaction instanceof DebitTransaction) {
                daysSinceWithdrawal = 0;
            }
        }

        int daysDiff = getDayDifference(prevTransaction.getTransactionDate(), DateProvider.getInstance().now());
        for (int k = 0; k < daysDiff; k++) {
            amount += interestEarned(amount, daysSinceWithdrawal);
            if (daysSinceWithdrawal <= MAX_AMOUNT) {
                daysSinceWithdrawal++;
            }

        }
        return amount;
    }

}

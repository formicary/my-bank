package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    private Calendar createdCalendar = Calendar.getInstance();
    public Date createdDate = this.createdCalendar.getTime();

    private final int accountType;
    public List<Transaction> transactions;

    public void updateYear(int year) {
        this.createdCalendar.set(Calendar.YEAR, year);
        this.createdDate = this.createdCalendar.getTime();
        // Built for testing purposes, no real uses outside of this
    }

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public void transferToAccount(Account b, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            Transaction takeMoney = new Transaction(-amount);
            takeMoney.destination = b;
            transactions.add(takeMoney);

            // Add destination and source of transfer if it is ever needed in future
            Transaction addMoney = new Transaction(amount);
            takeMoney.source = this;
            b.transactions.add(addMoney);

        }
    }

    public double annualInterestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                } else {
                    return 1 + (amount - 1000) * 0.002;
                }
            case MAXI_SAVINGS:
                if (transactionIn10Days()) {
                    return amount * 0.001;
                } else {
                    return amount * 0.05;
                }
            default:
                return amount * 0.001;
        }
    }

    public double daysInterestEarned(double days) {
        double amount = sumTransactions();
        double power = days / 365;
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    double rate = Math.pow(1.001, power);
                    return (amount * rate) - amount;
                } else {
                    double lowRate = Math.pow(1.001, power);
                    double rate = Math.pow(1.002, power);
                    return (1000 * lowRate) + ((amount - 1000) * rate) - amount;
                }
            case MAXI_SAVINGS:
                if (transactionIn10Days()) {
                    double rate = Math.pow(1.001, power);
                    return (amount * rate) - amount;
                } else {
                    double rate = Math.pow(1.05, power);
                    return (amount * rate) - amount;
                }
            default:
                double rate = Math.pow(1.001, power);
                return (amount * rate) - amount;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    public Date latestWithdrawal() {
        List<Date> transactionDates = new ArrayList<Date>();
        for (Transaction t : this.transactions) {
            if (t.amount < 0) {
                transactionDates.add(t.transactionDate);
            }
        }
        if (transactionDates.isEmpty()) {
            return this.createdDate;
            //If there has been no withdrawals, return the account created date
        } else {
            Date latestDate = Collections.max(transactionDates);
            return latestDate;
        }
    }

    public boolean transactionIn10Days() {
        Calendar now = Calendar.getInstance();
        Calendar transaction = Calendar.getInstance();
        transaction.setTime(this.latestWithdrawal());
        transaction.add(Calendar.DATE, 10);
        Date dateCheck = transaction.getTime();
        Date dateNow = now.getTime();

        return dateNow.before(dateCheck);
    }

}

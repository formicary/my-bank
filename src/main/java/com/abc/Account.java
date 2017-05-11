package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Account {

    private final AccountType accountType;
    private List<Transaction> transactions;
    private DateTime lastModified;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.lastModified = DateTime.now();
        this.transactions = new ArrayList<Transaction>();
    }
    public Account(int accountTypeOrdinal) {
        AccountType accountType = AccountType.values()[accountTypeOrdinal];
        this.accountType = accountType;
        this.lastModified = DateTime.now();
        this.transactions = new ArrayList<Transaction>();
    }
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(-amount));
        }
    }

    public void accrueInterestDaily() {
        DateTime currentDate = DateTime.now();

        int difference = Days.daysBetween(new LocalDate(lastModified),
                new LocalDate(currentDate)).getDays();
        if (difference >= 1) {
            double interest = interestEarned();
            this.deposit(interest / 365);
            this.lastModified = currentDate;
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(this.accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case SUPER_SAVINGS:
                if (amount <= 4000)
                    return 20;
            case MAXI_SAVINGS:
                Transaction lastTransaction = transactions.get(transactions.size() - 1);
                Date lastTransactionDate = lastTransaction.getTransactionDate();
                DateTime currentDate = DateTime.now();

                int difference = Days.daysBetween(new LocalDate(lastTransactionDate),
                                                    new LocalDate(currentDate)).getDays();
                if (difference >= 10) {
                    return amount * 0.05;
                }
                return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: this.transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

}

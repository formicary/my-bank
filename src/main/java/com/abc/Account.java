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

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    public Account(int accountTypeOrdinal) {
        AccountType accountType = AccountType.values()[accountTypeOrdinal];
        this.accountType = accountType;
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

    public double accrueInterestDaily() {
        double interest = interestEarned();
        this.deposit(interest / 365);
        return interest/365;
    }

    public double interestEarned() {
        double amount = getBalance();
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
//              Check if it has been less than 10 days since the last transaction
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

    public double getBalance() {
        double amount = 0.0;
        for (Transaction t: this.transactions)
            amount += t.getAmount();
        return amount;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

}

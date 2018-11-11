package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Account {

    private final AccountTypes accountType;
    private List<Transaction> transactions;
    private double balance;
    private boolean overdrawn;

    public Account(AccountTypes accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
        this.overdrawn = false;
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
            if (balance >= 0) {
                overdrawn = false;
            }
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
            if (balance < 0) {
                overdrawn = true;
            }
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case CHECKING:
                    return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                } else {
                    return 1 + (amount - 1000) * 0.002;
                }
            case MAXI_SAVINGS:
//                if (amount <= 1000) {
//                    return amount * 0.02;
//                } else if (amount <= 2000) {
//                    return 20 + (amount - 1000) * 0.05;
//                } else {
//                    return 70 + (amount - 2000) * 0.1;
//                }
                return amount * checkLastTransaction(transactions);
            default:
                return 0;   // invalid account type
        }
    }

    private double checkLastTransaction(List<Transaction> transactions) {
        double interest =  0;
        Transaction last = null;
        for (int i = transactions.size() - 1; i >= 0; i-- ) {
            if (transactions.get(i).getAmount() < 0) {
                last = transactions.get(i);
                break;
            }
        }

        Date today = DateProvider.getInstance().now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR, -10);
        Date tenDays = cal.getTime();

        if (last == null) {
            interest = 0.05;
        } else if (last.getTransactionDate().before(tenDays)) {
            interest = 0.05;
        } else if (last.getTransactionDate().after(tenDays)) {
            interest = 0.001;
        }
        return  interest;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

}

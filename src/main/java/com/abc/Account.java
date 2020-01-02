package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Math.abs;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (sumTransactions() < amount) {
            throw new IllegalArgumentException("amount must not be greater than available balance");
        } else {
            transactions.add(new Transaction(-amount));
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
                    return 1 + (amount-1000) * 0.002;
                }
            case MAXI_SAVINGS:
                boolean noRecentWithdrawals = true;
                Calendar now = Calendar.getInstance();
                // Set now to the start of the day
                now.set(Calendar.HOUR_OF_DAY, 0);
                now.set(Calendar.MINUTE, 0);
                now.set(Calendar.SECOND, 0);
                now.set(Calendar.MILLISECOND, 0);
                Calendar transactionCalendar;
                for (Transaction t : transactions) {
                    transactionCalendar = t.getTransactionDate();
                    transactionCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    transactionCalendar.set(Calendar.MINUTE, 0);
                    transactionCalendar.set(Calendar.SECOND, 0);
                    transactionCalendar.set(Calendar.MILLISECOND, 0);
                    if (now.getTimeInMillis() - transactionCalendar.getTimeInMillis() <= 10 * 24 * 60 * 60 * 1000) {
                        noRecentWithdrawals = false;
                        break;
                    }
                }
                if (noRecentWithdrawals) {
                    return amount * 0.05;
                } else {
                    return amount * 0.001;
                }
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    private static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    public String statement() {
        StringBuilder s = new StringBuilder();

        //Translate to pretty account type
        switch (getAccountType()) {
            case Account.CHECKING:
                s.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                s.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            s.append("  ").append(t.amount < 0 ? "withdrawal" : "deposit").append(" ").append(toDollars(t.amount)).append("\n");
            total += t.amount;
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }
}

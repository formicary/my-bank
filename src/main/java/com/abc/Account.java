package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private final Lock lock = new ReentrantLock(true); // Uses fair locking

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) throws IllegalArgumentException { // No mutex as depositing will add, not subtract. Can re-run method later for accuracy?
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) throws IllegalArgumentException {
        lock.lock();
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("amount must be greater than zero");
            } else if (sumTransactions() - amount < 0) {
                throw new IllegalArgumentException("balance too low to withdraw");
            } else {
                transactions.add(new Transaction(-amount));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private double interestBetweenTwoDates(double amount, double annual_interest, Date from, Date to) {
        Calendar calFrom = Calendar.getInstance();
        Calendar calTo = Calendar.getInstance();

        calFrom.set(from.getYear(), from.getMonth(), from.getDay());
        calTo.set(to.getYear(), to.getMonth(), to.getDay());

        int days = -1;

        final int diffWithinYear = Math.abs(calTo.get(Calendar.DAY_OF_YEAR) - calFrom.get(Calendar.DAY_OF_YEAR));
        final int diffYear = Math.abs(to.getYear() - from.getYear());

        if (diffYear == 0) {
            days = diffWithinYear;
        }
        else {
            if (diffYear == 1) {
                days = (365 - calFrom.get(Calendar.DAY_OF_YEAR));
                days += calTo.get(Calendar.DAY_OF_YEAR); // remainder
            }
            else {
                int count = diffYear;

                while (count > 1)
                {
                    days += 365;
                    count--;
                }

                days += calTo.get(Calendar.DAY_OF_YEAR); // 1 year ahead
                days += (365 - calFrom.get(Calendar.DAY_OF_YEAR)); // remainder from previous year to start point

            }
        }

        final double daily_interest = annual_interest / 365;
        //final double daily_interest = Math.pow((1 + annual_interest), 1.0 / 365.0) - 1;
        final double daily_compound_interest = (amount * Math.pow((1 + daily_interest), (days / 365.0) * 365.0)) - amount;

        return daily_compound_interest;
    }

    public double interestEarned() {
        double incremental_interest = 0.0;

        if (transactions.isEmpty())
            return 0.0;

        switch(accountType) {
            case CHECKING: // Per transaction pair

                for (int i = 0; i < transactions.size() - 1; i++) {
                    double diff = transactions.get(i + 1).amount - transactions.get(i).amount;
                    double amount =
                        diff >= 0 ? transactions.get(i).amount : transactions.get(i + 1).amount;

                    incremental_interest += interestBetweenTwoDates(amount,
                        0.001, transactions.get(i).transactionDate,
                        transactions.get(i + 1).transactionDate);
                }

                return incremental_interest;
            case SAVINGS: // Per transaction pair

                for (int i = 0; i < transactions.size() - 1; i++) {
                    double diff = transactions.get(i + 1).amount - transactions.get(i).amount;
                    double amount =
                        diff >= 0 ? transactions.get(i).amount : transactions.get(i + 1).amount;

                    if (amount <= 1000) {
                        incremental_interest += interestBetweenTwoDates(transactions.get(i).amount,
                            0.001, transactions.get(i).transactionDate,
                            transactions.get(i + 1).transactionDate);
                    } else {

                        incremental_interest += (10 + interestBetweenTwoDates((amount - 1000),
                            0.002, transactions.get(i).transactionDate,
                            transactions.get(i + 1).transactionDate)); // 0.1% of 1000 = 10

                    }
                }
            case MAXI_SAVINGS: // Across all transactions
                double amount = sumTransactions();

                Date currentDate = DateProvider.getInstance().now();
                final int N = 10;

                boolean withdrawnInPastNDays = false;
                for (Transaction t : transactions)
                {
                    if (Math.abs(currentDate.getDay() - t.transactionDate.getDay()) < N && t.amount < 0) { // < 0 indicates withdrawal
                        withdrawnInPastNDays = true;
                        break;
                    }
                }

                if (withdrawnInPastNDays)
                    return amount * 0.001;

                return amount * 0.05;
            default:
                throw new IllegalArgumentException("invalid account type. valid account types are: 'SAVINGS', 'MAXI_SAVINGS', and 'CHECKING' accounts");
        }
    }

    /*
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case CHECKING:
                return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 10 + ((amount-1000) * 0.002); // 0.1% of 1000 = 10
            case MAXI_SAVINGS:
                Date currentDate = DateProvider.getInstance().now();
                final int N = 10;

                boolean withdrawnInPastNDays = false;
                for (Transaction t : transactions)
                {
                    if (Math.abs(currentDate.getDay() - t.transactionDate.getDay()) < N && t.amount < 0) { // < 0 indicates withdrawal
                        withdrawnInPastNDays = true;
                        break;
                    }
                }

                if (withdrawnInPastNDays)
                    return amount * 0.001;

                return amount * 0.05;

                // Delete below if necessary (2nd stage).
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + ((amount-1000) * 0.05); // 2% of 1000 = 20
                return 20 + 50 + ((amount-2000) * 0.1); // 2% of first 1000 = 20, // 5% of next 1000 = 50, total = 70

            default:
                throw new IllegalArgumentException("invalid account type. valid account types are: 'SAVINGS', 'MAXI_SAVINGS', and 'CHECKING' accounts");
        }
    }
    //*/

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}

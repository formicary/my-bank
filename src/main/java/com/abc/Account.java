package com.abc;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// The account types used the long hand method using
// static final and int while enums are the better alternative.
enum AccountTypes {
    CHECKING, SAVINGS, MAXI_SAVINGS
}

public class Account {
    private final AccountTypes accountType;
    // I don't see the need for this to be initialised in constructor.
    // So I initialised it here out of the way.
    public List<Transaction> transactions = new ArrayList<Transaction>();

    public Account(AccountTypes accountType) {
        this.accountType = accountType;
    }

    public void transfer(double amount, Account otherAccount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if ((sumTransactions().doubleValue() - amount) < 0) {
            // Added to make sure the transfer to makes sense.
            throw new IllegalArgumentException("not enough money in account");
        } else {
            this.transactions.add(new Transaction(-amount));
            otherAccount.transactions.add(new Transaction(amount));
        }
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
        } else if ((sumTransactions().doubleValue() - amount) < 0.0) {
            // Can be implemented to run a loan system instead of error here.
            throw new IllegalArgumentException("not enough money in account");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    // If the amount is larger then allowed with double.
    public void deposit(BigDecimal amount) {
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    // If the amount is larger then allowed with double.
    public void withdraw(BigDecimal amount) {
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if ((sumTransactions().subtract(amount)).doubleValue() < 0.0) {
            // Can be implemented to run a loan system instead of error here.
            throw new IllegalArgumentException("not enough money in account");
        } else {
            transactions.add(new Transaction(amount.negate()));
        }
    }

    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        BigDecimal totalInterest = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        switch (accountType) {
            case SAVINGS:
                if (amount.doubleValue() <= 1000)
                    return amount.multiply(BigDecimal.valueOf(0.001));
                else
                    return (amount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.002)).add(BigDecimal.valueOf(1));
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                // This goes through all the transactions and can be optimised to start searching from a certain period
                BigDecimal interestRate = BigDecimal.valueOf(0.05);
                Transaction prevTransaction = null;

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                for (Transaction t : transactions) {
                    // Checks if the transaction was a withdrawal and if it happened after the date 11 days in the past.
                    // This means the past 10 days but in a different way.
                    if (prevTransaction == null) {
                        total = t.getAmount();
                    } else {
                        cal1.setTime(prevTransaction.getTransactionDate());
                        cal2.setTime(t.getTransactionDate());

                        DateTime start = new DateTime(cal1);
                        DateTime end = new DateTime(cal2);

                        System.out.println(start);
                        System.out.println(end);

                        Interval interval = new Interval(start, end);
                        int gap = interval.toDuration().toStandardDays().getDays();
                        // Checks if the transaction was a withdrawal and if it happened after the date 11 days in the past.
                        if (gap < 11 && t.getAmount().doubleValue() < 0) {
                            interestRate = BigDecimal.valueOf(0.01);
                        }
                        System.out.println("c "+total.setScale(2,BigDecimal.ROUND_HALF_EVEN));
                        if (gap != 0 && prevTransaction.getAmount().doubleValue()>0) {
                            BigDecimal newTotal = total.multiply(BigDecimal.ONE.
                                    add(interestRate.multiply(BigDecimal.valueOf(1 / 365.0))).pow(gap));
                            totalInterest = totalInterest.add(newTotal.subtract(total));
                            total = newTotal;
                            System.out.println(newTotal.setScale(2,BigDecimal.ROUND_HALF_EVEN));
                        }
                        total = total.add(t.getAmount());
                        // Reset the interest rate just in case the the next transaction is after than 10 days.
                        interestRate = BigDecimal.valueOf(0.05);
                        System.out.println(total.setScale(2,BigDecimal.ROUND_HALF_EVEN));
                    }
                    prevTransaction = t;
                }

                cal1.setTime(prevTransaction.getTransactionDate());
                cal2.setTime(DateProvider.getNow());

                DateTime start = new DateTime(cal1);
                DateTime end = new DateTime(cal2);

                Interval interval = new Interval(start, end);
                int gap = interval.toDuration().toStandardDays().getDays();
                // Checks if the transaction was a withdrawal and if it happened after the date 11 days in the past.
                if (gap < 11 && prevTransaction.getAmount().doubleValue() < 0) {
                    interestRate = BigDecimal.valueOf(0.01);
                }
                if (gap != 0) {
                    BigDecimal newTotal = total.multiply(BigDecimal.ONE.
                            add(interestRate.multiply(BigDecimal.ONE.
                                    divide(BigDecimal.valueOf(365.0),10,BigDecimal.ROUND_HALF_EVEN))).
                            pow(gap));
                    totalInterest = totalInterest.add(newTotal.subtract(total));
                }

                return totalInterest;
            default:
                return amount.multiply(BigDecimal.valueOf(0.001));
        }
    }

    public BigDecimal sumTransactions() {
        return checkIfTransactionsExist();
    }

    private BigDecimal checkIfTransactionsExist() {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t : transactions)
            amount = amount.add(t.getAmount());
        return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

}

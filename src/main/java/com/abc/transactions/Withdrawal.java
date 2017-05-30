package com.abc.transactions;

import com.abc.DateProvider;
import com.abc.exceptions.ExceedsNegativeBalanceException;
import com.abc.exceptions.NonPositiveAmountException;

import java.util.Date;


public class Withdrawal implements Transaction {
    private static final double NEGATIVE_BALANCE = -1000;
    private Date transactionDate;
    private double amount;
    private double currentBalance;

    public Withdrawal(double currentBalance, double amount) {
        this.transactionDate = DateProvider.getInstance().now();
        this.currentBalance = currentBalance;
        this.amount = amount;
    }

    public double executeTransaction() throws ExceedsNegativeBalanceException, NonPositiveAmountException {
        if (amount <= 0) {
            throw new NonPositiveAmountException("Amount must be greater than zero");
        } else {
            if (currentBalance - amount < NEGATIVE_BALANCE) {
                throw new ExceedsNegativeBalanceException("Withdrawal transaction failed: The negative balance cannot go below " + NEGATIVE_BALANCE + "!");
            } else {
                currentBalance = currentBalance - amount;
            }

        }
        return currentBalance;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Withdrawal that = (Withdrawal) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (Double.compare(that.currentBalance, currentBalance) != 0) return false;
        return transactionDate != null ? transactionDate.equals(that.transactionDate) : that.transactionDate == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = transactionDate != null ? transactionDate.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(currentBalance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

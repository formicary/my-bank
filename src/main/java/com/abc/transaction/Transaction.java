package com.abc.transaction;

import com.abc.util.DateProvider;
import com.abc.util.ZeroAmountException;

import java.util.Date;

public abstract class Transaction {

    private final TransactionType type;
    private final double amount;
    private final Date transactionDate;

    public Transaction(TransactionType type, double amount) throws ZeroAmountException {
        if (amount <= 0.00) {
            throw new ZeroAmountException("Could not create transaction of amount: " + amount);
        }
        this.type = type;
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (type != that.type) return false;
        return transactionDate.equals(that.transactionDate);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type.hashCode();
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + transactionDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}

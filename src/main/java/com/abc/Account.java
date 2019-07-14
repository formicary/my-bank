package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransactions() {
        // Use deep copy to prevent alteration of original transaction list
        return new ArrayList<Transaction>(this.transactions);
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Amount to deposit must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount, Transaction.TransactionType.MANUAL));
        }
    }

    // Package-private method to be used by child classes
    void depositInterest(double amount) {
        this.transactions.add(new Transaction(amount, Transaction.TransactionType.INTEREST));
    }

    public void withdraw(double amount) throws IllegalArgumentException {
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Amount to withdraw must be greater than zero");
        } else if (currentBalance() < amount) {
            throw new IllegalArgumentException("Withdrawal amount exceeds available funds");
        } else {
            this.transactions.add(new Transaction(-amount, Transaction.TransactionType.MANUAL));
        }
    }

    public double currentBalance() {
        double amount = 0.0;

        for (Transaction t : this.transactions) {
            amount += t.getAmount();
        }

        return amount;
    }

    public double accountInterestEarned() {
        double interestAmount = 0.0;
        for (Transaction t : this.transactions) {
            if (t.getTransactionType() == Transaction.TransactionType.INTEREST) {
                interestAmount += t.getAmount();
            }
        }

        return interestAmount;
    }

    // To be implemented by child classes
    public abstract void addInterest();

    @Override
    public String toString() {
        StringBuilder accountStatement = new StringBuilder();

        // Translate to pretty account type
        if (this instanceof CheckingAccount) {
            accountStatement.append("Checking Account\n");
        } else if (this instanceof SavingsAccount) {
            accountStatement.append("Savings Account\n");
        } else if (this instanceof MaxiSavingsAccount) {
            accountStatement.append("Maxi Savings Account\n");
        }

        // List all transactions with their amounts and give the account total
        for (Transaction t : this.transactions) {
            double transactionAmount = t.getAmount();

            accountStatement.append("\t")
                    .append(transactionAmount < 0 ? "withdrawal" : "deposit")
                    .append(" ")
                    .append(Utils.toDollars(transactionAmount))
                    .append("\n");
        }

        accountStatement.append("\n\tTotal: ").append(Utils.toDollars(this.currentBalance())).append("\n");

        return accountStatement.toString();
    }
}

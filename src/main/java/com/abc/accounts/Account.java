package com.abc.accounts;

import com.abc.Customer;
import com.abc.Transaction;

import java.util.*;

public abstract class Account {

    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(-amount));
        }
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public double interestEarned() {
        Collections.sort(getTransactions());
        Deque<Transaction> transactionDeque = new ArrayDeque<Transaction>(getTransactions());

        Date firstTransaction = transactionDeque.getFirst().transactionDate;
        Calendar start = new GregorianCalendar();
        start.setTime(firstTransaction);

        Calendar end = Calendar.getInstance();

        double balance = 0.0;
        double totalInterest = 0.0;

        for (Date d = firstTransaction; start.before(end);
             start.add(Calendar.DAY_OF_YEAR, 1), d = start.getTime()) {

            if (!transactionDeque.isEmpty()){
                Date nextTransaction = transactionDeque.getFirst().transactionDate;
                if (d.after(nextTransaction) || d.equals(nextTransaction)) {
                    balance += transactionDeque.removeFirst().amount;
                }
            }
            double dayInterest = getDailyInterest(balance);
            totalInterest += dayInterest;
            balance += dayInterest;

        }
        return totalInterest;
    }

    protected abstract double getDailyInterest(double balance);

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public String getAccountStatement() {
        String s = getPrettyAccountType();

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Customer.Formatter.toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + Customer.Formatter.toDollars(total);
        return s;
    }

    protected List<Transaction> getTransactions() {
        return transactions;
    }

    protected abstract String getPrettyAccountType();

}

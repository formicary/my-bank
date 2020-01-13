package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Account {

    public List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<>();
    }

    abstract String getAccountString();

    abstract double calculateInterest(double amount, LocalDate dateInQuestion);

    public void deposit(double amount) {
        checkAmount(amount);
        transactions.add(new Transaction(amount));
    }

    //Overloaded to deposit at specific date for testing
    public void deposit(double amount, LocalDate date) {
        checkAmount(amount);
        transactions.add(new Transaction(amount, date));
    }

    public void withdraw(double amount) {
        checkAmount(amount);
        transactions.add(new Transaction(-amount));
    }

    //Overloaded to deposit at specific date for testing
    public void withdraw(double amount, LocalDate date) {
        checkAmount(amount);
        transactions.add(new Transaction(-amount, date));
    }

    private void checkAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    public double getInterestEarned(LocalDate finalDate) {
        if (transactions.size() < 1) {
            return 0;
        }
        return interestEarned(0, transactions.get(0).getTransactionDate(), 0, 0, finalDate);
    }

    public double sumTransactions() {
        Optional<Double> sum = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(Double::sum);

        return sum.orElse(0.0);
    }

    private double interestEarned(int transactionsPointer,
                                  LocalDate dateInQuestion,
                                  double totalBalance,
                                  double totalInterest,
                                  LocalDate finalDate) {
        //Base case, we have iterated through all dates and want to return the amount (compound interest)
        if (dateInQuestion.isAfter(finalDate) ||
                (dateInQuestion.equals(finalDate) && (transactionsPointer >= transactions.size()))) {
            return totalInterest;
        }
        //Check if there is another transaction
        if (transactionsPointer < transactions.size()) {
            //Get its date
            LocalDate nextTransactionDate = transactions.get(transactionsPointer).getTransactionDate();

            //If the current recursion date is the date of the next transaction, add the amount of this transaction
            if (dateInQuestion.equals(nextTransactionDate)) {
                totalBalance += transactions.get(transactionsPointer).getAmount();
                transactionsPointer++;
            }
        }

        double interest = calculateInterest(totalBalance, dateInQuestion);
        totalBalance += interest;
        totalInterest += interest;

        return interestEarned(transactionsPointer, dateInQuestion.plusDays(1), totalBalance, totalInterest, finalDate);
    }
}

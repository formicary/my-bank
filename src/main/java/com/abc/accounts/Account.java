package com.abc.accounts;

import com.abc.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.abc.util.StringFormatter.toDollars;
import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Account {

    protected List<Transaction> transactions;
    protected double intRate;
    protected double accrueRate;
    protected double balance;
    protected double totalEarnedInt;
    protected LocalDateTime dateOfLastUpdate;

    public Account() {
        this.transactions = new ArrayList<>();
        intRate = 0.0;
        balance = 0.0;
        accrueRate = 0.0;
        totalEarnedInt = 0.0;
        dateOfLastUpdate = LocalDateTime.now();
    }

    public Account(LocalDateTime date){
        this();
        dateOfLastUpdate = date;
    }

    public void deposit(double amount) {
       deposit(amount, LocalDateTime.now());
    }
    public void deposit(double amount, LocalDateTime date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(amount,date));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        withdraw(amount,LocalDateTime.now());
    }
    public void withdraw(double amount, LocalDateTime date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(-amount,date));
            balance -= amount;
        }
    }

    void setDate(LocalDateTime date){
        dateOfLastUpdate = date;
    }

    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    void updateAccount(Transaction transaction) {

        LocalDateTime date = transaction.getTransactionDate();
        updateAccount(date);
        addTransaction(transaction);
    }
    public void updateAccount(){
        updateAccount(LocalDateTime.now());
    }
    public void updateAccount(LocalDateTime date){

        int daysSinceLastUpdate = (int) DAYS.between(dateOfLastUpdate, date);
        setDate(date);
        acrrueAndCompBalance(daysSinceLastUpdate);
    }

    void acrrueAndCompBalance(int daysSinceLastUpdate) {
        while (daysSinceLastUpdate > 0) {
            compoundInterest();
            accrueInterest();
            daysSinceLastUpdate--;
        }
    }

    protected abstract void compoundInterest();
    protected abstract void accrueInterest();

    public double totalInterestEarned(){
        return totalEarnedInt;
    }

    public double sumTransactions() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getStatementInDollars(){

        StringBuilder s = new StringBuilder(this.toString() + "\n");
        //Now total up all the transactions
        transactions.forEach(t -> s.append("  ").append(t.getStatementInDollars()).append("\n"));
        s.append("Total ").append(toDollars(balance));
        return s.toString();
    }

    public double getIntRate() {
        return intRate;
    }

    public double getAccrueRate() {
        return accrueRate;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    @Override
    public String toString(){
        return "Account";
    }
}
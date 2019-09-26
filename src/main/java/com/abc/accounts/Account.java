package com.abc.accounts;

import com.abc.Transaction;
import com.abc.util.DateProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.abc.util.CurrencyFormatter.toDollars;
import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Account {

    List<Transaction> transactions;
    double intRate;
    double accrueRate;
    double balance;
    private LocalDateTime dateOfLastUpdate;

    public Account() {
        this.transactions = new ArrayList<>();
        intRate = 0.0;
        balance = 0.0;
        accrueRate = 0.0;
        dateOfLastUpdate = DateProvider.getInstance().now();
    }

    public Account(LocalDateTime date){
        this();
        dateOfLastUpdate = date;
    }

    public void deposit(double amount) {
       deposit(amount, DateProvider.getInstance().now());
    }
    public void deposit(double amount, LocalDateTime date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(amount,date));
        }
    }

    public void withdraw(double amount) {
        withdraw(amount,DateProvider.getInstance().now());
    }
    public void withdraw(double amount, LocalDateTime date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(-amount,date));
        }
    }

    void updateDate(LocalDateTime date){
        dateOfLastUpdate = date;
    }

    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    void updateAccount(Transaction transaction) {

        LocalDateTime date = transaction.getTransactionDate();
        updateAccount(date);
        addTransaction(transaction);
        balance += transaction.getAmount();
    }
    void updateAccount(){
        updateAccount(DateProvider.getInstance().now());
    }
    void updateAccount(LocalDateTime date){

        int daysSinceLastUpdate = (int) DAYS.between(dateOfLastUpdate, date);
        updateDate(date);
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
        updateAccount();
        return balance - sumTransactions();
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
        double total = 0.0;
        for (Transaction t : transactions) {
            s.append("  ").append(t.getStatementInDollars()).append("\n");
            total += t.getAmount();
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }

    public double getIntRate() {
        return intRate;
    }

    public void setIntRate(double intRate) {
        this.intRate = intRate;
    }

    public double getAccrueRate() {
        return accrueRate;
    }

    public void setAccrueRate(double accrueRate) {
        this.accrueRate = accrueRate;
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
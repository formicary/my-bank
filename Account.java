package com.abc;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Account {

    private static long accountsEverCreated = 1L;
    private long number;
    private String type;
    private List<Transaction> transactions;

    public Account(String type) {
        this.number = accountsEverCreated++;
        this.type = type;
        this.transactions = new ArrayList<>();
    }

    abstract double interestPaidOnAccount();        // calculates the total interest paid on account - depends on type of account
    abstract List<Transaction> buildTransactions(); // creates a list of all transactions including interest earnings deposited at the end of each day

    // deposits amount to account
    // true is returned if the deposit was successful
    // false is returned if negative or zero amount is passed as parameter
    public boolean deposit(double amount) {
        if(amount < 0d) {
            System.out.println("Cannot deposit a negative amount (use withdraw instead), no transaction has been created.");
            return false;
        }else if(amount > 0d) {
            addTransaction(amount);
            System.out.println("Successful deposit of "+amount+" to account number "+this.number+".");
            return true;
        }else {
            System.out.println("Deposit of 0, no transaction has been created.");
            return false;
        }
    }

    // withdraws amount from account
    // amount is accepted either as positive or negative value
    // true is returned if the withdrawal was successful
    // false is returned in case the current balance of the account is lower than the amount to be withdrawn
    public boolean withdraw(double amount) {
        if(abs(amount) > makeTotal()) {
            System.out.println("Insufficient funds to perform withdrawal, no transaction has been created.");
            return false;
        }
        if(amount < 0d) {
            addTransaction(amount);
            System.out.println("Successful withdrawal of "+-amount+" from account number "+this.number+".");
            return true;
        }else if(amount > 0d) {
            addTransaction(-amount);
            System.out.println("Successful withdrawal of "+amount+" from account number "+this.number+".");
            return true;
        }else {
            System.out.println("Withdrawal of 0, no transaction has been created.");
            return false;
        }
    }

    // creates a new transaction based on the amount passed as parameter, date of transaction is generated from the moment of execution
    private void addTransaction(double amount) {
        transactions.add(new Transaction(amount));
    }

    // calculates the current balance of the account as a sum of all interest earnings up to the moment of execution and the sum of all customer's transactions
    public double makeTotal() {
        return interestPaidOnAccount()+sumCustomerTransactions();
    }

    // gets a formatted output of all transactions including interest earning deposits at the end of each day for a customer's account as String
    public String showAllTransactions() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM yyyy");
        StringBuilder result = new StringBuilder("Account no.: " + this.getNumber());
        result.append("\n");
        result.append("Account type: " + this.getType());
        result.append("\n");
        result.append("List of transactions:");
        List<Transaction> transactions = new ArrayList<>(this.buildTransactions());
        if (!transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
                result.append("\n" + (transaction.getAmount() > 0d ? "deposit of " : "withdrawal of ") + Math.abs(transaction.getAmount()) + " on " + transaction.getDate().format(formatter));
            }
            return result.toString();
        } else {
            return result.append("\nNo transactions made on this account so far.").toString();
        }
    }

    // gets sum of customer's transactions only for an account, interest earnings are excluded
    // return value is a double containing the sum of transactions
    private double sumCustomerTransactions() {
        double sum = 0d;
        for(Transaction transaction:transactions) {
            sum +=transaction.getAmount();
        }
        return sum;
    }

    public String getType() {
        return type;
    }

    public long getNumber() {
        return number;
    }

    // creates a copy of List, so no transactions can be added or deleted directly
    // returns a List of transactions referencing the original Transaction instances
   /* public List<Transaction> getTransactions() {
        List<Transaction> transactionsCopy = new ArrayList<>(transactions);
        return transactionsCopy;
    }*/

   // only temporary use for testing purposes
   public List<Transaction> getTransactions() {
       return transactions;
   }
}
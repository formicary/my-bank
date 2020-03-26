package com.accenture.mybank;

import java.util.ArrayList;
import java.util.List;
import com.accenture.mybank.Transaction;
import com.accenture.mybank.accounts.Account;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    /**
     * This method opens account for a particular customer
     * @param account
     * @return
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * This method gets number of accounts belonging to particular customer
     * @return total accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * This method gets total interest earned by the customer on all his accounts
     * @return total interest paid by bank to customer
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts){
            total += account.totalInterests();
        }
        return total;
    }

    /**
     * This method gets statement of all the accounts for a particular customer
     * @return statement
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        double total = 0.0;
        statement.append("Statement for ").append(name).append("\n");
        for (Account account : accounts) {
            statement.append("\n").append(statementForAccount(account)).append("\n");
            total += account.sumTransactions();
        }
        statement.append("\n").append("Total: ").append(total);

        return statement.toString();
    }

    /**
     * This method gets statement of particular account of the customer
     * @param account
     * @return statement of account
     */
    private String statementForAccount(Account account) {
        StringBuilder text = new StringBuilder();
        text.append(account.getType());

        //Now total up all the transactions
        double totalAmount = 0.0;
        for (Transaction transaction : account.transactions) {
            text.append("  ").append(transaction.amount < 0 ? "withdrawal" : "deposit");
            text.append(" ").append(transaction.amount).append("\n");

            totalAmount += transaction.amount;
        }

        text.append("Total ").append(totalAmount);

        return text.toString();
    }

    /**
     * This method transfer money in between the accounts of particular customer
     * @param from
     * @param to
     * @param amount
     * @throws IllegalArgumentException
     */
    public void transferMoneyTo(Account from, Account to, double amount) throws IllegalArgumentException{

        //customer can transfer only through his accounts
        if(this.accounts.contains(from) && this.accounts.contains(to)){

            from.withdraw(amount);
            to.deposit(amount);
        }
        else {
            throw new IllegalArgumentException("From or To account does not belong to customer");
        }
    }
}
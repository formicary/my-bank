package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

import static java.lang.Math.abs;

public class Customer {
    private String name; // Name of the customer
    private List<Account> accounts; // List of customer's accounts

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void transfer(Account fromAccout, Account toAccount, double amount){
        if (amount <= 0) throw new IllegalArgumentException("amount must be greater than zero");
        if (amount > fromAccout.getBalance()) throw new IllegalArgumentException("account does not have enough funds");
        fromAccout.withdraw(amount);
        toAccount.deposit(amount);
    }

    public double totalInterestEarned() {
        return totalInterestEarned(LocalDate.now());
    }

    public double totalInterestEarned(LocalDate date) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(date);
        return total;
    }

    public String getStatement() {
        return getStatement(LocalDate.now());
    }

    public String getStatement(LocalDate date) {
        // Returns the bank statement for a customer, the data parameter should only be used for testing purposes.
        StringBuilder statement = new StringBuilder("Statement for " + name + " on date " + date + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n")
                    .append(statementForAccount(a, date))
                    .append("\n");
            total += a.getBalance(date);
        }
        statement.append("\nTotal In All Accounts ")
                .append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account a, LocalDate date) {
        StringBuilder s = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                s.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        for (Transaction t : a.transactions) {
            s.append(("  "))
                    .append(t.transactionDate)
                    .append(t.amount < 0 ? " withdrawal" : " deposit")
                    .append(" ")
                    .append(toDollars(t.amount))
                    .append("\n");
        }
        s.append(("\n  "))
                .append("interests ")
                .append(toDollars(a.interestEarned(date)))
                .append("\n");
        s.append("Total ")
                .append(toDollars(a.getBalance(date)));
        return s.toString();
    }

    private String toDollars(double d){
        System.out.println(d);
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US); // Ensures we have a right USD format
        return nf.format(abs(d));
    }
}

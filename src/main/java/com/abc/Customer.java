package com.abc;

import com.abc.utils.Formatting;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

class Customer {
    private String name;
    private List<Account> accounts;

    Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    Customer openAccount(Account account) {
        accounts.add(account);
        account.setHolder(this);
        return this;
    }

    int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Calculates the total amount of interest this customer has earned across all of their accounts.
     * @return the total interest earned
     */
    double calcInterest() {
        double total = 0;

        for (Account a : accounts) total += a.calcInterest();

        return total;
    }

    /**
     * Executes a bank transfer between accounts held by the customer.
     * @param amount the amount to transfer across accounts
     * @param fromAccount the account, from which, to withdraw the funds
     * @param toAccount the account, to which, to deposit the funds
     */
    void transfer(double amount, Account fromAccount, Account toAccount) {
        if (accounts.size() < 2)
            throw new IllegalStateException("This customer does not have at least 2 accounts.");
        else if (amount < 0)
            throw new IllegalArgumentException("A negative amount cannot be transferred");

        fromAccount.execTransfer(amount, toAccount);
    }

    /**
     * Generates a statement for this customer, composing of statements for each account they hold at the bank.
     * @return a complete customer statement
     */
    String genStatement() {
        double total = 0.0;
        StringBuilder statement = new StringBuilder();

        statement.append("Statement for ");
        statement.append(name);
        statement.append('\n');

        for (Account account : accounts) {
            statement.append('\n');
            statement.append(account.genStatement());
            total += account.getBalance();
        }
        statement.append("\nCross-account total: ");
        statement.append(Formatting.toDollars(total));
        return statement.toString();
    }

    String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}

package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public List<Account> getAccounts() {        // new method
        return accounts;
    }

    public Account getAccount(int index) {      // new method
        if (index > accounts.size()) {
            throw new IndexOutOfBoundsException("You have not opened " + formatPlural(index, "account"));
        }
        return accounts.get(index-1);
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {      // edited
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null.");
        }
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {

        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }

    public String getStatement() {      // edited
        StringBuilder statement = new StringBuilder();

        statement.append("Statement for " + name + "\n");

        double total = 0.0;
        for (Account account : accounts) {
            statement.append("\n" + statementForAccount(account) + "\n");
            total += account.sumTransactions();
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account account) {       // edited
        StringBuilder statementForAccount = new StringBuilder();

        statementForAccount.append(account.getAccountType().getDescription() + "\n");

        double total = 0.0;
        List<Transaction> tempList = account.getTransactions();
        for (Transaction transaction : tempList) {
            statementForAccount.append("  " + transaction.getType() + " " + toDollars(transaction.getAmount()) + "\n");
            total += transaction.getAmount();
        }
        statementForAccount.append("Total " + toDollars(total));
        return statementForAccount.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", Math.abs(d));
    }

    private String formatPlural(int number, String word) { // edited name of method
        return number + " " + (number == 1 ? word : word + "s");
    }

    // Additional feature - customer can transfer between their accounts
    public void transferToMyAccount(Account fromAccount, Account toAccount, double amount) {
        if (!accounts.contains(fromAccount) ) {
            throw new IllegalArgumentException("You do not own payers account.");
        }
        if (!accounts.contains(toAccount) ) {
            throw new IllegalArgumentException("You do not own beneficiary account.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        if (amount == 0) {
            throw new IllegalArgumentException("Amount must be higher than 0.");
        }
        if (amount > fromAccount.sumTransactions()) {
            throw new IllegalArgumentException("Not enough money for transfer.");
        }
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}

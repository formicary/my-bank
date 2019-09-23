package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
    * Gets the name of the customer.
    *
    * @return The name of the customer, as String.
    */
    public String getName() {
        return name;
    }

    /**
    * Gets the accounts opened by the customer.
    *
    * @return The list of accounts.
    */
    public List<Transaction> getAccounts() {
        return accounts;
    }

    /**
    * Gets how many accounts the customer has opened so far.
    *
    * @return The number of accounts of the customer.
    */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
    * Adds a new account to the list of accounts the customer has.
    */
    public void openAccount(Account account) {
        accounts.add(account);
    }

    /**
    * Calculates the interest the customer has earned from all their accounts.
    *
    * @return The sum of the interests from each account.
    */
    public double totalInterestEarned() {
        double total = 0.0d;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
    * Formats and produces a statement based on all accounts.
    *
    * @return The statement as a String.
    */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0d;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + dollarFormat(total);
        return statement;
    }

    /**
    * Formats and produces a statement for a specific account.
    *
    * @return The account statement as a String.
    */
    private String statementForAccount(Account a) {
        String s = "";

       // Translate to account type name
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        // Total up all the transactions
        double total = 0.0d;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + dollarFormat(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + dollarFormat(total);
        return s;
    }

    /**
    * Transfers the specified amount between two accounts of the customer.
    *
    * @param fromAccount The account from which the amount will be withdrawn.
    * @param toAccount The account in which the amount will be deposited.
    * @param amount The amount to be transferred.
    */
    private void transferBetweenAccounts(Account fromAccount, Account toAccount, double amount){
        if (amount > 0) {    
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        } else {
            throw new IllegalArgumentException("The transfer amount cannot be negative.");
        }
    }

    /**
    * Formats a double into a dollar String.
    *
    * @return The formatted String.
    */
    private String dollarFormat(double d) {
        return String.format("$%,.2f", abs(d));
    }

}

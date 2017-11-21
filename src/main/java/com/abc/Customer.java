package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Customer constructor
     * @param name The customer name.
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * GetName method gets the name of the customer.
     * @return Returns the name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * openAccount method adds an account to the list of the accounts the customer have.
     * @param account The account to be added.
     * @return Returns the current instance of the customer.
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * GetNumberOfAccounts gets the number of accounts for the customer.
     * @return Returns the number of accounts for the customer.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * TotalInterestEarned calculates the total interest earned by the customer for all of its account.
     * @return Returns the total interest earned by the customer for all of its account.
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * GetStatement returns a summary of statement for all accounts of the customer.
     * @return Returns the summary statement for all accounts of the customer.
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumOfAllTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /**
     * StatementForAccount gets the statement summary for the account.
     * @param a The account that needs its summary statement created.
     * @return Returns the statement summary for the account.
     */
    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        if(a instanceof CheckingAccount)
            s += "Checking Account\n";
        else if(a instanceof SavingsAccount)
            s += "Savings Account\n";
        else if(a instanceof MaxiSavingsAccount)
            s += "Maxi Savings Account\n";

        //Now total up all the transactions
        double total = 0.0;
        double amount;
        for (Transaction t : a.getTransactions()) {
            amount = t.getAmount();
            s += "  " + (amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(amount) + "\n";
            total += amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    /**
     * ToDollars method transforms the number to its dollar form.
     * @param d The number to be transformed into dollars.
     * @return Returns the number in its dollar form.
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

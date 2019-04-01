package com.abc;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import static java.lang.Math.abs;

/**
 * Class to represent a customer of the bank.
 */
@Data
public class Customer {
    private String name;
    private List<Account> accounts = new ArrayList<Account>();

    /**
     * Creates a new customer record.
     * @param name the name of the customer
     */
    public Customer(String name) {
        this.name = name;
    }

    /** Opens an account for the customer.
     * 
     * @param account the account type to be opened
     * @return the customer record
     */
    public Customer openAccount(AccountType accountType) {
        accounts.add(new Account(accountType));
        return this;
    }

    /**
     * Returns the number of accounts opened for the customer.
     * @return the number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Counts the interest earned by the customer.
     * @return the sum of interests on the customer's accounts
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * Gets the statement for all the accounts of the customer.
     * @return the string representation for the accounts
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder();
        s.append(a.getAccountType().getName()+"\n");
        for (Transaction t : a.getTransactions()) {
            s.append("  " + t.getType() + " " + toDollars(t.getAmount()) + "\n");
        }
        s.append("Total " + toDollars(a.sumTransactions()));
        return s.toString();
    }   

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    /**
     * Transfers a sum between two accounts of the customer.
     * @param sourceAccount the source account
     * @param targetAccount the target account
     * @param sum the sum to be transferred
     * @throws IllegalArgumentException if any of the input parameters are incorrect
     */
    public void transfer(int sourceAccount, int targetAccount, double sum) throws IllegalArgumentException{
        if(sourceAccount>=accounts.size() || sourceAccount < 0) {
            throw new IllegalArgumentException("Invalid source account index!");
        }
        if(targetAccount>=accounts.size() || targetAccount < 0) {
            throw new IllegalArgumentException("Invalid target account index!");
        }
        if(sum < 0) {
            throw new IllegalArgumentException("Only positive values can be transferred!");
        }
        accounts.get(sourceAccount).withdraw(sum);
        accounts.get(targetAccount).deposit(sum);
    }

}

package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;


/* -- Customer Class --
    An object that represents a customer.
        A customer has the following methods:
            -Customer(String name) - Creats a customer with name 'name'
            -String getName() - Accessor - Returns name of customer
            -Customer openAccount(Account account) - Opens specified account
            for the customer. Returns the customer. 
            -int getNumberOfAccounts() - Accessor - Returns number of accounts
            that the customer owns.
            -double totalInterestEarned() - Utility Accessor - Returns total
            interest the customer will earn across all accounts. 
            -String generateStatement() - Returns a textual statement formatted
            in a stylish way of all the accounts and transactions under the
            customer.

*/
public class Customer {
    private final String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    // Returns the customers name
    public String getName() {
        return this.name;
    }

    // Opens an account on the customer. Returns the object?
    // Not exactly sure why, perhaps review with developer. 
    public Customer openAccount(Account account) {
        this.accounts.add(account);
        return this;
    }

    // Returns length of accounts array list, i.e. the number of accounts 
    // associated to the customer. 
    public int getNumberOfAccounts() {
        return this.accounts.size();
    }

    // Returns the total amount of interest the customer will earn across
    // all accounts. 
    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }

    // Generates a textual statement of some predetermined format 
    // of the amount in the customer's accounts. 
    public String generateStatement() {
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

    // Private (utility) function. Generates a text statement for the account. 
    private String statementForAccount(Account inputAccount) {
        String returnForm = "";

       //Translate to pretty account type
        switch(inputAccount.getAccountType()){
            case CHECKING:
                returnForm += "Checking Account\n";
                break;
            case SAVINGS:
                returnForm += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                returnForm += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : inputAccount.transactions) {
            returnForm += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        returnForm += "Total " + toDollars(total);
        return returnForm;
    }

    // Private (utility) function. Converts given amount to dollars. 
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

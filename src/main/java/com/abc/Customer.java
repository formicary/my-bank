package com.abc;

import com.abc.accounts.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A Customer class that will hold all the information about each customer.
 * Each customer will have a unique ID and can open any amount of accounts.
 */

public class Customer {

    private String name;
    private int customerID;
    private List<Account> accounts;

    /**
     * A constructor for the customer class.
     * @param name This is the name of the new customer.
     * @param customerID A unique ID to identify this customer.
     */
    public Customer(String name, int customerID) {
        this.name = name;
        this.customerID = customerID;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Getter for the customer name
     * @return Name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the customer ID
     * @return ID of the customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * A method to fetch one of the customers account.
     * @param accountID The account being fetched.
     * @return One of the customer accounts.
     * @throws IllegalArgumentException Throws an error if the account doesn't exist.
     */

    public Account getAccount(int accountID) throws IllegalArgumentException {

        for (Account account : accounts) {
            if (account.getAccountID() == accountID) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account doesn't exist");
    }

    /**
     * Method to open a new bank account from the 3 available options.
     * @param type The 3 available options
     * @param accountID A uniqueID for the new account
     * @throws IllegalArgumentException If an acocunt with that accountID already exists.
     */

    public void openAccount(AccountType type, int accountID) throws IllegalArgumentException {
       try{
           getAccount(accountID);
           throw new IllegalArgumentException("Account already exists");

       } catch (IllegalArgumentException e) {
           if (type == AccountType.Checking) {
               accounts.add(new CheckingAccount(accountID));

           } else if (type == AccountType.Savings) {
               accounts.add(new SavingsAccount(accountID));

           } else if (type == AccountType.MaxiSavings) {
               accounts.add(new MaxiSavingsAccount(accountID));
           }
       }
    }

    /**
     * Method to count the number of accounts the customer has opened.
     * @return Number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Method that allows the customer money between his accounts. It does this by withdrawing
     * from account x and depositing into account y. On the transaction it will be stamped Sent to & Received from.
     * @param fromAccount Account money being sent from.
     * @param toAccount Account money being sent to.
     * @param amount The amount to send.
     * @throws IllegalArgumentException If the customer tries to send money to himself.
     */

    public void transferBetweenAccounts(Account fromAccount, Account toAccount, double amount) throws IllegalArgumentException {

        if (fromAccount.getAccountID() != toAccount.getAccountID()) {
            fromAccount.withdrawAmount(amount, "Sent to " + toAccount.getAccountID());
            toAccount.depositAmount(amount, "Received from " + fromAccount.getAccountID());
        } else {
            throw new IllegalArgumentException("Unable to transfer to transfer to same account");
        }
    }

    /**
     * Method to calculate the total interest the user has earned from all his accounts.
     * @return The interest earned.
     */
    public double totalInterestEarned() {

        double interestPaid = 0.00;

        for (Account account : accounts) {
            interestPaid += account.compoundDailyInterest();
        }
        return interestPaid;
    }

    /**
     * toString method to return the information about the customer.
     * @return name, number of open accounts and transactions for each account;
     */
    public String toString() {
        String toString = "Account information for: " + name + "\n" + "Open accounts: " + getNumberOfAccounts() + "\n";

        for (Account account : accounts) {
            toString += "\n" + account.toString();
        }
        return toString;
    }

    /**
     * Method to print out the customer information, calls the toString method.
     */
    public void getCustomerInformation() {
        System.out.println(toString());
    }
}

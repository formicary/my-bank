package com.abc;

import java.util.Map;
import java.util.HashMap;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private Map<String, Account> accounts;

    /**
     * Constructor for Customer class
     * @param name the name of the customer
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Method used to open account under a Customer
     * @param accName a string name for the account for easier readability
     * @param account the Account object to be added to the ArrayList of customer's accounts
     * @return reference to self object
     */
    public Customer openAccount(String accName, Account account){
        accounts.put(accName, account);
        return this;
    }

    public int getNumberOfAccounts(){
        return accounts.size();
    }

    /**
     * Method used to calculate the total amount of interest earned across all accounts of a customer
     * @return total amount of interest earned by a customer
     */
    public double totalInterestEarned(){
        double total = 0;
        for (Account account : accounts.values())
            total += account.interestEarned();

        return total;
    }

    /**
     * Method used to send money between one client's account
     * @param fromAccID string of the account name from which to send money
     * @param toAccID string of the account name to which the money is being sent
     * @param amount value of currency involved in the transaction
     */
    public void transferBetweenOwnAccounts(String fromAccID, String toAccID, double amount){
        Account fromAcc = getAccount(fromAccID);
        Account toAcc = getAccount(toAccID);
        fromAcc.transferTo(toAcc, amount, false);
    }

    private Account getAccount(String accID){
        return accounts.get(accID);
    }

    /**
     * Method used to output a statement of one customer's accounts in a readable format
     * @return a string representing the customer's statement
     */
    public String getStatement(){
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account account : accounts.values()){
            statement += "\n" + account.getStatement() + "\n";
            total += account.sumTransactions();
        }

        statement += "\nTotal In All Accounts " + toDollars(total);

        return statement;
    }

    /**
     * Method to format a number representing amount of currency to a string with a dollar ($) sign
     * @param d amount of dolars (stored as a number) to be formatted as a string with dollar ($) sign
     * @return string containing the dollar ($) sign and the amount involved
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

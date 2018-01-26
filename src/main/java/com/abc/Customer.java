package com.abc;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private String id;
    private List<Account> accounts;

    /** Customer object constructor.
     * Other properties (firstName, lastName, dateOfBirth, etc.) could be added
     * in order to fully describe a customer, left only two for simplicity.
     *
     * @param name name of the customer
     * @param id unique identifier
     */
    public Customer(String name, String id){
        if (name.length()==0){
            throw new IllegalArgumentException("Customer Name cannot be empty!");
        }
        if(!isValidID(id)){
            throw new IllegalArgumentException("Identifier must be a string of 3 bytes in hex e.g. aa0001");
        }

        this.name = name;
        this.id = id;
        this.accounts = new ArrayList<Account>();
    }

    /** Checks if ID is valid i.e. contains hex numbers
     *
     * @param validID ID being checked
     * @return True/False (Is valid or not)
     */
    private Boolean isValidID(String validID){
        return validID.matches("[0-9A-Fa-f]+");
    }

    /** Getter method for the name of the customer
     *
     * @return name of the customer
     */
    public String getName() {
        return name;
    }

    /** Getter method for the identifier of the customer
     *
     * @return identifier of the customer
     */
    public String getIdentifier() {
        return id;
    }

    /** Opens account for a customer
     *
     * @param account Account object that is being opened
     * @return current instance of the Customer object
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /** Closes specified account for a customer
     *
     * @param account Account object that is being closed
     * @return current instance of the Customer object
     */
    public Customer closeAccount(Account account) {
        accounts.remove(account);
        return this;
    }

    /** Number of accounts opened for the Customer getter method
     *
     * @return number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /** Calculates total interest earned from all accounts
     *
     * @return total interest earned
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /** Generates a statement for all accounts
     *
     * @return statement
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.statementForAccount() + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /** Formats a money value to dollars
     *
     * @param amount amount to be formatted
     * @return statement
     */
    private String toDollars(double amount){
        return String.format("$%,.2f", abs(amount));
    }

    /** Transfers a certain amount between two accounts.
     *
     * @param senderAcc account that money is transferred from
     * @param receiverAcc account that money is transferred to
     */
    public void transfer (Account senderAcc, Account receiverAcc, double amount){
        if(senderAcc != receiverAcc) {
            senderAcc.withdraw(amount);
            receiverAcc.deposit(amount);
        }
    }
}

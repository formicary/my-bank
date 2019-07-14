package com.abc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer {

    private String firstName;
    private String lastName;
    private Map<Integer, Account> accounts;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new HashMap<Integer, Account>();
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public ArrayList<Account> getAccountsList() {
        // Return the accounts in the HashMap as an ArrayList that can be iterated through
        return (ArrayList<Account>) this.accounts.values();
    }

    public int getNumberOfAccounts() {
        return this.accounts.size();
    }

    public String getStatement() {
        StringBuilder customerStatement = new StringBuilder();
        double total = 0.0;

        // Start with customer name
        customerStatement.append("Statement for ")
                .append(this.firstName)
                .append(" ")
                .append(this.lastName)
                .append(":\n\n");

        if (this.accounts.isEmpty()) {
            customerStatement.append("No accounts");
            return customerStatement.toString();
        } else {
            // For each account in the HashMap, print the key value and then the account details
            for (Map.Entry<Integer, Account> mapEntry : this.accounts.entrySet()) {
                Account currentAccount = mapEntry.getValue();
                customerStatement.append(mapEntry.getKey())
                        .append(": ")
                        .append(currentAccount.toString())
                        .append("\n");

                // Keep a running total of all accounts
                total += currentAccount.currentBalance();
            }

            customerStatement.append("Total In All Accounts: ").append(Utils.toDollars(total));
            return customerStatement.toString();
        }
    }

    public void openAccount(Account account) {
        // Add the new account to the HashMap and automatically assign its key pair
        this.accounts.put(this.accounts.size() + 1, account);
    }

    public void transferBetweenAccounts(int withdrawAccountKey, int depositAccountKey, double amount)
            throws IllegalArgumentException {
        // Check that the keys for the HashMap are valid
        if (this.accounts.containsKey(withdrawAccountKey) && this.accounts.containsKey(depositAccountKey)) {
            Account withdrawAccount = this.accounts.get(withdrawAccountKey);
            Account depositAccount = this.accounts.get(depositAccountKey);

            // Check that the amount is positive and that the withdrawal account contains the requested funds
            if (amount <= 0.0 || withdrawAccount.currentBalance() < amount) {
                throw new IllegalArgumentException("Invalid withdrawal amount; " +
                        "must be a positive value and funds " +
                        "must be available in withdrawal account");
            } else {
                // If both checks are validated, perform the transaction
                withdrawAccount.withdraw(amount);
                depositAccount.deposit(amount);
            }
        } else {
            throw new IllegalArgumentException("Invalid account key was provided");
        }
    }

    public double totalInterestEarned() {
        double total = 0.0;

        for (Account a : this.accounts.values()) {
            total += a.accountInterestEarned();
        }

        return total;
    }

    @Override
    public String toString() {
        return this.getName() + " (" + Utils.formatWordPlural(this.getNumberOfAccounts(), "account") + ")";
    }
}

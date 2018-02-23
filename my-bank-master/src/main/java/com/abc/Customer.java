package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private ArrayList<Account> accounts;
    // Changed as we want to limit LinkedLists, as they will have O(n) time complexity for retrieval.

    /**
     * This constructor initialises a customer to having a certain name and no accounts
     * @param name The customer's name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * This method gets the customer's name
     * @return The customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method opens an account for the customer.
     * @param account An account to be opened
     */
    public void openAccount(Account account) {
        accounts.add(account);
    }

    /**
     * This method returns the number of accounts the customer has to their name within the bank
     * @return The number of accounts the customer has registered with the bank
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * This method returns the interest this customer has collected from the bank across accounts.
     * @return The interest this customer has collected from the bank across accounts.
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getGrossInterestEarned();
        return total;
    }

    /**
     * If a customer can successfully withdraw from one of his accounts, he may deposit to the other.
     * @param fromAccount The account money leaves
     * @param toAccount The account money enters
     * @param amount The amount of money transfered.
     */
    public void transfer(Account fromAccount, Account toAccount, double amount){
        if(withdraw(amount, fromAccount))
            deposit(amount, toAccount);
    }

    /**
     * A customer may attempt to withdraw an amount of money from an account
     * @param amount Amount to be withdrawn
     * @param a Account to be withdrawn from
     */
    public boolean withdraw(double amount, Account a){
        try {
            a.withdraw(amount);
            return true;
        }catch (IllegalArgumentException exc){
            exc.printStackTrace();
            return false;
        }
    }

    /**
     * A customer may attempt to deposit an amount of money to an account
     * @param amount Amount to be deposited
     * @param a Account to be deposited to
     */
    public void deposit(double amount, Account a){
        try {
            a.deposit(amount);
        }catch (IllegalArgumentException exc){
            exc.printStackTrace();
        }
    }

    /**
     * This method gets a a statement of all their transactions across all the accounts.
     * @return
     */
    public String getStatement() {
        /*Note: I use StringBuilder instead of StringBuffer as I assume this is not a concurrent application,
            so no synchronisation.
        */

        StringBuilder statement = new StringBuilder("");
        statement.append("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n" + statementForAccount(a) + "\n");

            /*
                I changed this to just take the balance in the account because of the phrasing "Total In All Accounts"
                implies this just wants the current total money in the accounts. Hence an O(n) - n being num transactions -
                query of all transactions is superfluous.
            */
            total += a.getCurrentBalance();
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));
        return statement.toString();
    }

    /**
     * This helper method produces a statement for a given account.
     * @param a The account for which the statement is to be produced.
     * @return The statement produced given certain account details.
     */
    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder("");

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                s.append("Checking Account\n");
                break;
            case SAVINGS:
                s.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        if (a.getTransactions() !=null)
            for (Transaction t : a.getTransactions()) {
                s.append("  ");
                s.append(t.getAmount() < 0 ? "withdrawal" : "deposit");
                s.append(" " + toDollars(t.getAmount()) + "\n");
                total += t.getAmount();
            }
        s.append("Total " + toDollars(total));
        return s.toString();
    }

    /**
     * This helper method creates a string containing the total money in the account in dollars
     * @param d The total of money in the account.
     * @return The amount of money in dollars.
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

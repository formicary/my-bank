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

    public String getName() {
        return name;
    }

    public void openAccount(Account account) {
        if (accounts.contains(account))
            throw new UnsupportedOperationException("This account has already been added to the list of users' " +
                    "accounts");
        else {
            accounts.add(account);
            account.linkAccWithCustomer();
        }
    }

    /** This method transfers money from one account to another of a customer.
     *
     * @param accountFrom The account we are transferring from.
     * @param accountTo The account we are transferring to.
     * @param amount The amount to be transferred between accounts.
     */

    public void transferBetweenAccount(Account accountFrom, Account accountTo, double amount) {
        if (accountFrom == accountTo) {
            throw new IllegalArgumentException("The account from which the amount would be taken is the same" +
                    "as the account ");
        }

        else if (!accounts.contains(accountFrom) || !accounts.contains(accountTo)) {
            throw new IllegalArgumentException("Both accounts need to be held by the same customer");
        }

        else if (amount <= 0) {
            throw new IllegalArgumentException("The amount transferred needs to be positive");
        }

        else {
            accountFrom.withdraw(amount);
            accountTo.deposit(amount);
        }

    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

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
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
//        double total = 0.0;
//        for (Transaction t : a.transactions) {
//            s += "  " + (t.< 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
//            total += t.amount;
//        }
//        s += "Total " + toDollars(total);
//        return s;

        return "";
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

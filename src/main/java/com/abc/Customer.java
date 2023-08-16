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
     * @return String customer name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param account
     * @return account object
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * 
     * @return number of accounts (int)
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * 
     * @return total interest
     */

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            if (a.getAccountType() == 0)
                total += a.interestEarnedChecking();
            else if (a.getAccountType() == 1)
                total += a.interestEarnedSavings();
            else
                total += a.interestEarnedMaxiSavings();
        return total;
    }

    /**
     * 
     * @return statement across accounts (String)
     */

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getAccountBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /**
     * 
     * @param account
     * @return statement for individual account (String)
     */

    private String statementForAccount(Account a) {
        String s = "";

        // Translate to pretty account type
        switch (a.getAccountType()) {
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

        // Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    /**
     * 
     * @param amount
     * @return formatted String to dollars
     */
    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

}

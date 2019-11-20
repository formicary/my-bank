package com.abc;

import com.abc.utils.Formatting;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

class Customer {
    private String name;
    private List<Account> accounts;

    Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Calculates the total amount of interest this customer has earned across all of their accounts.
     * @return the total interest earned
     */
    double calcInterest() {
        double total = 0;

        for (Account a : accounts) total += a.calcInterest();

        return total;
    }


    /**
     * Generates a statement for this customer, composing of statements for each account they hold at the bank.
     * @return a complete customer statement
     */
    String genStatement() {
        double total = 0.0;
        StringBuilder statement = new StringBuilder();

        statement.append("Statement for ");
        statement.append(name);
        statement.append('\n');

        for (Account account : accounts) {
            statement.append('\n');
            statement.append(account.genStatement());
            total += account.getBalance();
        }
        statement.append("\nCross-account total: ");
        statement.append(Formatting.toDollars(total));
        return statement.toString();
    }

    String getName() {
        return name;
    }
}

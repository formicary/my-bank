package com.abc.customer;

import com.abc.account.Account;
import com.abc.Money;
import com.abc.Transaction;

;
import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Generates a statement for a customers set of accounts
 */
public class Statement {
    private Customer customer;

    public Statement(Customer customer){
        this.customer = customer;
    }

    /**
     * @return statement for ALL of a customers accounts
     */
    public String getStatement() {
        ArrayList<Account> accounts = customer.getAccounts();
        String statement = null;
        statement = "Statement for " + customer.getName() + "\n";
        Money total = new Money("0");
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add(a.getBalance());
        }
        statement += "\nTotal In All account " + total.toString();
        return statement;
    }

    /**
     * @param a account
     * @return statement for one account
     */
    private String statementForAccount(Account a) {
        String s = "";
        s = s + a.getName() + "\n";
        //Now total up all the transactions
        Money total = new Money("0");
        for (Transaction t : a.getTransactions()) {
            s += "  " + t.getType() + " " + t.getAmount().toString() + "\n";
            total = total.add(t.getAmount());
        }
        s += "Total " + total.toString();
        return s;
    }
}

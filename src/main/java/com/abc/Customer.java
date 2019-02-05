package com.abc;

import com.abc.Exceptions.NotEnoughFundsAvailableException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 *
 * @author t-pizzle
 */
public class Customer {

    private String name;
    private List<Account> accounts;

    /**
     *
     * @param name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param account
     * @return
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     *
     * @return
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     *
     * @return
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
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
        switch (a.getAccountType()) {
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    /**
     * 
     * @param from account to send funds from
     * @param to account to funds to
     * @param amount value of money to be send
     * @return boolean true, if funds successfully sent
     * @throws NotEnoughFundsAvailableException 
     */
    public boolean transferFundsToAccount(Account from, Account to, double amount) throws NotEnoughFundsAvailableException {

        // If the customer owns both accounts.
        if (accounts.contains(from) && accounts.contains(to) && from.hasEnoughFunds(amount)) {
            from.withdraw(amount);
            to.deposit(amount);
            return true;
        }
        return false;

        }
    }


package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * This is a class for Customer. It contains a list of accounts belongs to the customer and the customer's name.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version   03/05/2018
 */
public class Customer {

    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * This is a getter for the accounts.
     * @return A list of accounts.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * This is a method for transfer funds between two accounts of the same customer.
     * @param a The account to be withdrawn.
     * @param b The account to be deposited.
     * @param amount The amount of money to be transferred.
     */
    public void transferBetweenAccounts(Account a, Account b, double amount) {
        if (amount < a.sumTransactions().doubleValue()) {
            a.withdraw(new BigDecimal(amount));
            b.deposit(new BigDecimal(amount));
        } else {
            System.out.println("insufficient fund in the account");
        }
    }

    /**
     * This method gets the name of the customer
     * @return The name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * This method allows the customer to open an account
     * @param account An account
     * @return The customer
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * This methods gets the number of accounts the customer has.
     * @return The total number of accounts.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = new BigDecimal(0);
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        return total;
    }

    /**
     * This method returns a statement of the customer.
     * @return A statement of the customer.
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        BigDecimal total = new BigDecimal(0);
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add(a.sumTransactions());
        }
        statement += "\nTotal In All Accounts " + toDollars(total.doubleValue());
        return statement;
    }

    /**
     * This method returns a statement of an account.
     * @param a An account
     * @return A statement of the account.
     */
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
        BigDecimal total = new BigDecimal(0);
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount().doubleValue() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount().doubleValue()) + "\n";
            total = total.add(t.getAmount());
        }
        s += "Total " + toDollars(total.doubleValue());
        return s;
    }

    /**
     * This method formatted the dollar.
     * @param d the amount of money.
     * @return Formatted dollar.
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

}

package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * TASK
 * A customer can open an account
 * A customer can deposit / withdraw funds from an account
 * A customer can request a statement that shows transactions and totals for each of their accounts
 * <p>
 * Additional
 * A customer can transfer between their accounts
 */

/**
 * The Customer class represents a user of an artificial bank application.
 */
public class Customer {
    private final String name;
    private List<Account> accounts; //can hold multiple instances of the same accounts

    //id
    //firstName
    //surname
    //Address Line 1
    //Address Line 2
    //Postcode
    //Mobile Number
    //Email

    /**
     * Constructs a new Customer with the given name.
     *
     * @param name the Customers name
     */
    public Customer(String name, Account account) {
        // TODO: 10/10/2019 Name validation
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        if (account == null) {
            throw new IllegalArgumentException("account must not be null");
        }
        System.out.println("Creating new Customer...");
        this.name = name;
        this.accounts = new ArrayList<>();
        //Customer must have at least one account
        openAccount(account);
    }

    /**
     * Adds a new account to list of accounts.
     *
     * @param account new account
     * @return the current Customer object
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Displays interest earned from all accounts.
     *
     * @return interest earned
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    private BigDecimal sumSingleAccount(Account account){
        BigDecimal total = BigDecimal.ZERO;
        for(Transaction t: account.getTransactions()){
            total = total.add(t.getAmount());
        }
        total = total.setScale(2, RoundingMode.HALF_EVEN);
        return total;
    }

    private BigDecimal sumAllAccount() {
        BigDecimal total = BigDecimal.ZERO;

        for (Account account : accounts) {
            total = total.add(account.getBalance());
        }
        total = total.setScale(2, RoundingMode.HALF_EVEN);  //round to 2.d.p
        return total;
    }

    /**
     * Prints the statement for all accounts.
     *
     * @return
     */
    public String getStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Statement for ")
                .append(name)
                .append("\n");

        //Print statement for each Account
        for (Account account : accounts) {
            sb.append("\n")
                    .append(statementForAccount(account))
                    .append("\n");
        }
        //Sum of all Accounts
        sb.append("\nTotal In All Accounts ")
                .append(currencyFormat(sumAllAccount()));
        return sb.toString();
    }

    /**
     * Displays transactions of a GIVEN account and its final balance.
     *
     * @param account the account to query
     * @return a list of transactions from the given account
     */
    private String statementForAccount(Account account) {
        StringBuilder sb = new StringBuilder();

        //Print account name (e.g. Saving Account)
        sb.append(account.getAccountType()).append("\n");

        //Now total up all the transactions
        for (Transaction t : account.getTransactions()) {
            sb.append("  ")
                    .append(t.getAmount().signum() < 0 ? "withdrawal" : "deposit")
                    .append(" ").append(currencyFormat(t.getAmount().abs())).append("\n");
        }
        BigDecimal total = sumSingleAccount(account);
        sb.append("Total ")
                .append(currencyFormat(total));
        return sb.toString();
    }

    /**
     * Gets the amount of accounts currently opened by a Customer.
     *
     * @return number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Returns the name of the Customer.
     *
     * @return the Customers name
     */
    public String getName() {
        return name;
    }

    /**
     * Formats the input amount to 2 decimal place.
     *
     * @param d the input representing the price
     * @return String prefixed with dollar symbol
     */
    @Deprecated
    private String toPounds(double d) {
        return String.format("£%,.2f", abs(d));
    }

    public static String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }
}

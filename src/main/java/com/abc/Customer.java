package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * The Customer class represents a user of an artificial bank application.
 */
public class Customer {
    private final String name;
    private List<Account> accounts; //can hold multiple instances of the same accounts

    private String firstNameRegex = "[A-Z][a-z]*";
    private static final int NAME_LEN = 13;
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
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        if (!name.matches(firstNameRegex)) {
            throw new IllegalArgumentException("Incorrect name, valid name has only lowercase/capital letters with" +
                    "no spaces.");
        }
        if (name.length() >= NAME_LEN) {
            throw new IllegalArgumentException("name must be less than " + NAME_LEN + " characters");
        }
        if (account == null) {
            throw new IllegalArgumentException("account must not be null");
        }
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
     * Displays interest earned from all accounts. Single customer.
     *
     * @return interest earned
     */
    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account account : accounts) {
            total = total.add(account.interestEarned());
        }
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

        //Prints all transactions of Account given
        for (Transaction t : account.getTransactions()) {
            sb.append("  ")
                    .append(t.getAmount().signum() < 0 ? "withdrawal" : "deposit")
                    .append(" ").append(currencyFormat(t.getAmount().abs())).append("\n");
        }
        //Prints the final balance
        BigDecimal total = account.getBalance();
        sb.append("Total ")
                .append(currencyFormat(total));
        return sb.toString();
    }

    public void transfer(String fromAccountNo, String toAccountNo, BigDecimal amount) {
        Account from = getAccount(fromAccountNo);
        Account to = getAccount(toAccountNo);
        if (from == null || to == null) {
            System.err.println("Please enter correct account number...");
        } else {
            if (from.withdraw(amount)) {
                to.deposit(amount);
            }
        }
    }

    public Account getAccount(String other) {
        for (Account account : accounts) {
            if (account.getAccountNum().equals(other)) {
                return account;
            }
        }
        System.err.println("Can't find account: " + other);
        return null;
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

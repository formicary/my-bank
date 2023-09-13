package com.abc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer of the bank with associated accounts.
 */
public class Customer {

    private String name;
    private List<Account> accounts;

    /**
     * Constructs a new customer with the given name and an empty list of accounts.
     *
     * @param name The name of the customer.
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Gets the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Opens a new account for the customer and associates it with the customer.
     *
     * @param account The account to be opened.
     * @return The customer instance for method chaining.
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Gets the number of accounts associated with the customer.
     *
     * @return The number of accounts.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Calculates the total interest earned by the customer across all accounts.
     *
     * @param noOfDays The number of days for which to calculate interest.
     * @return The total interest earned.
     */
    public BigDecimal totalInterestEarned(int noOfDays) {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts)
            total = total.add(a.compoundedInterestEarned(noOfDays));
        return total;
    }

    /**
     * Generates a statement summarizing the customer's accounts and transactions.
     *
     * @return A statement for the customer.
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for ").append(name).append("\n");
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts) {
            statement.append("\n").append(statementForAccount(a)).append("\n");
            total = total.add(a.sumTransactions());
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    /**
     * Generates a statement for a specific account, including its transaction
     * history.
     *
     * @param a The account for which to generate the statement.
     * @return A statement for the account.
     */
    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder();

        // Translate to a more readable account type
        switch (a.getAccountType()) {
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

        // Now total up all the transactions for the account
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : a.transactions) {
            s.append("  ").append(t.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit").append(" ")
                    .append(toDollars(t.getAmount())).append("\n");
            total = total.add(t.getAmount());
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }

    /**
     * Formats a BigDecimal value as a currency amount.
     *
     * @param d The BigDecimal value to format.
     * @return A formatted currency string.
     */
    private String toDollars(BigDecimal d) {
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return df.format(d.abs());
    }

    /**
     * Transfers funds between two accounts of the customer.
     *
     * @param fromAccount The source account to transfer from.
     * @param toAccount   The target account to transfer to.
     * @param amount      The amount to transfer.
     * @throws IllegalArgumentException if the source or target account doesn't
     *                                  belong to the customer
     *                                  or if the transfer amount is invalid.
     */
    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (fromAccount == null || toAccount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid transfer request");
        }

        if (!accounts.contains(fromAccount) || !accounts.contains(toAccount)) {
            throw new IllegalArgumentException("One or both accounts do not belong to this customer");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

    /**
     * Gets the account at the specified index.
     *
     * @param index The index of the account to get.
     * @return The account at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 ||
     *                                   index >= accounts.size()).
     */
    public Account getAccount(int index) {
        if (index < 0 || index >= accounts.size()) {
            throw new IndexOutOfBoundsException("Invalid account index");
        }
        return accounts.get(index);
    }
}

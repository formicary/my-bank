package com.abc;


import java.util.ArrayList;
import java.util.List;

import com.abc.account.Account;

import static com.abc.utilities.AmountValidator.canWithdraw;
import static com.abc.utilities.AmountValidator.isNegativeAmount;

import java.math.BigDecimal;

/**
 * Customer category that represents a real customer
 */
public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Initialises a new customer object instance with a given name and empty list of accounts
     * @param name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Gets the customer name
     * @return customer name
     */
    public String getCustomerName() {
        return name;
    }

    /**
     * Opens a new account and associates it with given customer
     * @param account account being opened
     */
    public void openAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Calculates the number of accounts a givn customer has
     * @return number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Calculates the total interest earned across all accounts for a given customer
     * @return total amount of interest
     */
    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account account : accounts) {
            total = total.add(account.interestEarned());
        }
        return total;
    }

    /**
     * Moves money between two accounts for a given customer only
     * @param initiatingAccount account from which the funds are to be transferred
     * @param destinationAccount account receiving the funds
     * @param amount quantity to be transferred
     */
    public void transferFunds(Account initiatingAccount, Account destinationAccount, BigDecimal amount) {
        if (!accounts.contains(initiatingAccount) || !accounts.contains(destinationAccount)) {
            throw new IllegalArgumentException("The account(s) provided does not belong to this customer");
        }

        isNegativeAmount(amount);
        canWithdraw(initiatingAccount.getBalance(), amount);
        initiatingAccount.withdrawFunds(amount);
        destinationAccount.depositFunds(amount);
    }

    /**
     * Creates a statement that summarises all accounts and transactions for a given customer and calculates the
     * the total amount of funds across all their accounts combined
     * @return statement summary
     */
    public String generateConsolidatedAcountsStatement() {
        StringBuilder consolidatedStatement = new StringBuilder("Statement for ")
            .append(name)
            .append("\n");
        
        BigDecimal total = BigDecimal.ZERO;

        for (Account account : accounts) {
            consolidatedStatement.append("\n")
                .append(generateAccountStatement(account))
                .append("\n");
            total = total.add(account.sumTransactions());
        }
        consolidatedStatement.append("\nTotal In All Accounts ")
            .append(toDollars(total));
        return consolidatedStatement.toString();
    }

    /**
     * Creates a statement for a specified account of a given customer,
     * with a summary of all transactions and totals the transactions
     * @param account given account
     * @return statement summary
     */
    private String generateAccountStatement(Account account) {
        StringBuilder accountStatement = new StringBuilder();

        switch (account.getAccountType()) {
            case CHECKING -> accountStatement.append("Checking Account\n");
            case SAVINGS -> accountStatement.append("Savings Account\n");
            case MAXI_SAVINGS -> accountStatement.append("Maxi Savings Account\n");
            // Default not used to enforce compile error if the AccountType enum is modified
        }

        // Now total up all transactions for the specified account
        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : account.getTransactions()) {
            accountStatement.append("  ")
                .append((transaction.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit"))
                .append(" ")
                .append(toDollars(transaction.getAmount()))
                .append("\n");
            total = total.add(transaction.getAmount());
        }
        accountStatement.append("Total ").append(toDollars(total));

        return accountStatement.toString();
    }

    /**
     * Formats the value to include a currency
     * @param value quantity to be formatted
     * @return a currency string
     */
    private String toDollars(BigDecimal value){
        return String.format("$%,.2f", value.abs());
    }
}

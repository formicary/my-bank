package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Customer  {
    private static final String ILLEGAL_AMOUNT = "Amount must be greater than zero!";
    private static final String ILLEGAL_ACCOUNT = "Customer can only transform between his own accounts!";
    private static final String COLUMN_LENGTH = "%-20s%-20s%-20s%-20s\n";
    private static final String NOT_APPLICABLE = "N/A";
    private static final AtomicLong CUSTOMER_ID = new AtomicLong(0);
    // Customer details
    private final long customerId;
    private String firstName;
    private String lastName;
    // Customer's accounts
    private List<Account> customerAccounts;
    // HashMap of accounts
    private HashMap<Long, Account> accountsHashMap;

    /**
     * Constructor for the Customer class. Creates a customer with brand new ID
     * and new list of accounts
     * @param firstName
     * @param lastName
     */
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerAccounts = new ArrayList<Account>();
        this.customerId = CUSTOMER_ID.getAndIncrement();

        // Testing hashset
        accountsHashMap = new HashMap<Long, Account>();
    }
    /**
     * Open a new account for a customer
     * @param account
     * @return [Customer]
     */
    public Customer openAccount(Account account) {
        customerAccounts.add(account);

        // testing hashset
        accountsHashMap.put(account.getAccountId(), account);
        return this;
    }
    /**
     * Transfer money from an account to the other; only works with accounts that the customer owns
     * Method accepts account IDs instead of account types. Uses hashmap to reduce search complexity
     * @param amount
     * @param transferAccId
     * @param destintationAccID
     */
    public void transferMoney(double amount, long transferAccId, long destintationAccID) {
            Account transferAccount = accountsHashMap.get(transferAccId);
            Account destinationAccount = accountsHashMap.get(destintationAccID);
            transferMoney(amount, transferAccount, destinationAccount);
    }
    /**
     * Transfer money from an account to the other; only works with accounts that the customer owns
     * Uses an amount and two account types as parameters
     * @param amount
     * @param transferAccount
     * @param destinationAccount
     */
    public void transferMoney(double amount, Account transferAccount, Account destinationAccount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(ILLEGAL_AMOUNT);
        } else if(!accountsHashMap.containsKey(transferAccount.getAccountId())
                || !accountsHashMap.containsKey(destinationAccount.getAccountId())) {
            throw new IllegalArgumentException(ILLEGAL_ACCOUNT);
        } else {
            // Subtract money from target ID
            transferAccount.setBalance(transferAccount.getBalance().subtract(BigDecimal.valueOf(amount)));
            // Add money to destinationId
            destinationAccount.setBalance(destinationAccount.getBalance().add(BigDecimal.valueOf(amount)));
        }
    }
    /**
     * Calculate the total interest earned by the customer
     * @return [BigDecimal]
     */
    public BigDecimal totalInterestEarned() {
        BigDecimal totalInterest = BigDecimal.valueOf(0);
        for (Account account : customerAccounts) {
            totalInterest = totalInterest.add(account.interestEarned());
        }
        return totalInterest;
    }
    /**
     * Print a statement for the customer which includes the customer's accounts and
     * all of the transactions made
     * @return [StringBuilder]
     */
    public StringBuilder getStatement() {
        // Total Balance across accounts
        BigDecimal totalAllAccounts = BigDecimal.valueOf(0);
        // String builder to construct statement for customer
        StringBuilder customerStatement = new StringBuilder();
        // Add customer name to the StringBuilder
        customerStatement.append("Customer: " + firstName + " " + lastName + "\n");

        for(Account account : customerAccounts) {
            customerStatement.append(getStatement(account));
            totalAllAccounts = totalAllAccounts.add(account.getBalance());
        }

        customerStatement.append("\nTotal Balance: " + toDollars(totalAllAccounts));
        return customerStatement;
    }
    /**
     * Print the statement for a specific account of the customer which shows all of the
     * transactions belonging to that account
     * @param account
     * @return [StringBuilder]
     */
    public StringBuilder getStatement(Account account) {
        // Balance in current account
        BigDecimal totalAccount = account.getBalance();
        // StringBuilder to construct account statement
        StringBuilder accountStatement = new StringBuilder();
        // Add type of account first
        accountStatement.append("\nAccount Type: "
                + account.getAccountType()
                + "\n");
        // Add account ID to statement so customer can distinguish between accounts of similar type
        accountStatement.append("Account ID: "
                + account.getAccountId()
                + "\n");
        // Create table to show transactions
        accountStatement.append(String.format(COLUMN_LENGTH,
                "Transaction ID",
                "Transaction Type",
                "Amount",
                "Transaction Date"));
        if(account.getTransactions().isEmpty()) {
            accountStatement.append(String.format(COLUMN_LENGTH,
                    NOT_APPLICABLE,
                    NOT_APPLICABLE,
                    NOT_APPLICABLE,
                    NOT_APPLICABLE));
        } else {
            for (Transaction transaction : account.getTransactions()) {
                accountStatement.append(String.format(
                        COLUMN_LENGTH,
                        transaction.getTransactionId(),
                        transaction.getTransactionType(),
                        toDollars(BigDecimal.valueOf(transaction.getAmount())),
                        transaction.getTransactionDate()));
            }
        }
        accountStatement.append("Balance: " + toDollars(totalAccount) + "\n");
        return accountStatement;
    }

    // Convert amount of money to dollar format
    private String toDollars(BigDecimal amount){
        return String.format("$%,.2f", amount.abs());
    }

    // Getters
    /**
     * Get first name of the customer
     * @return [String] customer's first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Get last name of the customer
     * @return [String] customer's last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Get unique ID
     * @return [int] customer's unique id
     */
    public long getCustomerId() {
        return customerId;
    }
    /**
     * Retrieve list of accounts for that customer
     * @return [List<Accounts>] customer's accounts
     */
    public List<Account> getCustomerAccounts() {
        return customerAccounts;
    }
    /**
     * Get number of accounts the customer has
     * @return [int] number of accounts the customer has opened
     */
    public int getNumberOfAccounts() {
        return customerAccounts.size();
    }
}

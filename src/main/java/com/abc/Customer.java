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

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }


    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Returns the total amount of interest the customer has earned across all its accounts
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * Returns a statement that contains a summary of the customers accounts
     * and the total amount of money they have in all their accounts
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n" + statementForAccount(a) + "\n");
            total += a.sumAllTransactions();
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));
        return statement.toString();
    }

    /**
     * This method will allow the customer to transfer money from one owned account to another owned account.
     * It will do this by first making a withdrawal from the FIRSTACCOUNT and the a deposit into the SECONDACCOUNT.
     * The AMOUNT withdrawn and deposited into both accounts will be the same.
     * It first checks that both accounts belong to the customer, it does this by checking that both accounts are
     * present in the accounts list
     */
    public void transferMoney(Account firstAccount, Account secondAccount, double amount) {
        if (accounts.contains(firstAccount) && accounts.contains(secondAccount)) {
            firstAccount.withdraw(amount);
            secondAccount.deposit(amount);
        } else {
            throw new IllegalArgumentException("One or more the accounts do not belong to this customer");
        }
    }

    /**
     * Private method used to generate the statement for an Account,
     * returns a string in the format:
     *      Account type
     *      All transactions
     *      Balance in account.
     */
    private String statementForAccount(Account a) {
        StringBuilder statement = new StringBuilder();

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING:
                statement.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                statement.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                statement.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            total += t.amount;
            switch (t.getTransactionType()) {
                case Transaction.DEPOSIT:
                    statement.append("  deposit " + toDollars(t.amount) + "\n");
                    break;
                case Transaction.WITHDRAWAL:
                    statement.append("  withdrawal " + toDollars(t.amount) + "\n");
                    break;
                case Transaction.INTEREST:
                    statement.append("  withdrawal " + toDollars(t.amount) + "\n");
                    break;
            }
        }
        statement.append("Total " + toDollars(total));
        return statement.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}

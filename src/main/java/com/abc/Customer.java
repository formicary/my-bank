package com.abc;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    private String name;
    private Set<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new HashSet<>();
    }

    public void openAccount(Account account) {
        if (accounts.contains(account))
            throw new UnsupportedOperationException("The account has already been added to the list of user accounts");
        else
            accounts.add(account);
            account.linkWithCustomer();
    }

    // Transfers a sum between two accounts that belong to the same customer
    public void transferBetweenAccounts(Account accountFrom, Account accountTo, double amount) {

        if (accountFrom == accountTo)
            throw new IllegalArgumentException("The account from which the amount would be taken out and the account to" +
                                               " which the amount is to be transfer should be different");
        if (!accounts.contains(accountFrom) || !accounts.contains(accountTo))
            throw new IllegalArgumentException("Both accounts should be associated with the same customer");

        if (amount <= 0)
            throw new IllegalArgumentException("The amount to be transferred should be positive");

        if (amount > accountFrom.getBalance() || accountFrom.getBalance() == 0)
            throw new IllegalArgumentException("The balance of the account used for withdrawal should be greater " +
                                               "than 0 and less than or equal to the current account balance");

            accountFrom.withdraw(amount);
            accountTo.deposit(amount);
    }

    // Returns a bank statement for all customer accounts
    public String getStatement() {
        String statement = "Statement for " + name + "\n";

        statement += ReportFormatter.makeSymbolLine('‾', 30);

        for (Account account : accounts)
            statement += getAccountStatement(account);

        statement = statement.replaceAll("\n$", "");
        statement += "\n" + ReportFormatter.makeSymbolLine('_', 20);

        return statement;
    }

    // Returns a bank statement for an individual customer account
    public String getAccountStatement(Account account) {
        String statement = "";

        switch(account.getAccountType()){
            case CHECKING:
                statement += "Checking Account";
                break;
            case SAVINGS:
                statement += "Savings Account";
                break;
            case MAXI_SAVINGS:
                statement += "Maxi Savings Account";
                break;
        }

        statement += "\n" + ReportFormatter.makeSymbolLine('-', 20);

        //Total up all transactions
        if (account.getTransactions().size() > 0)
            for (Transaction transaction : account.getTransactions())
                statement += "  *" + (transaction.isWithdrawal() ? "withdrawal" : "deposit")
                        + " $" + ReportFormatter.decimalFormatter.format(transaction.getTransactionAmount()) + " - " + transaction.getFormattedTransactionDate() + "\n";
        else
            statement += "  No transactions made to this date\n";

        statement += ReportFormatter.makeSymbolLine('-', 20);

        statement += "Total: " + ReportFormatter.decimalFormatter.format(account.getBalance()) + "\n\n";

        return statement;
    }

    // Calculates the total interest earned from all customer accounts
    public double getTotalInterestEarned() {
        double total = 0D;

        for (Account account : accounts)
            total += account.getEarnedInterest();

        return total;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
}

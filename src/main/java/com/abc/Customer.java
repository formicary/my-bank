package com.abc;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

import static com.abc.Utilities.AmountValidator.isNegativeAmount;
import static com.abc.Utilities.AmountValidator.canWithdraw;

// Todo: continue with refactor and include similar to JSDoc
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

    public double totalInterestEarned() {
        double total = 0.0d;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }

    public void transferFunds(Account initiatingAccount, Account destinationAccount, double amount) {
        if (!accounts.contains(initiatingAccount) || !accounts.contains(destinationAccount)) {
            throw new IllegalArgumentException("The account(s) provided does not belong to this customer");
        }

        isNegativeAmount(amount);
        canWithdraw(initiatingAccount.getBalance(), amount);
        initiatingAccount.withdrawFunds(amount);
        destinationAccount.depositFunds(amount);
    }

    public String generateConsolidatedAcountsStatement() {
        StringBuilder consolidatedStatement = new StringBuilder("Statement for ").append(name).append("\n");
        
        double total = 0.0d;

        for (Account account : accounts) {
            consolidatedStatement.append("\n").append(generateAccountStatement(account)).append("\n");
            total += account.sumTransactions();
        }
        consolidatedStatement.append("\nTotal In All Accounts ").append(toDollars(total)); // is this desired? It seems odd to get one sum total from different account types
        return consolidatedStatement.toString();
    }

    private String generateAccountStatement(Account account) {
        StringBuilder accountStatement = new StringBuilder();

        // State account type
        switch (account.getAccountType()) {
            case CHECKING -> accountStatement.append("Checking Account\n");
            case SAVINGS -> accountStatement.append("Savings Account\n");
            case MAXI_SAVINGS -> accountStatement.append("Maxi Savings Account\n");
            // Default not used to enforce compile error if the AccountType enum is modified
        }

        // Now total up all transactions for the specified account
        double total = 0.0d;

        for (Transaction transaction : account.transactions) {
            accountStatement.append("  ")
            .append((transaction.getAmount() < 0 ? "withdrawal" : "deposit"))
            .append(" ")
            .append(toDollars(transaction.getAmount()))
            .append("\n");
            total += transaction.getAmount();
        }
        accountStatement.append("Total ").append(toDollars(total));

        return accountStatement.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

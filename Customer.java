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

    public List<Account> getAccounts() {
        return accounts;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double getTotalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.getInterestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder totalStatement = new StringBuilder();
        totalStatement.append("Statement for " + name + "\n");
        double total = 0.0;
        for (Account account : accounts) {
            totalStatement.append("\n" + getStatementForAccount(account) + "\n");
            total += account.sumTransactions();
        }
        totalStatement.append("\nTotal In All Accounts " + toDollars(total));
        return totalStatement.toString();
    }

    private String getStatementForAccount(Account account) {
        StringBuilder individualStatement = new StringBuilder();

       //Translate to pretty account type
        switch(account.getAccountType()){
            case Account.CHECKING:
                individualStatement.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                individualStatement.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                individualStatement.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        List<Transaction> transactions = account.getTransactions();

        for (Transaction transaction : transactions) {
            individualStatement.append("  " + (transaction.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(transaction.getAmount()) + "\n");
            total += transaction.getAmount();
        }
        individualStatement.append("Total " + toDollars(total));
        return individualStatement.toString();
    }

    private String toDollars(double amount){
        return String.format("$%,.2f", abs(amount));
    }

    // Get total funds from all accounts
    public double getTotalFunds () {
        double amount = 0;

        for(Account account : accounts) {
            amount += account.sumTransactions();
        }

        return amount;
    }

    // Transfer between two accounts from the same customer
    public void bankTransfer(double amount, Account from, Account to) {
        from.withdraw(amount);
        to.deposit(amount);
    }
}

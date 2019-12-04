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

    public Account openAccount(Account.AccountType accountType) {
        Account account = new Account(accountType);
        accounts.add(account);

        return account;
    }

    public void transfer(Account source, Account destination, double amount) {
        if (accounts.indexOf(source) == -1) {
            throw new IllegalArgumentException("source account not owned by customer");
        }

        if (source.calculateBalance() < amount) {
            throw new IllegalArgumentException("source account has an insufficient balance");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be larger than 0");
        }

        source.addTransaction(new Transaction(-amount, Transaction.TransactionType.TRANSFER_OUT));
        destination.addTransaction(new Transaction(amount, Transaction.TransactionType.TRANSFER_IN));
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getTotalInterestEarned();
        return total;
    }

    public void updateInterestPayments() {
        for (Account a : accounts) {
            if (a.checkInterestEligibility()) {
                a.addTransaction(new Transaction(a.getDailyInterestEarned(), Transaction.TransactionType.INTEREST));
            }
        }
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for ")
            .append(name)
            .append('\n');

        double total = 0.0;

        for (Account a : accounts) {
            statement.append('\n')
                .append(statementForAccount(a))
                .append('\n');
            total += a.calculateBalance();
        }
        statement.append("\nTotal In All Accounts ")
            .append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account a) {
        StringBuilder statement = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                statement.append("Checking Account\n");
                break;
            case SAVINGS:
                statement.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                statement.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            statement.append("  ")
                .append(t.getTransactionType().name())
                .append(' ')
                .append(toDollars(t.getAmount()))
                .append('\n');
            total += t.getAmount();
        }
        statement.append("Total ")
            .append(toDollars(total));
        return statement.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

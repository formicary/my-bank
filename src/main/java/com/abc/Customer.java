package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

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

    public double totalInterestEarned() {
        double total = 0.0;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for ");
        statement.append(name);
        statement.append("\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n" + statementForAccount(a) + "\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
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

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s.append("  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n");
            total += t.amount;
        }
        s.append("Total " + toDollars(total));
        return s.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    // For Transfer between accounts
    public boolean checkIfAccountExists(Account a) {
        boolean exist = false;
        for(Account ac : accounts) {
            if (ac == a) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    // Transfer: Withdraw from one account and deposit it to another account
    public void transfer(Account send, Account receive, double amount) {
        boolean checkSending = checkIfAccountExists(send);
        boolean checkReceiving = checkIfAccountExists(receive);

        if(checkSending && checkReceiving) {
            send.withdraw(amount);
            receive.deposit(amount);
        } else if (checkSending) {
            throw new IllegalArgumentException("Invalid Receiving Account for Transfer");
        } else {
            throw new IllegalArgumentException("Invalid Sending Account for Transfer");
        }
    }
}

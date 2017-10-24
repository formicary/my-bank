package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.math.RoundingMode;
import java.math.BigDecimal;

import static java.lang.Math.abs;

public class Customer {
    
    private String name;
    private List<Account> accounts;

    protected Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    protected String getName() {
        return name;
    }

    protected Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    protected int getNumberOfAccounts() {
        return accounts.size();
    }

    protected double totalInterestEarned() {
        double total = 0.00;
        BigDecimal roundedTotal;
        BigDecimal convertedTotal;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        convertedTotal = BigDecimal.valueOf(total);
        roundedTotal = convertedTotal.setScale(2, RoundingMode.HALF_EVEN);
        return roundedTotal.doubleValue();
    }
     
    protected void transfer(double amount, int withdrawalAccountIndex, int depositAccountIndex) {
        accounts.get(withdrawalAccountIndex).withdraw(amount);
        accounts.get(depositAccountIndex).deposit(amount);
    }
        
    private String toDollars(double d) {
        return String.format("%1$.2f", abs(d));
    }
    
    protected String getStatement() {
        String statement;
        double total = 0.00;
        BigDecimal convertedTotal;
        BigDecimal roundedTotal;
        statement = String.format("Statement for %1$s%n", name);
        for (Account a : accounts) {
            statement += String.format("%n%1$s%n", statementForAccount(a));
            total += a.sumTransactions();
        }
        convertedTotal = BigDecimal.valueOf(total);
        roundedTotal = convertedTotal.setScale(2, RoundingMode.HALF_EVEN);
        statement += String.format("%nTotal In All Accounts %1$s", toDollars(roundedTotal.doubleValue()));
        return statement;
    }

    private String statementForAccount(Account a) {
        double total = 0.00;
        BigDecimal convertedTotal;
        BigDecimal roundedTotal;
        String s;
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s = "Checking Account\n";
                break;
            case Account.SAVINGS:
                s = "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s = "Maxi Savings Account\n";
                break;
            default:
                throw new IllegalArgumentException("Invalid account type");
        }
        for (Transaction t : a.transactions) {
            if (t.amount < 0) {
                s += String.format("  withdrawal %1$s%n", toDollars(t.amount));
            } else {
                s += String.format("  deposit %1$s%n", toDollars(t.amount));
            }
            total += t.amount;
        }
        roundedTotal = convertedTotal.setScale(2, RoundingMode.HALF_EVEN);
        statement += String.format("%nTotal In All Accounts %1$s", toDollars(roundedTotal.doubleValue()));
        s += String.format("Total %1$s", toDollars(total));
        return s;
    }
}

package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add(a.sumTransactions());
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
       
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount.compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total = total.add(t.amount);
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(BigDecimal d) {       
        return String.format("$%,.2f", d.abs().setScale(2, RoundingMode.DOWN)); 
    }
}

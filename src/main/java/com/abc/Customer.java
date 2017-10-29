package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Customer {
    private final String name;
    private final List<Account> accounts;

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
        StringBuilder statement = new StringBuilder();
        
        statement.append("Statement for ");
        statement.append(name);
        statement.append("\n");
        
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts) {
            statement.append("\n");
            statement.append(statementForAccount(a));
            statement.append("\n");
            total = total.add(a.sumTransactions());
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
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : a.transactions) {
            s.append("  ");
            s.append((t.amount.compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit"));
            s.append(" ");
            s.append(toDollars(t.amount));
            s.append("\n");
            total = total.add(t.amount);
        }
        s.append("Total ");
        s.append(toDollars(total));
        return s.toString();
    }

    private String toDollars(BigDecimal d) {       
        return String.format("$%,.2f", d.abs().setScale(2, RoundingMode.DOWN)); 
    }
}

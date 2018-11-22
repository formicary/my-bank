package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;

    // I don't see the need for this to be initialised in constructor.
    // So I initialised it here out of the way.
    private List<Account> accounts = new ArrayList<>();

    public Customer(String name, Account account) {
        this.name = name;
        accounts.add(account);
    }

    public Account getAccount(int i) {
        return accounts.get(i);
    }

    public String getName() {
        return name;
    }

    // Its odd to use return this in this case as
    // this can easily be a voided method. So I have changed this.
    public void openAnotherAccount(Account account) {
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
        BigDecimal total = new BigDecimal("0.0");
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
        BigDecimal total = new BigDecimal("0.0");
        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount().doubleValue() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total = total.add(t.getAmount());
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(BigDecimal d) {
        d = d.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return String.format("$%,.2f", abs(d.doubleValue()));
    }
}

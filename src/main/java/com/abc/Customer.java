package com.abc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Customer {

    private final String name;
    private final Map<String, Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.put(account.getIban(), account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void transfer(String withdrawAccount, String depositAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount for transfer must be greater than zero");
        }
        if (!accounts.containsKey(withdrawAccount) || !accounts.containsKey(depositAccount)) {
            throw new IllegalArgumentException("account does not exist");
        }

        accounts.get(withdrawAccount).withdraw(amount);
        accounts.get(depositAccount).deposit(amount);
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Account> a : accounts.entrySet())
            total = total.add(a.getValue().interestEarned());
        return total;
    }

    public String getStatement() {
        String statement;
        statement = "Statement for " + name + "\n";
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Account> a : accounts.entrySet()) {
            statement += "\n" + statementForAccount(a.getValue()) + "\n";
            total = total.add(a.getValue().sumTransactions());
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s += "Checking Account" + " [" + a.getIban() + "]\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account" + " [" + a.getIban() + "]\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account" + " [" + a.getIban() + "]\n";
                break;
        }

        //Now total up all the transactions
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total = total.add(t.getAmount());
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(BigDecimal d) {
        return String.format("$%,.2f", d.setScale(2).abs());
    }

}

package com.abc;

import java.math.BigDecimal;
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

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
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

    /**
     * Transfer funds from one account to another.
     * @param from Account to transfer from.
     * @param to Account to transfer to.
     * @param amount The amount to transfer
     */
    public void transferAccountFunds(Account from, Account to, double amount) {
        if (amount > 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }else if (from.sumTransactions().compareTo(new BigDecimal(amount)) < 0){
            throw new IllegalArgumentException("account does not have enough funds");
        }else {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount.compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit");
            s += " " + toDollars(t.amount) + "\n";
            total = total.add(t.amount);
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(BigDecimal d){
        return String.format("$%,.2f", d.abs().doubleValue());
    }
}

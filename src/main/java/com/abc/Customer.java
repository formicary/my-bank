package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    private Account account;


    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();


    }

    public void transferMoney(BigDecimal amount, Account targetAccount, UUID accountId) throws IllegalArgumentException {
        boolean successfulTransfer = false;
        for (int i = 0; i < accounts.size(); i++) {
            Account currentAccount = accounts.get(i);
            if (currentAccount.accountId.equals(accountId)) {
                currentAccount.withdraw(amount);
                targetAccount.deposit(amount);
                successfulTransfer = true;
            }


        }
        if (!successfulTransfer) {
            System.out.println("Error - transfer not successful.");
            throw new IllegalArgumentException("Error - transfer not successful.");
        }

    }

    public String customerSummary() {
        String s = "";
        for (Account a : accounts) {
            s += "\n" + a.getAccountSummary() + "\n";
        }
        return "A customer summary for " + name + ": " + s;
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
        BigDecimal total = new BigDecimal(0);
        for (Account a : accounts)
            total = total.add(a.interestEarnedDaily());
        return total;
    }

    public String getStatement() {

        String s = this.name + "'s Statement: \n";
        BigDecimal total = new BigDecimal(0.0);
        for (Account a : accounts) {
            s += "\n" + a.getAccountSummary() + "\n";
            total = total.add(a.getAccountBalance());
        }
        s += "\nTotal In All Accounts " + Conversion.toDollars(total);
        return s;
    }


}

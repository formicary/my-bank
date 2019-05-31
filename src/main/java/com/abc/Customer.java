package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    private Utility utility = new Utility();

    public enum AccountPortfolioVersion {
        PORTFOLIO_1,
        PORTFOLIO_2;
    }

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

    public double totalInterestEarned(AccountPortfolioVersion accountPortfolioVersion) {
        double total = 0;
        for (Account a : accounts)
            switch (accountPortfolioVersion)
        {
            case PORTFOLIO_1:
                // Portfolio 1 implements the original interest calculation method for accounts opened before the bank introduced type of Maxi Saving account
                total += a.interestEarnedPORTFOLIO_1();
                break;

            case PORTFOLIO_2:
                // Portfolio 1 implements the new interest calculation method for newly opened accounts
                total += a.interestEarnedPORTFOLIO_2();
                break;
        }
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + utility.formatAmount(total, utility.currencyFormatDollar);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        s += a.getAccountTypeName() + "\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + t.getTransactionType() + " " + utility.formatAmount(abs(t.getAmount()), utility.currencyFormatDollar) + "\n";
            total += t.getAmount();
        }
        s += "Total " + utility.formatAmount(total, utility.currencyFormatDollar);
        return s;
    }

    public void transferBeetweenAccounts(Account sourceAccount, Account targetAccount, double amountToTransfer) {
        // As there are no detailed requirements were given this is just a minimalist solution without any additional validation
        if (accounts.contains(sourceAccount) && accounts.contains(targetAccount)) {
            sourceAccount.withdraw(amountToTransfer, new Date());
            targetAccount.deposit(amountToTransfer, new Date());
        }
    }

}


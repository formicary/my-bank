package com.abc.MainClasses;

import java.util.ArrayList;
import java.util.List;

import com.abc.Interfaces.CustomerInterface;

import static java.lang.Math.abs;

public class Customer implements CustomerInterface {
    private String name;
    private List<Account> accounts;
    private double total;

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
        double total = 0;

        for (Account account : accounts)
            total += account.interestEarned();

        return total;
    }

    public String[] getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        total = 0;

        for (Account account : accounts) {
            statement += statementForAccount(account) + "\n";
            total += account.sumTransactions();
        }

        statement += "\nTotal Of " + name + "'s Accounts : " + toDollars(total);
        String statementAndTotal[] = {statement, String.valueOf(total)};

        return statementAndTotal;
    }

    public String statementForAccount(Account account) {
        String s = "";

       //Translate to pretty account type
        switch(account.getAccountType()){
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
        double total = 0;

        for (Transaction t : account.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " : " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }

        s += "Total : " + toDollars(total);

        return s;
    }

    //Transfer funds from one account to another
    public double[] transferFunds(double amount, Account fromAccount, Account toAccount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        //Withdraw from from source account and deposit to destination account
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        //Return list that contains the balance of each account
        double[] amountInAccounts = {fromAccount.getBalance(), toAccount.getBalance()};
        return amountInAccounts;
    }

    public String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
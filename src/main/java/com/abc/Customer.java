package com.abc;

import com.abc.Account.Account;
import com.abc.Exception.IllegalTransferException;

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

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public Account getAccount(int index) {
        return accounts.get(index);
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public double getTotalBalance(){
        double total = 0.0;
        for(Account a : accounts) {
            for (int i = 0; i < a.getNumberOfTransactions(); i++) {
                total += a.getTransaction(i).amount;
            }
        }
        return total;
    }

    public void transferBetweenAccounts(double amount, int accountIndexFrom, int accountIndexTo){
        int size = getNumberOfAccounts() - 1;
        if(accountIndexFrom > size | accountIndexTo > size){
            throw new IllegalTransferException("account must exist in customer's list");
        } else if(amount <= 0) {
            throw new IllegalTransferException("amount must be greater than zero");
        }
        Account accountFrom = accounts.get(accountIndexFrom);
        Account accountTo = accounts.get(accountIndexTo);
        if(accountFrom.sumTransactions() < amount){
            throw new IllegalTransferException("account cannot contain less than transaction amount");
        } else {
            accountFrom.withdraw(amount);
            accountTo.recieveTransfer(amount);
        }
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public void accrueInterestForAllAccounts(){
        for(Account a : accounts){
            a.accrueDailyInterest();
        }
    }

    @Override
    public String toString() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(a).append("\n");
            total += a.getBalance();
        }
        statement.append("\nTotal In All Accounts ").append(Bank.toDollars(total));
        return statement.toString();
    }
}

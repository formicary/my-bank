package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    private final Lock transfer_lock = new ReentrantLock(true); // Uses fair locking

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

    private void _accountsEmptyCheck() throws IllegalArgumentException
    {
        if (accounts.isEmpty())
            throw new IllegalArgumentException("no accounts exist for this customer");
    }

    private void _accountValidCheck(int accountNumber) throws IllegalArgumentException
    {
        if (accountNumber > getNumberOfAccounts())
            throw new IllegalArgumentException("invalid account number (is greater than the available number of accounts for this customer)");
    }

    public void depositIntoAccount(int accountNumber, double amount) throws IllegalArgumentException
    {
        _accountValidCheck(accountNumber);
        accounts.get(accountNumber).deposit(amount);
    }

    public void withdrawFromAccount(int accountNumber, double amount)
    {
        ///* mutex here per account? make sure withdrawal is done before anything else reads from 'accountFrom'?
        _accountValidCheck(accountNumber);
        accounts.get(accountNumber).withdraw(amount);
        //*/
    }

    public void transferBetweenAccounts(int accountFrom, int accountTo)
    {
        _accountsEmptyCheck();
        _accountValidCheck(accountFrom);
        _accountValidCheck(accountTo);

        transfer_lock.lock();
        try {
            ///* mutex here per account? make sure withdrawal/transfer is done before anything else reads from 'accountFrom' or 'accountTo'?
            double transSumFrom = accounts.get(accountFrom).sumTransactions();
            accounts.get(accountFrom).withdraw(transSumFrom);
            accounts.get(accountTo).deposit(transSumFrom);
            //*/
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            transfer_lock.unlock();
        }
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
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
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
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
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

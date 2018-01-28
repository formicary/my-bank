package com.abc;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * This is the class for the Customer.
 * It contains an ArrayList of the Accounts held by the customer.
 * @author Matthew Howard - <a href="mailto:m.o.howard@outlook.com">m.o.howard@outlook.com</a>
 */

public class Customer {
    private String name;
    private List<Account> accounts;


    public Customer(String name, Bank bank) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        bank.addCustomer(this);
    }

    public String getName() {
        return name;
    }

    public Account openAccount(int accountType) {
        Account newAcc = new Account(accountType);
        accounts.add(newAcc);
        return newAcc;
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
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    public void transferFundsBetweenAccounts(Account from, Account to, double amount){
        boolean fromExists = false;
        boolean toExists = false;
        for(int i = 0; i< accounts.size();i++){
            if (accounts.get(i) == from) {
                fromExists = true;
            }
            if(accounts.get(i) == to){
                toExists= true;
            }
        }
        if(fromExists&&toExists){
            from.withdraw(amount);
            to.deposit(amount);
        } else{
            throw new IllegalArgumentException("both accounts must belong to this customer");
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
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

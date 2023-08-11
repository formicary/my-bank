package com.abc.classes;

import java.util.ArrayList;
import java.util.List;

import com.abc.classes.Account.AccountType;
import com.abc.helpers.CustomerStatementBuilder;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private static List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        Customer.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Account openAccount(AccountType accountType) {
        Account newAccount = new Account(accountType);
        accounts.add(newAccount);
        return newAccount;
    }   

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getAccruedIntered();
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

    public static void test(){
        CustomerStatementBuilder.createStatement(accounts);
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

        //Remove after testing
    public static void main(String[] args) {
        Customer customer = new Customer("Test Name");
        Account newAccount = customer.openAccount(AccountType.CHECKING);
        Account newAccount1 = customer.openAccount(AccountType.MAXI_SAVINGS);
        Account newAccount2 = customer.openAccount(AccountType.MAXI_SAVINGS);
        newAccount.tryDeposit(5000);
        newAccount.addInterest();
        newAccount.addInterest();

        // System.out.println(customer.getNumberOfAccounts());
        // System.out.println(newAccount.getBalance());
        // System.out.println(customer.totalInterestEarned());
        test();



    }
}

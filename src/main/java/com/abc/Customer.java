package main.java.com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    //Constructor
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    //Return the customer's name
    public String getName() {
        return name;
    }

    //Create a new account for the customer
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    //Returns the number of accounts the customer has open
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    //Calculates and returns interest earned across all accounts
    public double totalInterestEarned(double years) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(a.sumTransactions(), years);
        return total;
    }

    //Returns a statement for all accounts
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

    //Returns a statement for an Account
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

        //Total up all transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    //Formats a double to $X.XX format
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }


    public String transfer(Account fromAccount, Account toAccount, double amount){
        try{
            fromAccount.withdraw(amount);
        } catch (Error e){
            e.printStackTrace();
            return "Error";
        }

        toAccount.deposit(amount);
        return "Transfer successful!";
    }
}

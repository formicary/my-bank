package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import java.util.Calendar;
public class Customer {
    private final String name;
    private final List<Account> accounts;
//constructor consisting of customer name and a list of his accounts

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
//class to return the customers name
    public String getName() {
        return name;
    }
//class to openAccount by adding an account to the accounts list
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
//class to get total number of accounts
    public int getNumberOfAccounts() {
        return accounts.size();
    }
//class to calculate the total interest earned. The bank manager or the customer
//should specify how many days they want that to be.
    public double totalInterestEarned(int numOfDays) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(numOfDays);
        return total;
    }
//class to return a statement for the account
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + this.name + "\n";
        double total = 0.0;
        for (Account a : this.accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }
//method to get a statement for an individual account.
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
    //method to transfer between accounts
    public void transferBetweenAccounts(Account accountTransfering, Account accountToTransfer,double amountToTransfer){
        if(this.checkAccount(accountTransfering) && this.checkAccount(accountToTransfer)){
            accountTransfering.withdraw(amountToTransfer, Calendar.getInstance().getTime());
            accountToTransfer.deposit(amountToTransfer, Calendar.getInstance().getTime());
        }
        
    }
//method to convert a dollar format
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
//method to check if an account exists.
    private Boolean checkAccount(Account a){
        Boolean acountExists;
        if (this.accounts.contains(a))
          acountExists = true;
        else
            acountExists = false;
        return acountExists;
    }
}

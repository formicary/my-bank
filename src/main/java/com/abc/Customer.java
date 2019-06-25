package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<AccountTemp> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<AccountTemp>();
    }

    public String getName() {
        return name;
    }

    public AccountTemp openCheckingAccount(){
        CheckingAccount acc = new CheckingAccount();
        accounts.add(acc);
        return acc;
    }

    public AccountTemp openMaxiSavingsAccount(){
        MaxiSavingsAccount acc = new MaxiSavingsAccount();
        accounts.add(acc);
        return acc;
    }

    public AccountTemp openSavingsAccount(){
        SavingsAccount acc = new SavingsAccount();
        accounts.add(acc);
        return acc;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (AccountTemp a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (AccountTemp a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getAccountBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(AccountTemp a) {
        String s = "";

       //Translate to pretty account type
        /*
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
         */

        s += a.getAccountTypeString() + " Account\n";

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

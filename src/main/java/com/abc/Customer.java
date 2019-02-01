package com.abc;

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

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }



    public String getStatement() {
        StringBuilder statement = new StringBuilder("");
        statement.append("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n" + statementForAccount(a) + "\n");
            total += a.getAccountBalance() ;
        }
        statement.append("\nTotal In All Accounts " + (total > 0 ? toDollars(total) : "-" + toDollars(total)));
        return statement.toString();
    }

    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder("");

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                s.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s.append("  ").append(t.getTransactionType()).append(" ").append(toDollars(t.getAmount())).append("\n");
            total += t.getAmount();
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }



    public void transfer(int depositAcc, int withdrawAcc, double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Transfers must be greater than zero");
        }
        else{
            this.accounts.get(depositAcc).transfer(amount);
            this.accounts.get(withdrawAcc).transfer(-amount);
        }
    }


    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public String getName() {
        return name;
    }

}

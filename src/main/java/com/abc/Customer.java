package com.abc;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;


import static java.lang.Math.abs;

public class Customer {
    private static int count = 0;
    private String name;
    private List<Account> accounts;
    private int customerID;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
        this.customerID = count ++;
    }

    public String getName() {
        return name;
    }

    public int getID(){
        return customerID;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public Account getAccount(int index){
        if (index < getNumberOfAccounts()){
            return accounts.get(index);
        }
        else{
            System.out.println("Account does not exist");
            return null;
        }
    }

    public void transfer(Account a, Account b, BigDecimal amount){
        a.withdraw(amount);
        b.deposit(amount);
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.valueOf(0.00);
        for (Account a : accounts) {
            total = a.interestEarned().add(total);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        BigDecimal total = BigDecimal.valueOf(0.0);
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = a.sumTransactions().add(total);
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
        BigDecimal total = BigDecimal.valueOf(0.0);
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount.compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total = t.amount.add(total);
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(BigDecimal d){
        return String.format("$%,.2f", d.abs());
    }
}

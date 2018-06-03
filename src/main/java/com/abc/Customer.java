package com.abc;

import java.math.BigDecimal;
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

    //my addition
    public List<Account> getAccounts() {
        return accounts;
    }

    //my addition
    public void transferBetweenAccounts(Account a, Account b, double amount) {

        if (amount < a.sumTransactions().doubleValue()) {
            a.withdraw(new BigDecimal(amount));
            b.deposit(new BigDecimal(amount));
        } else {
            System.out.println("insufficient fund");
        }

    }

    //my additon
    public BigDecimal totalSaving(){
        BigDecimal totalSaving = new BigDecimal(0);
        for (int i = 0; i < accounts.size(); i++) {
            for (int j = 0; j < accounts.get(i).transactions.size(); j++) {
                totalSaving = totalSaving.add(accounts.get(i).transactions.get(j).amount);
            }
        }
        return totalSaving;
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

    public BigDecimal totalInterestEarned() {
        BigDecimal total = new BigDecimal(0);
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        BigDecimal total = new BigDecimal(0);
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add(a.sumTransactions());
        }
        statement += "\nTotal In All Accounts " + toDollars(total.doubleValue());
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
        BigDecimal total = new BigDecimal(0);
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount.doubleValue() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount.doubleValue()) + "\n";
            total = total.add(t.amount);
        }
        s += "Total " + toDollars(total.doubleValue());
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public static void main(String[] args){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(new BigDecimal(100.0));
        savingsAccount.deposit(new BigDecimal(4000.0));
        savingsAccount.withdraw(new BigDecimal(200.0));

        System.out.println(henry.getStatement());
    }
}

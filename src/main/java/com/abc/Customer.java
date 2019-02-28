package com.abc;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;
/*----------------------------------------------------------------------------- 
                                Customer Class
-----------------------------------------------------------------------------*/
public class Customer {
    private String name;
    private List<Account> accounts;
    private Statement statemnt;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.statemnt=new Statement();
    }

    public boolean makeTransfer(Account from, Account to, double amount){
        if(getNumberOfAccounts()<2){
            return false;
        }

        from.withdraw(amount);
        to.deposit(amount);
        return true;
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

    public double totalInterestEarned() {
        double total = 0.0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement(){
        return this.statemnt.getStatement();
    }

    /*-----------------------------------------------------------------------------
                                    Nested Statement Class
    -----------------------------------------------------------------------------*/
    class Statement{
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

        private String statementForAccount(Account a) {
            String s = "";
            double total = 0.0;
            s += a.getAccountType();    //display account type

            for (Transaction t : a.getTransactions()) {     //list all transactions
                s += "  " + (t.getTransactionAmount() < 0 ? "withdrawal" : "deposit") + " "+ toDollars(t.getTransactionAmount()) + "\n";
                total += t.getTransactionAmount();
            }
            s += "Total " + toDollars(total);
            return s;
        }

        private String toDollars(double d) {
            return String.format("$%,.2f", abs(d));
        }
    }

}

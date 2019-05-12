/*Edited by: Foyaz Hasnath*/
package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

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

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned(Date businessDate) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(businessDate);
        return total;
    }

    public String getStatement(Bank bank) {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a,bank.getCurrBusinessDate()) + "\n";
            total += a.sumTransactions()+a.interestEarned(bank.getCurrBusinessDate());
        }
        statement += "\nTotal In All Accounts (including interest): " + toDollars(total);
        return statement;
    }

    public String getTransactions(){

        String checkingAccountTransacts = "CHECKING: ";
        String savingsAccountTransacts = "SAVINGS: ";
        String maxiSavingsAccountTransacts = "MAXI_SAVINGS: ";

            //go through each transaction for each account and record
            for (Account a:accounts) {
                for (Transaction t : a.transactions) {
                    if(a.getAccountType()==Account.CHECKING && t.getAmount()!=0){
                        checkingAccountTransacts += t.getAmount() + ",";
                    }else if(a.getAccountType()==Account.SAVINGS && t.getAmount()!=0){
                        savingsAccountTransacts += t.getAmount() + ",";
                    }else{
                        if(t.getAmount()!=0) {
                            maxiSavingsAccountTransacts += t.getAmount() + ",";
                        }
                    }
                }

            }

        //concat all and remove "," at end of line
        String allTransacts = checkingAccountTransacts.substring(0,checkingAccountTransacts.length()-1)+"\n"
                                +savingsAccountTransacts.substring(0,savingsAccountTransacts.length()-1)+"\n"
                                    +maxiSavingsAccountTransacts.substring(0,maxiSavingsAccountTransacts.length()-1);
        System.out.println("all transacts:\n"+allTransacts);
        return allTransacts;
    }

    private String statementForAccount(Account a,Date businessDate) {
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
        s += "Total (less interest): " + toDollars(total) + "\n"
            + "Interest: "+ toDollars(a.interestEarned(businessDate)) +"\n"
            + "Total: " + toDollars(total+a.interestEarned(businessDate));
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}

package com.accenture.accounts;

import com.accenture.Customer;
import com.accenture.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Account {

    private long accountNumber;
    private List<Transaction> transactions;
    private ArrayList<Customer> customers;

    public Account(long accountNumber) {
        if (accountNumber<9999999 || accountNumber>99999999)
            throw new IllegalArgumentException("Account number must be positive and with 8 digits");
        this.accountNumber = accountNumber;
        this.transactions = new ArrayList<Transaction>();
        this.customers = new ArrayList<Customer>();
    }

    public void linkCustomer(Customer customer){
        this.customers.add(customer);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public List<Transaction> getTransactions(){
        return this.transactions;
    }

    public long getAccountNumber(){
        return this.accountNumber;
    }

    public void deposit(double amount,String depositType) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,depositType));
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");

        if(sumTransactions()-amount<0)
            return false;
        transactions.add(new Transaction(-amount,"Withdraw"));
        return true;


    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }



    public String statementForAccount() {
        String statement;

        //Translate to pretty account type
        statement = getAccountType()+"\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : getTransactions()) {
            statement += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        statement += "Total " + toDollars(total);
        return statement;
    }

    public String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }




    public abstract double interestEarned();

    public abstract String getAccountType();

}

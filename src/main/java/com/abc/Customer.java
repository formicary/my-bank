package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    private Account account;
    private List<Customer> allCustomers;

    /**
     * Constructs a new arraylist with the account name.
     * @param name of the account
     */

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();

        this.allCustomers = new ArrayList<>();

    }

    public void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }


    public String customerSummary(Customer customer) {
        String summary = "Customer Summary";
        for (Customer c : allCustomers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }


    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
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

//    public String getStatement(Account account) {
//        String statement = null;
//        statement = "Statement for " + name + "\n";
//        BigDecimal total = new BigDecimal(0.0);
//        for (Account a : accounts) {
//            statement += "\n" +  account.statementForAccount(a) + "\n";
//            total = total.add(a.getAccountBalance());
//        }
//        statement += "\nTotal In All Accounts " + Conversion.toDollars(total);
//        return statement;
//    }

    public String getStatement(){

        String s = this.name + "'s Statement: \n";
        BigDecimal total = new BigDecimal(0.0);
        for (Account a : accounts) {
            s += "\n" +  a.getAccountSummary() + "\n";
            total = total.add(a.getAccountBalance());
        }
        s += "\nTotal In All Accounts " + Conversion.toDollars(total);
        return s;
    }


}

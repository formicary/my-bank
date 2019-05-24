package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.transfers.TransferContext;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    private List<Account> accounts;


    public Bank() {
        customers = new ArrayList<Customer>();
        accounts = new ArrayList<Account>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addCustomer(Customer customer){
        for(Customer c : customers)
            if(c.getName().equals(customer.getName()))
                throw new IllegalArgumentException("Customer already added!");
        this.customers.add(customer);
    }

    public void addAccount(Account account){
        for(Account a : accounts)
            if(a.getAccountNumber()==account.getAccountNumber())
                throw new IllegalArgumentException("Account already exists!");
        this.accounts.add(account);
    }

    public void linkCustomerToAccount(Customer customer, Account account){
        if(!customers.contains(customer))
            throw new IllegalArgumentException("Customer doesn't exist!");
        if(!accounts.contains(account))
            throw new IllegalArgumentException("Account doesn't exist!");
        accounts.get(accounts.indexOf(account)).linkCustomer(customer);

    }

    public List<Customer> getCustomers(){
        return this.customers;
    }


    public List<Account> getCustomersAccount(Customer customer){
        List <Account> customerAccounts = new ArrayList<Account>();
        for(Account account : accounts){
            if(account.getCustomers().contains(customer))
                customerAccounts.add(account);
        }
        return customerAccounts;
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
            int numberOfAccounts = getCustomersAccount(c).size();
            summary += "\n - " + c.getName() + " (" + format(numberOfAccounts, "account") + ")";
        }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Account account : accounts)
            for(Transaction transaction : account.getTransactions())
                if(transaction.getTransactionType().equals("Interest"))
                    total += transaction.getAmount();

        return total;
    }

    public void transferMoney(Account accountFrom, Account accountTo,double amount) throws Exception {
        TransferContext transferContext = new TransferContext(accountFrom,accountTo);
        transferContext.transfer(amount);
    }


    public void dailyInterestPay(){
        double interest;
        for(Account account : accounts){
            interest = account.interestEarned();
            interest /= 365;
            if(interest>0)
                account.deposit(interest,"Interest");
        }
    }


}

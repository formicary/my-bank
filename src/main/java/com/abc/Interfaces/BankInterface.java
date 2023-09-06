package com.abc.Interfaces;

import com.abc.MainClasses.Customer;

//Interface for Bank class
public interface BankInterface {
    public void addCustomer(Customer customer);

    public String customerSummary();

    public double totalInterestPaid();

    public String getFirstCustomer();

    public String getLastCustomer();
}

package com.abc;

import java.util.Map;


public interface SystemManagement {

    void addCustomer(Customer customer);

    String customerSummary();

    double totalInterestPaid();

    Map<Integer, Customer> getCustomers();

    String getFirstCustomer();


}

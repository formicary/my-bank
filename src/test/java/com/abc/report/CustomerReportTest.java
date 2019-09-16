package com.abc.report;

import com.abc.Account.CheckingAccount;
import com.abc.Bank;
import com.abc.Customer.Customer;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerReportTest {

    @Test
    public void getCustomerReport() {
        Customer john = new Customer("John");
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(john);
        john.addAccount(new CheckingAccount());

        assertEquals("Customer Summary\n - John (1 account)", new CustomerReport(customers).getCustomerReport());
    }
}
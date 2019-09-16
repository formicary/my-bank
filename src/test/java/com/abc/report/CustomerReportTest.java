package com.abc.report;

import com.abc.account.CheckingAccount;
import com.abc.customer.Customer;
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

        assertEquals("customer Summary\n - John (1 account)", new CustomerReport(customers).getCustomerReport());
    }
}
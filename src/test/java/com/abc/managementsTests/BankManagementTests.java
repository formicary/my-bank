package com.abc.managementsTests;

import com.abc.Bank;
import com.abc.Customer;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BankManagementTests {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }


    @Test
    public void customerSummary() {
        Customer john = TestUtils.createCustomer(bank, "John");
        TestUtils.createSavingsAccount(john);
        assertEquals("Customer Summary\n - John (1 account)", bank.getSystemManagement().customerSummary());
    }

    @Test
    public void customerSummaries() {
        Customer john = TestUtils.createCustomer(bank, "John");
        TestUtils.createSavingsAccount(john);
        Customer nick = TestUtils.createCustomer(bank, "Nick");
        TestUtils.createSavingsAccount(nick);
        TestUtils.createCheckingAccount(nick);

        assertEquals("Customer Summary\n - John (1 account)\n - Nick (2 accounts)", bank.getSystemManagement().customerSummary());
    }
}


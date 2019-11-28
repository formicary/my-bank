package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {
    private static final double DELTA = 1e-15;

    @Test
    public void bank() {
        Bank bank = new Bank();
        assertTrue(bank instanceof Bank);
    }

    // Test adding and retrieving a customer to the bank
    @Test
    public void testAddCustomer() {
        Bank testBank = new Bank();
        Customer john = new Customer("John", "Doe");

        // Add one customer
        testBank.addCustomer(john);
        assertEquals("John", testBank.getBankCustomers().get(0).getFirstName());
    }

    // Test adding multiple customers
    @Test
    public void testMultipleCustomers() {
        Bank testBank = new Bank();
        Customer john = new Customer("John", "Doe");
        Customer cristi = new Customer("Cristian", "Beldie");
        Customer mary = new Customer("Mary", "Jane");

        // Add three customers
        testBank.addCustomer(john, cristi, mary);
        assertEquals("John", testBank.getBankCustomers().get(0).getFirstName());
        assertEquals("Cristian", testBank.getBankCustomers().get(1).getFirstName());
        assertEquals("Mary", testBank.getBankCustomers().get(2).getFirstName());
        // Adding extra customers
        testBank.addCustomer(new Customer("Jane", "Doe"), new Customer("Richard", "Roe"));
        assertEquals("Jane", testBank.getBankCustomers().get(3).getFirstName());
        assertEquals("Richard", testBank.getBankCustomers().get(4).getFirstName());
    }

    // Getting customer summary
    @Test
    public void testCustomerSummary() {
        Bank testBank = new Bank();
        Customer john = new Customer("John", "Doe");
        Customer cristi = new Customer("Cristian", "Beldie");

        // Open some accounts for both
        john.openAccount(new CheckingAccount());
        cristi.openAccount(new CheckingAccount());
        cristi.openAccount(new SavingsAccount());

        // Test individual parts of the customer summary
        // Name for customers
        assertEquals("John", john.getFirstName());
        assertEquals("Doe", john.getLastName());
        assertEquals("Cristian", cristi.getFirstName());
        assertEquals("Beldie", cristi.getLastName());
        // Number of accounts
        assertEquals(1, john.getNumberOfAccounts());
        assertEquals(2, cristi.getNumberOfAccounts());
    }

    // Getting a report for total interest paid
    @Test
    public void getInterestReport() {
        Bank testBank = new Bank();
        Customer john = new Customer("John", "Doe");
        Customer cristi = new Customer("Cristian", "Beldie");

        // Open accounts and deposit money
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        checkingAccount.deposit(2000);
        savingsAccount.deposit(1200);
        john.openAccount(checkingAccount);
        cristi.openAccount(savingsAccount);

        // Add customers to bank
        testBank.addCustomer(john, cristi);
        // Test report for interest
        final String report = "Total interest paid by the Bank: $3.40";
        assertEquals(report, testBank.totalInterestReport().toString());
    }
}

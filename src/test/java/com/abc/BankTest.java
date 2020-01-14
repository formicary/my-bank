package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



// The test of the bank class should:
// 1) Cover all public methods.
// 2) Verify that: a bank manager can get a report showing the list of
// customers and how many accounts they have.
// 3) Verify that: a bank manager can get a report showing the total 
// interest paid by the bank on all accounts. 
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    // Tests of all public methods.

    // Tests that customers can be added to a bank. 
    @Test
    public void addCustomerTest() {
        // A bank should be able to add customers. 
        Bank testBank = new Bank();
        testBank.addCustomer(new Customer("John Doe"));
        testBank.addCustomer(new Customer("Felix Gardener"));
        testBank.addCustomer(new Customer("Sarah Walker"));

        String customer_transcript = testBank.customerSummary();

        Pattern firstExpectedName = Pattern.compile("John Doe");
        Matcher nameFinder = firstExpectedName.matcher(customer_transcript);

        assertTrue("John Doe should be in the customer summary",
                    nameFinder.find());

        Pattern secondExpectedName = Pattern.compile("Felix Gardener");
        nameFinder = secondExpectedName.matcher(customer_transcript);

        assertTrue("Felix Gardener should be in the customer summary",
                    nameFinder.find());

        Pattern thirdExpectedName = Pattern.compile("Sarah Walker");
        nameFinder = thirdExpectedName.matcher(customer_transcript);

        assertTrue("Sarah Walker should be in the customer summary",
                    nameFinder.find());
    }

    // Test that customer summary works. Also tests item 2)
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("CustomerSummary of expected format should be generated",
                "Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    // Tests the total interest paid function. Also tests item 3) 
    @Test
    public void totalInterestPaidByAllCustomersTest() {
        Bank testBank = new Bank();

        Customer john = new Customer("John");
        Customer susan = new Customer("Susan");

        CheckingAccount johnsCheckingAccount = new CheckingAccount();
        CheckingAccount susansCheckingAccount = new CheckingAccount();
        john.openAccount(johnsCheckingAccount);
        susan.openAccount(susansCheckingAccount);
        johnsCheckingAccount.deposit(5320.0);
        susansCheckingAccount.deposit(29219992.0); // Susan is rich
        
        double expectedInterest = johnsCheckingAccount.interestEarned();
        expectedInterest += susansCheckingAccount.interestEarned();

        testBank.addCustomer(john);
        testBank.addCustomer(susan);

        assertEquals("Total interest paid by all customers should return some expected value", 
                    expectedInterest, testBank.totalInterestPaidByAllCustomers(), DOUBLE_DELTA);
    }

    // Tests the getFirstCustomer function
    @Test
    public void getFirstCustomerTest() {
        Bank testBank = new Bank();

        Customer skipper = new Customer("Skipper");
        Customer kowalski = new Customer("Kowalski");
        Customer privatep = new Customer("Private");
        Customer rico = new Customer("Rico");

        testBank.addCustomer(skipper);
        testBank.addCustomer(kowalski);
        testBank.addCustomer(privatep);
        testBank.addCustomer(rico);

        assertEquals("First customer should be as set",
                            skipper.getName(), testBank.getFirstCustomer());
    }
}

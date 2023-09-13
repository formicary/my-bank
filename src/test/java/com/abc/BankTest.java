package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

/**
 * This class contains unit tests for the Bank class.
 * These tests cover various aspects of bank functionality.
 */
public class BankTest {

    private Bank bank;
    private Customer customer;

    /**
     * Initializes a new Bank and Customer instance before each test.
     */
    @Before
    public void setUp() {
        bank = new Bank();
        customer = new Customer("Alice");
    }

    /**
     * Tests adding a customer to the bank and checks if the bank contains the
     * customer.
     */
    @Test
    public void addCustomer() {
        bank.addCustomer(customer);
        assertEquals(1, bank.getNumberOfCustomers());
    }

    /**
     * Tests generating a summary of customer information.
     */
    @Test
    public void customerSummary() {
        customer.openAccount(new CheckingAccount());
        bank.addCustomer(customer);
        assertEquals("Customer Summary\n - Alice (1 account)", bank.customerSummary());
    }

    /**
     * Tests calculating the total interest paid by the bank to its customers.
     * This also implicitly tests the interest calculations for Checking and MaxiSavings accounts.
     */
    @Test
    public void totalInterestPaid() {
        CheckingAccount aliceChecking = new CheckingAccount();
        SavingsAccount aliceSavings = new SavingsAccount();
        MaxiSavingsAccount aliceMaxiSavings = new MaxiSavingsAccount();        

        customer.openAccount(aliceChecking);
        customer.openAccount(aliceSavings);
        customer.openAccount(aliceMaxiSavings);

        bank.addCustomer(customer);

        aliceChecking.deposit(new BigDecimal("1000.00"));
        aliceSavings.deposit(new BigDecimal("1500.00"));
        aliceMaxiSavings.deposit(new BigDecimal("2000.00"));

        // Alice's expected CheckingAccount interest
        // = 1000 * (1 + (0.001/365))^400 - 1000
        // = 1.10
        // Alice's expected Savings interest
        // = 1500 * (1 + (0.002/365))^400 - 1500
        // = 3.29
        // Alice's expected MaxiSavingsAccount interest:
        // = 2000 * (1 + (0.05/365))^400 - 2000
        // = 112.64

        BigDecimal expectedTotalInterest = new BigDecimal("117.03");

        BigDecimal actualTotalInterest = bank.totalInterestPaid(400);

        assertEquals(expectedTotalInterest, actualTotalInterest);
    }
}

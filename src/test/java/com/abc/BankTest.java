package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class contains unit tests for the Bank class.
 * These tests cover various aspects of bank functionality.
 */
public class BankTest {
    
    private static final double DOUBLE_DELTA = 0.01;

    /**
     * Tests adding a customer to the bank and checks if the bank contains the customer.
     */
    @Test
    public void addCustomer() {
        Bank bank = new Bank();
        Customer customer = new Customer("Alice");

        bank.addCustomer(customer);

        assertTrue(bank.getNumberOfCustomers() == 1);
    }

    /**
     * Tests generating a summary of customer information.
     */
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");

        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    /**
     * Tests calculating the total interest paid by the bank to its customers.
     */
    @Test
    public void totalInterestPaid() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        Customer bob = new Customer("Bob");

        Account aliceChecking = new Account(Account.CHECKING);
        Account bobSavings = new Account(Account.SAVINGS);

        alice.openAccount(aliceChecking);
        bob.openAccount(bobSavings);

        aliceChecking.deposit(1000.0);
        bobSavings.deposit(1500.0);

        bank.addCustomer(alice);
        bank.addCustomer(bob);

        // Calculate expected total compounded interest paid after a year
        double aliceDailyInterestRate = Account.CHECKING_INTEREST_RATE / 365.0;

        double aliceExpectedInterest = 1000.0 * (Math.pow(1 + aliceDailyInterestRate, 365) - 1);
        double bobExpectedInterest = calculateSavingsInterest(1500.0);
        double expectedTotalInterest = aliceExpectedInterest + bobExpectedInterest;

        double actualTotalInterest = bank.totalInterestPaid();

        assertEquals(expectedTotalInterest, actualTotalInterest, DOUBLE_DELTA);
    }

    /**
     * Tests adding multiple customers to the bank and checking if their names are included in the summary.
     */
    @Test
    public void multipleCustomers() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        Customer bob = new Customer("Bob");

        bank.addCustomer(alice);
        bank.addCustomer(bob);

        String summary = bank.customerSummary();

        assertTrue(summary.contains("Alice"));
        assertTrue(summary.contains("Bob"));
    }

    /**
     * Tests getting the first customer from an empty bank.
     */
    @Test
    public void getFirstCustomerEmpty() {
        Bank bank = new Bank();

        assertEquals("No customers found.", bank.getFirstCustomer());
    }

    /**
     * Tests getting the first customer from a bank with one customer.
     */
    @Test
    public void getFirstCustomerNotEmpty() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");

        bank.addCustomer(alice);

        assertEquals("Alice", bank.getFirstCustomer());
    }

    /**
     * Tests adding customers with the same name and ensuring they are treated independently.
     */
    @Test
    public void addCustomersWithSameName() {
        Bank bank = new Bank();
        Customer alice1 = new Customer("Alice");
        Customer alice2 = new Customer("Alice");

        bank.addCustomer(alice1);
        bank.addCustomer(alice2);

        // Ensure that both customers with the same name are treated independently
        assertEquals(2, bank.getNumberOfCustomers());
    }

    /**
     * Tests adding multiple accounts to a customer and checking the number of accounts.
     */
    @Test
    public void addAccountsToCustomers() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        alice.openAccount(checkingAccount);
        alice.openAccount(savingsAccount);
        alice.openAccount(maxiSavingsAccount);

        bank.addCustomer(alice);

        assertEquals(3, alice.getNumberOfAccounts());
    }

    /**
     * Helper method to calculate expected savings account interest.
     *
     * @param amount The amount to calculate interest for.
     * @return The expected interest amount.
     */
    public double calculateSavingsInterest(double amount) {
        double dailyLowRate = Account.SAVINGS_LOW_INTEREST_RATE / 365.0;
        double dailyHighRate = Account.SAVINGS_HIGH_INTEREST_RATE / 365.0;
        double thresholdAmount = Account.SAVINGS_THRESHOLD;

        // Calculate interest accrued after 1 day and compound it for a year
        double expectedInterest = thresholdAmount * Math.pow(1 + dailyLowRate, 365) +
                (amount - thresholdAmount) * Math.pow(1 + dailyHighRate, 365) - amount;

        double roundedExpectedInterest = Math.round(expectedInterest * 100.0) / 100.0;
        return roundedExpectedInterest;
    }
}

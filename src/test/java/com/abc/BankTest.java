package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class BankTest {

    @Test
    public void addCustomer() {
        Bank bank = new Bank();
        Customer customer = new Customer("Smith", "John");

        int customerId = customer.getId();
        int returnedId = bank.addCustomer(customer);
        assertEquals(
                "Test that returned int is the value of the parameter customer\'s id",
                customerId,
                returnedId);

        assertEquals(
                "Test that customer is added to customers list",
                customerId,
                bank.getFirstCustomer().getId());

        Customer secondCustomer = new Customer("Smith", "Jane");
        int secondCustomerId = secondCustomer.getId();
        bank.addCustomer(secondCustomer);
        assertNotNull(
                "Test that multiple customers can be added", bank.getCustomer(secondCustomerId));
        assertNotNull(
                "Test that adding additional customers does not effect existing  customers",
                bank.getCustomer(customerId));
    }

    @Test
    public void customerSummary() {
        Customer customerOne = new Customer("Doe", "Jane");
        Customer customerTwo = new Customer("Simpson", "Marge");
        customerOne.openMaxiSavingsAccount();
        customerOne.openCheckingAccount();
        customerOne.openCheckingAccount();
        customerTwo.openCheckingAccount();
        int customerOneAccounts = customerOne.getNumberOfAccounts();

        Bank bank = new Bank();
        bank.addCustomer(customerTwo);
        bank.addCustomer(customerOne);

        String summery = bank.customerSummary();

        assertTrue(
                "Test that result includes customer surname",
                summery.contains(customerTwo.getSurname()));
        assertTrue(
                "Test that result includes customer first name",
                summery.contains(customerOne.getFirstName()));
        assertTrue("Test that result includes number of customer accounts", summery.contains(Integer.toString(customerOneAccounts)));
        assertTrue(
                "Test that customers are ordered by ID",
                summery.indexOf(customerOne.getSurname())
                        < summery.indexOf(customerTwo.getSurname()));
    }

    @Test
    public void totalInterestPaid() {
        final int BIG_DECIMAL_SCALE = 10;

        Customer customerOne = new Customer("Simpson", "Homer");
        CheckingAccount customerOneCheckingAccount = new CheckingAccount();
        customerOneCheckingAccount.deposit(500);
        customerOneCheckingAccount.getTransactions().get(0).setTransactionDate(ZonedDateTime.now().minusDays(4));
        SavingsAccount customerOneSavingsAccount = new SavingsAccount();
        customerOneSavingsAccount.deposit(25.76);
        customerOneSavingsAccount.getTransactions().get(0).setTransactionDate(ZonedDateTime.now().minusDays(15));
        customerOne.addAccount(customerOneCheckingAccount);
        customerOne.addAccount(customerOneSavingsAccount);

        Customer customerTwo = new Customer("Fry", "Philip");
        CheckingAccount customerTwoCheckingAccount = new CheckingAccount();
        customerTwoCheckingAccount.deposit(123.45);
        customerTwoCheckingAccount.getTransactions().get(0).setTransactionDate(ZonedDateTime.now().minusDays(5));
        customerTwo.addAccount(customerTwoCheckingAccount);

        BigDecimal interestEarnedByBothCustomers = customerOne.totalInterestEarned().add(customerTwo.totalInterestEarned());

        Bank bank = new Bank();
        bank.addCustomer(customerOne);
        bank.addCustomer(customerTwo);
        assertEquals("Test that result is the combined interest earned by all the bank\'s customers", interestEarnedByBothCustomers.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP), bank.totalInterestPaid().setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));
    }

    @Test
    public void getFirstCustomer() {
        Customer firstCustomer = new Customer("Simpson", "Lisa");
        Customer secondCustomer = new Customer("Simpson", "Bart");
        Bank bank = new Bank();
        bank.addCustomer(firstCustomer);
        bank.addCustomer(secondCustomer);
        assertEquals("Test that result is the first customer added to the bank", firstCustomer, bank.getFirstCustomer());
    }

    @Test
    public void getCustomer() {
        Customer otherCustomerOne = new Customer("Simpson", "Maggie");
        Customer searchCustomer = new Customer("Fry", "Philip");
        Customer otherCustomerTwo = new Customer("Farnsworth", "Hubert");

        Bank bank = new Bank();
        bank.addCustomer(otherCustomerOne);
        bank.addCustomer(searchCustomer);
        bank.addCustomer(otherCustomerTwo);

        Customer returnedCustomer = bank.getCustomer(searchCustomer.getId());
        assertEquals("Test that customer returned is the customer whose id was passed as a parameter", searchCustomer, returnedCustomer);
    }
}

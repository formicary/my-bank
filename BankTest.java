package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private Bank bank;

    @org.junit.Before
    public void setup() {
        bank = new Bank();
    }

    // create a customer within bank
    @Test
    public void createCustomer() {
        Assert.assertEquals(0, bank.getCustomers().size(),0);
        Assert.assertEquals("FirstName LastName", bank.createCustomer("FirstName LastName").getName());
        Assert.assertEquals(1, bank.getCustomers().size(),0);
        Assert.assertEquals(1, bank.getCustomers().get(0).getId());
        Assert.assertEquals(0, bank.getCustomers().get(0).getAccounts().size());
    }

    // get the customers overview, no customers created so far
    @Test
    public void getCustomersOverview_noCustomerSoFar() {
        Assert.assertNull(bank.getCustomersOverview());
    }

    // get the customers overview, 2 customers created so far
    @Test
    public void getCustomersOverview_existingCustomers() {
        bank.getCustomers().add(new Customer("FirstName LastName"));
        Customer customer1 = bank.getCustomers().get(0);
        bank.getCustomers().add(new Customer("FirstName2 LastName2"));
        Customer customer2 = bank.getCustomers().get(1);
        customer1.getAccounts().add(new CheckingAccount());
        customer1.getAccounts().add(new SavingsAccount());
        customer1.getAccounts().add(new MaxiSavingsAccount());
        customer2.getAccounts().add(new CheckingAccount());
        customer2.getAccounts().add(new SavingsAccount());
        customer2.getAccounts().add(new CheckingAccount());
        Assert.assertEquals(1, bank.getCustomersOverview().get(0).getId(),0);
        Assert.assertEquals("FirstName LastName", bank.getCustomersOverview().get(0).getName());
        Assert.assertEquals(3, bank.getCustomersOverview().get(0).getNumberOfAccounts(),0);
        Assert.assertEquals(2, bank.getCustomersOverview().get(1).getId(),0);
        Assert.assertEquals("FirstName2 LastName2", bank.getCustomersOverview().get(1).getName());
        Assert.assertEquals(3, bank.getCustomersOverview().get(1).getNumberOfAccounts(),0);
    }

    // get total interest paid, no customers created so far, no accounts, no interest paid
    @Test
    public void getTotalInterestPaid_noCustomersSoFar() {
        Assert.assertEquals(0d, bank.getTotalInterestPaid(),0);
    }

    // get total interest paid, existing customer with accounts, no interest paid so far
    @Test
    public void getTotalInterestPaid_existingCustomer_existingAccount_noInterestSoFar() {
        bank.getCustomers().add(new Customer("FirstName LastName"));
        Customer customer = bank.getCustomers().get(0);
        customer.getAccounts().add(new CheckingAccount());
        Account account = customer.getAccounts().get(0);
        account.getTransactions().add(new Transaction(100d, LocalDate.now()));
        Assert.assertEquals(0d, bank.getTotalInterestPaid(),0);
    }

    // get total interest paid, existing customer with accounts, interest paid for 3 days so far
    @Test
    public void getTotalInterestPaid_existingCustomer_existingAccount_existingInterest() {
        double result = 1000d;
        bank.getCustomers().add(new Customer("FirstName LastName"));
        Customer customer = bank.getCustomers().get(0);
        customer.getAccounts().add(new CheckingAccount());
        Account account = customer.getAccounts().get(0);
        account.getTransactions().add(new Transaction(1000d, LocalDate.now().minusDays(3)));

        for(int i = 0;i < 3;i++) {
            result += result*(0.001d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
        }
        result -= 1000d;
        System.out.println(result);
        assertEquals(result, bank.getTotalInterestPaid(),0);
    }

    // get total interest paid, existing 2 customers with accounts, interest paid for 3 and 5 days respectively
    @Test
    public void getTotalInterestPaid_existing2Customers_existingAccounts_existingInterests() {
        double result1 = 1000d, result2 = 1000d;
        bank.getCustomers().add(new Customer("FirstName LastName"));
        Customer customer = bank.getCustomers().get(0);
        customer.getAccounts().add(new CheckingAccount());
        Account account = customer.getAccounts().get(0);
        account.getTransactions().add(new Transaction(1000d, LocalDate.now().minusDays(3)));

        bank.getCustomers().add(new Customer("FirstName LastName"));
        Customer customer2 = bank.getCustomers().get(1);
        customer2.getAccounts().add(new CheckingAccount());
        Account account2 = customer2.getAccounts().get(0);
        account2.getTransactions().add(new Transaction(1000d, LocalDate.now().minusDays(5)));

        for(int i = 0;i < 3;i++) {
            result1 += result1*(0.001d/(LocalDate.now().minusDays(3 - i).isLeapYear() ? 366 : 365));
        }
        result1 -= 1000d;

        for(int i = 0;i < 5;i++) {
            result2 += result2*(0.001d/(LocalDate.now().minusDays(5 - i).isLeapYear() ? 366 : 365));
        }
        result2 -= 1000d;

        assertEquals(result1 + result2, bank.getTotalInterestPaid(),0);
    }
}
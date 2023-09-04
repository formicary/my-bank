package com.abc;

import com.util.BigDecimalProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    Bank bank;
    Customer customer;

    @Before
    public void setUp() {
        bank = new Bank();
        customer = new Customer("John");
        bank.addCustomer(customer);
    }

    @After
    public void tearDown() {
        bank = null;
        customer = null;
    }

    @Test
    public void addCustomerTest() {
        bank.addCustomer(customer);
        assertEquals(true, bank.hasCustomer(customer));
    }

    @Test
    public void getAllCustomersSummaryTest() {
        customer.openAccount(new AccountChecking());
        assertEquals("Customer Summary\n - John (1 account)", bank.getAllCustomersSummary());
    }

    @Test
    public void getTotalInterestsPaidTest_CHECKING() {
        AccountChecking accountChecking = new AccountChecking();
        customer.openAccount(accountChecking);
        accountChecking.deposit(BigDecimalProvider.format(798));
        assertEquals(BigDecimalProvider.format(0.80), bank.getTotalInterestsPaid());
    }

    @Test
    public void getTotalInterestsPaidTest_SAVINGS_LOW() {
        Account account = new AccountSavings();
        customer.openAccount(account);
        account.deposit(BigDecimalProvider.format(798));
        assertEquals(BigDecimalProvider.format(0.80), bank.getTotalInterestsPaid());
    }

    @Test
    public void getTotalInterestsPaidTest_SAVINGS_HIGH() {
        Account account = new AccountSavings();
        customer.openAccount(account);
        account.deposit(BigDecimalProvider.format(3765));
        assertEquals(BigDecimalProvider.format(6.53), bank.getTotalInterestsPaid());
    }

    @Test
    public void getTotalInterestsPaidTest_MAXI_SAVINGS_LOW() {
        Account account = new AccountMaxiSavings();
        customer.openAccount(account);
        account.deposit(BigDecimalProvider.format(587));
        assertEquals(BigDecimalProvider.format(11.74), bank.getTotalInterestsPaid());
    }

    @Test
    public void getTotalInterestsPaidTest_MAXI_SAVINGS_MID() {
        Account account = new AccountMaxiSavings();
        customer.openAccount(account);
        account.deposit(BigDecimalProvider.format(1587));
        assertEquals(BigDecimalProvider.format(49.35), bank.getTotalInterestsPaid());
    }


    @Test
    public void getTotalInterestsPaidTest_MAXI_SAVINGS_HIGH() {
        Account account = new AccountMaxiSavings();
        customer.openAccount(account);
        account.deposit(BigDecimalProvider.format(2587));
        assertEquals(BigDecimalProvider.format(128.70), bank.getTotalInterestsPaid());
    }
}

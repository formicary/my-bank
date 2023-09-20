package com.abc;

import org.junit.Test;

import com.abc.Utilities.Enums.AccountType;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

// Todo: Fix tests and ensure all scenarios covered
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-2;

    private Bank bank;
    private Customer customer;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    @Before
    public void setup() {
        bank = new Bank();
        customer = new Customer("Jade");
        checkingAccount = new Account(AccountType.CHECKING);
        savingsAccount = new Account(AccountType.SAVINGS);
        maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
    }

    @After
    public void tearDown() {
        bank = null;
        customer = null;
        checkingAccount = null;
        savingsAccount = null;
        maxiSavingsAccount = null;
    }

    @Test
    public void customerSummary() {
        bank.addCustomer(customer);
        customer.openAccount(checkingAccount);
        
        assertEquals("Customer Summary\n - Jade (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        bank.addCustomer(customer);
        customer.openAccount(checkingAccount);

        checkingAccount.depositFunds(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        bank.addCustomer(customer);
        customer.openAccount(savingsAccount);

        savingsAccount.depositFunds(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        bank.addCustomer(customer);
        customer.openAccount(maxiSavingsAccount);

        maxiSavingsAccount.depositFunds(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

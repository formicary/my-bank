package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummaryOneCustomer() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank);
        john.openAccount(Account.CHECKING);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummaryThreeCustomers() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank);
        john.openAccount(Account.CHECKING);
        Customer tim = new Customer("Tim", bank);
        tim.openAccount(Account.SAVINGS);
        tim.openAccount(Account.SAVINGS);
        Customer gary = new Customer("Gary", bank);
        String expected = "Customer Summary\n - John (1 account)\n - Tim (2 accounts)\n - Gary (0 accounts)";
        assertEquals(expected, bank.customerSummary());
    }

    @Test
    public void testCheckingAccountInterest() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", bank);
        Account checkingAccount = bill.openAccount(Account.CHECKING);

        checkingAccount.deposit(100.0);
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountInterest() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", bank);

        Account checkingAccount = bill.openAccount(Account.SAVINGS);

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountInterest() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill", bank);
        Account checkingAccount = bill.openAccount(Account.MAXI_SAVINGS);

        checkingAccount.deposit(3000.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
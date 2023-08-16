package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testTotalInterestPaidSameCustomer() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(100.0);
        checkingAccount.deposit(100.0);

        assertEquals(0.2, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testTotalInterestPaidDifferentCustomer() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        bank.addCustomer(new Customer("Mary").openAccount(checkingAccount));
        savingsAccount.deposit(1500.0);
        checkingAccount.deposit(5000);

        assertEquals(7.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testGetFirstCustomer() {
        Bank bank = new Bank();
        Account maxiSaverAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Mary").openAccount(maxiSaverAccount));
        bank.addCustomer(new Customer("Bill").openAccount(maxiSaverAccount));
        bank.addCustomer(new Customer("John").openAccount(maxiSaverAccount));

        assertEquals("Mary", bank.getFirstCustomer());
    }

}

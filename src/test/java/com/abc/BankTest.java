package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(Account.AccountType.CHECKING);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testTotalInterest() {
        Bank bank = new Bank();

        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Customer alice = new Customer("Alice");
        bank.addCustomer(alice);
        Account aliceCheckingAccount = alice.openAccount(Account.AccountType.CHECKING);
        aliceCheckingAccount.deposit(100.0);


        Customer bob = new Customer("Bob");
        bank.addCustomer(bob);
        Account bobCheckingAccount = bob.openAccount(Account.AccountType.CHECKING);
        bobCheckingAccount.deposit(100.0);

        c.add(Calendar.DATE, 1);
        c.add(Calendar.MILLISECOND, 1);

        DateProvider.getInstance().setCustomDate(c.getTime());

        bank.updateInterestPayments();

        assertEquals(2.0 * (100.0 * 0.001 / 365.25), bank.totalInterestPaid(), DOUBLE_DELTA);

    }

}

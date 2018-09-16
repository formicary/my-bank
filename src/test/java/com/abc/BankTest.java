package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        
        final Customer customer = bank.addCustomer("John");
        bank.openAccount(customer.getName(), new CheckingAccount());

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer customer = bank.addCustomer("Bill");
        bank.openAccount(customer.getName(), checkingAccount);
        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        Customer customer = bank.addCustomer("Bill");
        bank.openAccount(customer.getName(), checkingAccount);

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        Customer customer = bank.addCustomer("Bill");
        bank.openAccount(customer.getName(), checkingAccount);

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

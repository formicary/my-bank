package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openCheckingAccount();
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.openCheckingAccount();
        int accountNumberToDeposit = 0;
        double amount = 100.0;
        bill.depositFunds(accountNumberToDeposit, amount);
        bank.addCustomer(bill);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.openSavingsAccount();
        bank.addCustomer(bill);
        int accountNumberToDeposit = 0;
        double amount = 1500.0;
        bill.depositFunds(accountNumberToDeposit, amount);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.openMaxiSavingsAccount();
        bank.addCustomer(bill);
        
        int accountNumberToDeposit = 0;
        double amount = 3000.0;
        
        bill.depositFunds(accountNumberToDeposit, amount);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

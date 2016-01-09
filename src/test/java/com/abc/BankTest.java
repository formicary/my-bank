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
    public void testMultipleCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer bill = new Customer("Bill");
        john.openCheckingAccount();
        bank.addCustomer(john);
        bill.openMaxiSavingsAccount();
        bank.addCustomer(bill);

        assertEquals("Customer Summary\n - John (1 account)\n - Bill (1 account)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openCheckingAccount();
        bill.depositFunds(0, 100.0);

       assertEquals(0.10, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openSavingsAccount();
        bill.depositFunds(0, 1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.openMaxiSavingsAccount();
        bank.addCustomer(bill);
        bill.depositFunds(0, 3000.0);

        assertEquals(153.8, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountWithWithdrawal() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bill.openMaxiSavingsAccount();
        bank.addCustomer(bill);
        bill.depositFunds(0, 3100.0);
        bill.withdrawFunds(0, 100.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testMultipleCustomers() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Customer oscar = new Customer("Oscar");
        bank.addCustomer(bill);
        bank.addCustomer(oscar);
        bill.openMaxiSavingsAccount();
        oscar.openSavingsAccount();
        bill.depositFunds(0, 3000.0);
        oscar.depositFunds(0, 1500.0);
        
        assertEquals(155.80, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

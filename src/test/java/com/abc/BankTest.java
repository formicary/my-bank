package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    @Test // test 1 account customer summary
    public void testCustomerSummary1() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test // test 2 accounts customer summary (+ plural)
    public void testCustomerSummary2() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);
        Customer sam = new Customer("Sam").openAccount(savingsAccount).openAccount(checkingAccount);
        bank.addCustomer(sam);

        assertEquals("Customer Summary\n - Sam (2 accounts)", bank.customerSummary());
    }

    @Test // Test get first customer
    public void testGetFirstCustomer() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        bank.addCustomer(new Customer("John").openAccount(savingsAccount));
        bank.addCustomer(new Customer("Sam").openAccount(checkingAccount));
        assertEquals("Bill", bank.getFirstCustomer());
    }

    @Test // Test total interest
    public void testTotalInterestPaid() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        bank.addCustomer(new Customer("John").openAccount(savingsAccount));
        bank.addCustomer(new Customer("Sam").openAccount(maxiSavingsAccount));
        
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(500.0);
        maxiSavingsAccount.deposit(500.0);

        assertEquals(10.6, bank.totalInterestPaid(), 0);
    }

}

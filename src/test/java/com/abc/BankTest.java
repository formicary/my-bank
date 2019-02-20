package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void customerSummaryMultipleAccounts() {
        Bank bank = new Bank();
        
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }
    
    @Test
    public void customerSummaryMultipleCustomers() {
        Bank bank = new Bank();
        
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);
        
        Customer katie = new Customer("Katie");
        katie.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(katie);

        assertEquals("Customer Summary\n - John (2 accounts)\n - Katie (1 account)", bank.customerSummary());
    }
    
    @Test
    public void customerSummaryNoCustomers() {
        Bank bank = new Bank();

        assertEquals("Customer Summary", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        checkingAccount.withdraw(700.0);
        
        assertEquals(0.8, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        checkingAccount.deposit(200.0);
        
        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        checkingAccount.deposit(1.0);
        
        assertEquals(1.002, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        checkingAccount.withdraw(100.0);
        
        assertEquals(2.9, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}

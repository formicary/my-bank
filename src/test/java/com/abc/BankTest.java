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
    public void testFirstCustomer() {
    	Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer billy = new Customer("Billy");
        bank.addCustomer(john);
        bank.addCustomer(billy);
        
        assertEquals(bank.getFirstCustomer(), "John");
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
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void dailyInterestChecking() {
    	Bank bank = new Bank();
    	Account account = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(account));
        
        account.deposit(100.0);
        account.dailyInterestAddition();
        
        double expected = 100.0 + (0.1/365.0);
        assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test
    public void dailyInterestSavings() {
    	Bank bank = new Bank();
    	Account account = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(account));
        
        account.deposit(10000.0);
        account.dailyInterestAddition();
        
        double expected = 10000.0 + (19.0/365.0);
        assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test
    public void dailyInterestMaxi() {
    	Bank bank = new Bank();
    	Account account = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(account));
        
        account.deposit(100.0);
        account.dailyInterestAddition();
        
        double expected = 100.0 + (0.1/365.0);
        assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test
    public void multipleDailyInterestChecking() {
    	Bank bank = new Bank();
    	Account account = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(account));
        
        account.deposit(1000.0);
        account.dailyInterestAddition();
        account.dailyInterestAddition();
        
        double totalAfterFirst = 1000.0 + (1.0/365.0);
        double expected = 1000.0 + (1.0/365.0) + ((totalAfterFirst * 0.001)/365.0);
        
        assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
    }

}

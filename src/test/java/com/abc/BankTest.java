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
        
        assertEquals("Customer Summary\n - John (1 account)",
        		bank.customerSummary());
    }

    @Test
    public void testCheckingAccountInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //test rewritten for new maxi savings interest rates
    @Test
    public void testMaxiSavingsAccountInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //tests the change of maxi saving interest rates with recent withdrawals
    @Test
    public void testMaxiSavingsAccountInterestWithdrawal() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(1);//withdrawal within last 10 days
        checkingAccount.deposit(1);
        
        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testFormat() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        Customer bill=new Customer("Bill");
        Customer john=new Customer("John");
        
        bill.openAccount(checkingAccount);
        bill.openAccount(savingAccount);
        john.openAccount(savingAccount);
        
        bank.addCustomer(bill);
        bank.addCustomer(john);
        
        assertEquals("Customer Summary\n - Bill (2 accounts)\n - John (1 account)",
        		bank.customerSummary());
    }
    
    @Test
    public void testGetFirstCustomer() {
        Bank bank = new Bank();
        Customer bill=new Customer("Bill");
        Customer john=new Customer("John");
        
        bank.addCustomer(bill);
        bank.addCustomer(john);
        
        assertEquals(bill.getName(),bank.getFirstCustomer());
    }
    
    @Test
    public void testTotalInterestPaid() {
    	Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        Customer bill=new Customer("Bill");
        Customer john=new Customer("John");
        
        checkingAccount.deposit(1000);
        savingAccount.deposit(1000);
        
        bill.openAccount(checkingAccount);
        bill.openAccount(savingAccount);
        john.openAccount(checkingAccount);
        
        bank.addCustomer(bill);
        bank.addCustomer(john);
        
        assertEquals(3,bank.totalInterestPaid(),DOUBLE_DELTA);
    }
    

}

package com.abc;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;
    private Customer bill;
    
    @Before
    public void setUp() throws Exception {
    	this.checkingAccount = new Account(Account.CHECKING);
    	this.savingsAccount = new Account(Account.SAVINGS);
    	this.maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
    	this.bill = new Customer("Bill");
    }
    
    @After
    public void tearDown() throws Exception {
    	
    }

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
//        bill.openAccount(checkingAccount);
//        bank.addCustomer(bill);
        bank.addCustomer(bill.openAccount(checkingAccount));
        
        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

//	TODO
//    @Test
//    public void testSavingsAccount() {
//        Bank bank = new Bank();
//        Account checkingAccount = new Account(Account.SAVINGS);
//        bank.addCustomer(bill.openAccount(checkingAccount));
//
//        checkingAccount.deposit(1500.0);
//
//        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
//    }
    
    /*
     * TODO - 0.1%
     */
    @Test
    public void testSavingsAccountRateOne() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    /*
     * TODO - 0.2%
     */
    @Test
    public void testSavingsAccountRateTwo() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(2000.0);

        assertEquals(4.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

//    @Test
//    public void testMaxiSavingsAccount() {
//        Bank bank = new Bank();
//        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
//        bank.addCustomer(bill.openAccount(checkingAccount));
//
//        checkingAccount.deposit(3000.0);
//
//        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
//    }

    /**
     * TODO - 2%
     */
    @Test
    public void testMaxiSavingsAccountRateOne() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);

        assertEquals(20, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * TODO - 5%
     */
    @Test
    public void testMaxiSavingsAccountRateTwo() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(2000.0);

        assertEquals(70.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * TODO - 10%
     */
    @Test
    public void testMaxiSavingsAccountRateThree() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(4000.0);

        assertEquals(270.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
//    @Test
//    public void testInterestEarnedChecking() {
//    	oscar.openAccount(checkingAccount);
//    	checkingAccount.deposit(1000.0);
//    	assertEquals(1010.0, savingsAccount.interestEarned(), DOUBLE_DELTA);
//    }
//    
//    @Test
//    public void testInterestEarnedSavingsRateOne() {
//    	oscar.openAccount(savingsAccount);
//    	savingsAccount.deposit(1000.0);
//    	//TODO
//    	assertEquals(1010.0, savingsAccount.interestEarned(), DOUBLE_DELTA);
//    }
//    
//    @Test
//    public void testInterestEarnedSavingsRateTwo() {
//    	oscar.openAccount(savingsAccount);
//    	savingsAccount.deposit(2000.0);
//    	assertEquals(2040.0, savingsAccount.interestEarned(), DOUBLE_DELTA);
//    }
//    
//    @Test
//    public void testInterestEarnedMaxiSavings() {
//    	oscar.openAccount(maxiSavingsAccount);
//    	maxiSavingsAccount.deposit(1000.0);
//    	//TODO
//    	assertEquals(expected, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
//    }

}

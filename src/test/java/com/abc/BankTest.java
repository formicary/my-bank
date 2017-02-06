package com.abc;

import java.util.Calendar;
import java.util.Date;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    
    private Bank bank;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;
    private Customer bill;
    
    @Before
    public void setUp() throws Exception {
    	this.checkingAccount = new Account(Account.CHECKING);
    	this.savingsAccount = new Account(Account.SAVINGS);
    	this.maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
    	this.bank = new Bank();
    	this.bill = new Customer("Bill");
    }
    
    @After
    public void tearDown() throws Exception {
    	
    }

    @Test
    public void customerSummary() {
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void bankInterestSummary() {
    	Customer tom = new Customer("Tom");
    	checkingAccount.deposit(200.0);
    	savingsAccount.deposit(450.0);
    	tom.openAccount(checkingAccount);
    	bank.addCustomer(tom);
    	
    	checkingAccount = new Account(Account.CHECKING);
    	savingsAccount = new Account(Account.SAVINGS);
    	
    	Customer oscar = new Customer("Oscar");
    	savingsAccount.deposit(150.0);
    	maxiSavingsAccount.deposit(225.0);
    	
    	oscar.openAccount(savingsAccount);
    	oscar.openAccount(maxiSavingsAccount);
    	bank.addCustomer(oscar);

        double actual = Double.valueOf((String.format("%.2f", bank.totalInterestPaid())));
    	assertEquals(586.89, actual, DOUBLE_DELTA);
    }

    @Test
    public void checkingAccount() {
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(bill.openAccount(checkingAccount));
        
        checkingAccount.deposit(100.0);
        double actual = Double.valueOf((String.format("%.2f", bank.totalInterestPaid())));
        assertEquals(100.10, actual, DOUBLE_DELTA);
    }
    
    /*
     * TODO - 0.1%
     */
    @Test
    public void savingsAccountRateOne() {
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);
        double actual = Double.valueOf((String.format("%.2f", bank.totalInterestPaid())));
        assertEquals(1001.00, actual, DOUBLE_DELTA);
    }
    
    /*
     * TODO - 0.2%
     */
    @Test
    public void savingsAccountRateTwo() {
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(2000.0);
        double actual = Double.valueOf((String.format("%.2f", bank.totalInterestPaid())));
        assertEquals(1003.00, actual, DOUBLE_DELTA);
    }

    /**
     * TODO - 2%
     */
    @Test
    public void maxiSavingsAccountRateOne() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);
        double actual = Double.valueOf((String.format("%.2f", bank.totalInterestPaid())));
        assertEquals(1051.27, actual, DOUBLE_DELTA);
    }

    /**
     * TODO - 5%
     */
    @Test
    public void maxiSavingsAccountRateTwo() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(2000.0);
        double actual = Double.valueOf((String.format("%.2f", bank.totalInterestPaid())));
        assertEquals(2102.53, actual, DOUBLE_DELTA);
    }

    /**
     * TODO - 10%
     */
    @Test
    public void maxiSavingsAccountRateThree() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(checkingAccount));

        checkingAccount.deposit(4000.0);
        double actual = Double.valueOf((String.format("%.2f", bank.totalInterestPaid())));
        assertEquals(4205.07, actual, DOUBLE_DELTA);
    }
    
    @Test
    public void getFirstCustomer() {
    	Customer tom = new Customer("Tom");
    	Customer oscar = new Customer("Oscar");
    	Customer theo = new Customer("Theo");
    	
    	bank.addCustomer(tom.openAccount(checkingAccount));
    	bank.addCustomer(oscar.openAccount(savingsAccount));
    	bank.addCustomer(theo.openAccount(maxiSavingsAccount));
    	
    	assertEquals("Tom", bank.getFirstCustomer());
    }
    
    //TODO
    @Test
    public void maxiSavings() {
    	Customer tom = new Customer("Tom");
    	bank.addCustomer(tom.openAccount(maxiSavingsAccount));
    	maxiSavingsAccount.deposit(300.0);
    }
    
    @Test
    public void hasWithdrawalBeenMade() {
    	Account a = new Account(Account.MAXI_SAVINGS);
    	boolean expected = true;
    	a.deposit(300.0);
    	a.deposit(100.0);
    	a.withdraw(150.0);
    	a.deposit(100.0);
    	assertEquals(expected, a.hasWithdrawalBeenMade());
    }
    
    @Test
    public void calcCompountInterest() {
    	double expected = 3153.802489402342;
    	Account a = new Account(Account.MAXI_SAVINGS);
    	a.deposit(3000.0);
    	
    	assertEquals(expected, a.interestEarned(), DOUBLE_DELTA);
    }
}

package com.abc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTest {

	private static final double DOUBLE_DELTA = 1e-15;
	Bank bank;
	Customer john, bill;
	Account checkingAccount, savingsAccount, maxiAccount;
	
	/**
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		bank = new Bank();
		john = new Customer("John");
		bill = new Customer("Bill");
		
		checkingAccount = new Account(AccountType.CHECKING);
		savingsAccount = new Account(AccountType.SAVINGS);
		maxiAccount = new Account(AccountType.MAXI_SAVINGS);
	}
	
	/**
	 * 
	 */
    @Test
    public void customerSummary() {
        john.openAccount(checkingAccount);
        bank.addCustomer(john);
        
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    /**
     * 
     */
    @Test
    public void checkingAccount() {
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(100.0);
        
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * 
     */
    @Test
    public void savings_account() {
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);
        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * 
     */
    @Test
    public void maxi_savings_account() {
        bill.openAccount(maxiAccount);
        bank.addCustomer(bill);
        maxiAccount.deposit(3000.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    /**
     * @throws Exception 
     * 
     */
    @Test
    public void first_customer() throws Exception {
    	bank.addCustomer(bill);
    	
    	assertEquals("Bill", bank.getFirstCustomer());
    }
    
    /**
     * 
     */
    @Test
    public void first_customer_exc() {
    	Bank emptyBank = new Bank();
    	Assertions.assertThrows(Exception.class, () -> emptyBank.getFirstCustomer());
    }

}

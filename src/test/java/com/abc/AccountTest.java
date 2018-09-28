package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;


	@Test
	public void testAccountDepositAndWithdraw(){
		Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        
        //tests if customer tried to withdraw more than their account balance
        try {
        	checkingAccount.withdraw(100);
		} catch (Exception e) {
			assertEquals("Insufficient funds. Try to withdraw a smaller amount", e.getMessage());
		}
        
        //tests if customer tried to deposit nothing
        try {
        	checkingAccount.deposit(0);
		} catch (Exception e) {
			assertEquals("Deposit amount must be greater than zero", e.getMessage());
		}
        
        //test deposit function works
        checkingAccount.deposit(100);
        assertEquals(100, checkingAccount.getBalance(), DOUBLE_DELTA);
        
        //check withdraw function works
        checkingAccount.withdraw(50);
        assertEquals(50, checkingAccount.getBalance(), DOUBLE_DELTA);

	}   
}

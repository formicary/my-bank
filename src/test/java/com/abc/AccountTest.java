package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	//test an account can be open
	@Test
	public void testValidAccountCreation(){
		Account checking=new Account(Account.CHECKING);
		assertTrue(checking instanceof Account);
	}
	
	//test an account cannot have invalid account type
	@Test
	public void testInvalidAccountCreation(){
		try {
			Account checking=new Account(-1);
			assert(false);
		}catch(IllegalArgumentException e){
			System.out.println(e);
			assert(true);
		}
	}
	
	//test bounds checking of deposits
	@Test
	public void testDeposit() {
		Account checking=new Account(Account.CHECKING);
		try {
			checking.deposit(-1);
			assert(false);
		}catch(IllegalArgumentException e){
			System.out.println(e);
			checking.deposit(1000);
			assertEquals(1000.0,checking.sumTransactions(),DOUBLE_DELTA);
		}
	}
	
	@Test
	public void testWithdrawal() {
		Account checking=new Account(Account.CHECKING);
		checking.deposit(1000);
		try {
			checking.withdraw(-1);
			assert(false);
		}catch(IllegalArgumentException e){
			System.out.println(e);
			checking.withdraw(400);
			assertEquals(600.0,checking.sumTransactions(),DOUBLE_DELTA);
		}
	}
	
	//tests the calculation of interest earned
	@Test
	public void testInterestEarned() {
		Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        
        checkingAccount.deposit(1000);//earns 0.1% interest == $1
        savingAccount.deposit(2500);//earns 0.1% on 1000 and 0.2% on 1500 == $1+$3

        assertEquals(5.0,checkingAccount.interestEarned()+
        		savingAccount.interestEarned(),DOUBLE_DELTA);
	}
	
	//tests the summation of transactions
	@Test
	public void testSumTransactions() {
		Account checkingAccount = new Account(Account.CHECKING);
		checkingAccount.deposit(1000);
		checkingAccount.deposit(300);
		checkingAccount.withdraw(700);
		checkingAccount.deposit(100);
		
		assertEquals(700.0,checkingAccount.sumTransactions(),DOUBLE_DELTA);
	}
	
}

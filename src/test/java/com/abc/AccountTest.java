package com.abc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private static Customer dummy = new RegularCustomer("dummy", DateProvider.getInstance().now());
	
	@Test
    void testDeposit() {
		Account sa = new SavingsAccount(dummy, 0);
		sa.deposit(1000);
		assert(!sa.getTransactions().isEmpty()):"transaction not recorded";
		assertEquals(1000, sa.getBalance(), DOUBLE_DELTA);
	}

    @Test
    void testWithdraw() {
    	Account ca = new CheckingAccount(dummy, 5000);
    	ca.withdraw(2000);
    	assert(!(ca.getTransactions().isEmpty())):"transaction not recorded";
    	assertEquals(3000, ca.getBalance(), DOUBLE_DELTA);
    }
    
    void testTransfer() {
    	Account ca = new CheckingAccount(dummy, 1000);
    	Account sa = new SavingsAccount(dummy, 100);
    	
    	ca.transferAmount(sa, 300, true);
    	
    	assertEquals(700,ca.getBalance(),DOUBLE_DELTA);
    	assertEquals(400,sa.getBalance(),DOUBLE_DELTA);
    }

    @Test
    void testInterestEarned() {
    	Account ca = new CheckingAccount(dummy, 1000);
    	Account sa1 = new SavingsAccount(dummy, 100);
    	Account sa2 = new SavingsAccount(dummy, 2000);
    	Account msa1 = new MaxiSavingAccount(dummy, 500);
    	Account msa2 = new MaxiSavingAccount(dummy, 1500);
    	Account msa3 = new MaxiSavingAccount(dummy, 3000);
    	
    	assertEquals(1, ca.interestEarned(),DOUBLE_DELTA);
    	assertEquals(0.1, sa1.interestEarned(),DOUBLE_DELTA);
    	assertEquals(3, sa2.interestEarned(),DOUBLE_DELTA);
    	assertEquals(25,msa1.interestEarned(),DOUBLE_DELTA);
    	assertEquals(45,msa2.interestEarned(),DOUBLE_DELTA);
    	assertEquals(120,msa3.interestEarned(),DOUBLE_DELTA);
    }

    @Test
    void testSumTransactions() {
    	Account ca = new CheckingAccount(dummy, 1000);
    	Account sa = new SavingsAccount(dummy, 1500);
    	ca.deposit(10000);
    	ca.withdraw(5000);
    	ca.transferAmount(sa, 300, true);
    	assertEquals(4700,ca.sumTransactions(),DOUBLE_DELTA);
    	assertEquals(300,sa.sumTransactions(),DOUBLE_DELTA);
    }

    @Test
    void testAccountType() {
	   Account ca = new CheckingAccount(dummy,0);
	   Account sa = new SavingsAccount(dummy,0);
	   Account msa = new MaxiSavingAccount(dummy,0);
	   assertEquals("CHECKING ACCOUNT", ca.getAccountType());
	   assertEquals("SAVINGS ACCOUNT", sa.getAccountType());
	   assertEquals("MAXISAVINGACCOUNT", msa.getAccountType());
    }

}

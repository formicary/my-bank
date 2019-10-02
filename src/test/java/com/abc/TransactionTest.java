package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;


import org.junit.BeforeClass;

public class TransactionTest {
	
	static Transaction testDeposit;
	static Transaction testWithdrawal;
	static final double delta = 1e-15;
	
	
	@BeforeClass
	public static void setUpBeforeClass() {
		testDeposit = new Transaction(1);
		testWithdrawal = new Transaction(-1);
	}
	
    @Test
    public void depositToString() {
    	String expStr = "  Deposit $1.00";
    	assertEquals(expStr, testDeposit.toString());
    }
    
    @Test
    public void withdrawalToString() {
    	String expStr = "  Withdrawal $1.00";
    	assertEquals(expStr, testWithdrawal.toString());
    }
}

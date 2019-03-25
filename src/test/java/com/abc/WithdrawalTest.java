package com.abc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WithdrawalTest {
	
private Withdrawal with;
	
	@Before
	public void setUp() throws Exception {
		with = new Withdrawal(TestConstants.DEPOSIT_AMOUNT);
	}

	@Test
	public void testToString() {
		assertEquals(TestConstants.WITHDRAWAL_TOSTRING_FIRST_HALF + with.getWithdrawalDate() + TestConstants.WITHDRAWAL_TOSTRING_SECOND_HALF, with.toString());
	}

	@After
    public void tearDown() throws Exception {
		with = null;
	}
}

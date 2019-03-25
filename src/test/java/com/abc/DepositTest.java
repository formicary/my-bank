package com.abc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DepositTest {
	
	private Deposit dep;
	
	@Before
	public void setUp() throws Exception {
		dep = new Deposit(TestConstants.DEPOSIT_AMOUNT);
	}

	@Test
	public void testToString () {
		assertEquals(TestConstants.DEPOSIT_TOSTRING_FIRST_HALF + dep.getDepositDate() + TestConstants.DEPOSIT_TOSTRING_SECOND_HALF, dep.toString());
	}

	
	@After
    public void tearDown() throws Exception {
		dep = null;
	}
}

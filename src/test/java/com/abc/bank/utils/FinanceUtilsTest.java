package com.abc.bank.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FinanceUtilsTest {

	@Test
	public void testCalcInterestEarned() {
		System.out.println(FinanceUtils.INSTANCE.calcInterestEarned(100, 0.1D,365));
		assertEquals("Dff Detected:",10D,FinanceUtils.INSTANCE.calcInterestEarned(100, 0.1D,365),1e-8);
		assertEquals("Dff Detected:",100*1.1*1.1-100,FinanceUtils.INSTANCE.calcInterestEarned(100, 0.1D,2*365),1e-8);
		}

}

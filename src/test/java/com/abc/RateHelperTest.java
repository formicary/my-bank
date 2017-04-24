package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.abc.implementation.RateHelper;
import com.abc.interfaces.IRateHelper;

public class RateHelperTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void getDailyRate()
	{
		IRateHelper rateHelper  = new RateHelper();
		assertEquals(0.01, rateHelper.getDailyRate(3.65), DOUBLE_DELTA);
	}
	
	@Test
	public void getEarnedInterest()
	{
		IRateHelper rateHelper = new RateHelper();
		assertEquals(0.0510100501, rateHelper.getEarnedInterest(0.01, 5), DOUBLE_DELTA);
	}
	
}

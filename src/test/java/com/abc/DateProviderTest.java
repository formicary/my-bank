package com.abc;

import org.junit.Test;
import com.abc.TestUtils;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

public class DateProviderTest 
{
	@Test
	public void dateDifference()
	{
		Calendar c = Calendar.getInstance();
		
		c.set(2019, 1, 1, 12, 0, 0);
		Date firstOfFirst = c.getTime();
		
		Date thirteenthOfFirst = DateProvider.getDateWithOffset(firstOfFirst, 12);
		
		assertEquals(DateProvider.getDifferenceDays(firstOfFirst, thirteenthOfFirst), 12.0, 0.0);
	}
}

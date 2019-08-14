package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MoneyTest {

	@Test
	public void testMoney(){
		Money m1 = new Money("100.000000000");
		Money m2 = new Money("-001.000000000");	
		Money m3 = new Money(m1.getAmount().add(m2.getAmount()));
		Money m4 = new Money(m1.getAmount().subtract(m2.getAmount()));
		
		assertEquals(new Money("-1").getAmount(), m2.getAmount());	
		assertEquals(new Money("99").getAmount(), m3.getAmount());
		assertEquals(new Money("101").getAmount(), m4.getAmount());
	}
}

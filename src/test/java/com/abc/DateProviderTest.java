package com.abc;
import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.assertTrue;

public class DateProviderTest {
	

		@Test
		public void NowTest(){
			
			
			
			DateProvider SingltonDate=new DateProvider();
			DateProvider singleton=SingltonDate.getInstance();	
			assertTrue(SingltonDate.getInstance().equals(singleton));
			
		}

		
}

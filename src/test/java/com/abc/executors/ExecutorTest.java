package com.abc.executors;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Ignore;
import org.junit.Test;

import com.abc.Bank;
import com.abc.Customer;
import com.abc.accounts.AccountType;
import com.abc.exceptions.InvalidAccountTypeException;
import com.abc.exceptions.InvalidCustomerName;


public class ExecutorTest {
	
	private Customer customer; 

	@Test
	@Ignore("Takes 1.5 seconds to run, it is not meant to run periodically.")
	public void executor() {
		
		DailyInterestRateExecutor executor = new DailyInterestRateExecutor(new AbstractInterestCalculator() {
			@Override
			public void execute() {
				try {
					customer = new Bank().createCustomer("John");
				} catch (InvalidCustomerName e) {
					e.printStackTrace();
				}
			}
		});
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int min = Calendar.getInstance().get(Calendar.MINUTE);
		int sec = Calendar.getInstance().get(Calendar.SECOND);
		if (sec == 60) min++;
		else sec++;
		executor.startExecutionAt(hour, min, sec);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		
		assertEquals("John", customer.getName());
	}
	
	@Test
	public void naiveExecutor() {
		try {
			customer = new Bank().createCustomer("John");
			InterestRateExecutor executor = new NaiveInterestRateExecutor(new AbstractInterestCalculator() {
				
				@Override
				public void execute() {
					try {
						customer.openAccount(AccountType.CHECKING);
					} catch (InvalidAccountTypeException e) {
						e.printStackTrace();
					}
				}
			});
			assertEquals(0, customer.getNumberOfAccounts());
			executor.start();
			assertEquals(1, customer.getNumberOfAccounts());
			executor.start();
			assertEquals(2, customer.getNumberOfAccounts());
		} catch (InvalidCustomerName e) {			
			e.printStackTrace();
		}
	}
	
}

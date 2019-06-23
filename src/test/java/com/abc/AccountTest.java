package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import com.abc.Account.*;

public class AccountTest {
	// Test the common behaviour of accounts
	@Test
	public void deposit()
	{
		Account a = new CheckingAccount();
		a.deposit(15.6);
		a.deposit(10.6);
		assertEquals(a.sumTransactions(), 15.6+10.6, TestUtils.DELTA_MONEY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void depositNegative()
	{
		Account a = new SavingsAccount();
		a.deposit(-15.6);
	}

	@Test
	public void withdraw()
	{
		Account a = new SavingsAccount();
		a.deposit(15.6);
		a.withdraw(5.7);
		assertEquals(a.sumTransactions(), 15.6-5.7, TestUtils.DELTA_MONEY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void withdrawNegative()
	{
		Account a = new SavingsAccount();
		a.deposit(15.6);
		a.withdraw(-5.7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void withdrawInsufficient()
	{
		Account a = new SavingsAccount();
		a.deposit(15.6);
		a.withdraw(15.7);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongTransactionOrder()
	{
		Calendar cal = Calendar.getInstance();
		Date dateFirst = cal.getTime();
		
		cal.setTimeInMillis(cal.getTimeInMillis() + 1000);
		Date dateSecond = cal.getTime();
		
		Account a = new SavingsAccount();
		a.deposit(100.0, dateSecond);
		a.deposit(100.0, dateFirst);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyTransaction()
	{
		Account a = new MaxiSavingsAccount();
		a.deposit(0);
	}
	// Test individual types
	@Test
	public void checkingAccountInterest()
	{
		Account checkingAccount = new CheckingAccount();
		checkingAccount.deposit(2000.0);
		assertEquals(checkingAccount.interestEarned(), 2000*0.001, TestUtils.DELTA_MONEY);
	}
	@Test
	public void savingsAccountInterest()
	{
		Account savingsAccount = new SavingsAccount();
		savingsAccount.deposit(999.0);
		assertEquals(savingsAccount.interestEarned(), 999*0.001, TestUtils.DELTA_MONEY);
		
		savingsAccount = new SavingsAccount();
		savingsAccount.deposit(1999);
		assertEquals(savingsAccount.interestEarned(), 1000*0.001 + 999*0.002, TestUtils.DELTA_MONEY);
		
	}
	@Test
	public void maxiSavingsAccountInterest()
	{
		Account maxiSavingsAccount = new MaxiSavingsAccount();
		maxiSavingsAccount.deposit(999.0);
		assertEquals(maxiSavingsAccount.interestEarned(), 999*0.02, TestUtils.DELTA_MONEY);
		
		maxiSavingsAccount = new MaxiSavingsAccount();
		maxiSavingsAccount.deposit(1999.0);
		assertEquals(maxiSavingsAccount.interestEarned(), 1000*0.02 + 999*0.05, TestUtils.DELTA_MONEY);
		
		maxiSavingsAccount = new MaxiSavingsAccount();
		maxiSavingsAccount.deposit(2400.0);
		assertEquals(maxiSavingsAccount.interestEarned(), 1000*0.02 + 1000*0.05 + 400*0.1, TestUtils.DELTA_MONEY);
	}
	
	@Test
	public void accountsOneDayInterest()
	{
		Account checkingAccount = new CheckingAccount();
		checkingAccount.deposit(156.2);
		checkingAccount.withdraw(10.2);
		checkingAccount.deposit(10000.2);
		
		Account savingsAccount = new SavingsAccount();
		savingsAccount.deposit(156.2);
		savingsAccount.withdraw(10.2);
		savingsAccount.deposit(10000.2);
		
		Account maxiAccount = new MaxiSavingsAccount();
		maxiAccount.deposit(156.2);
		maxiAccount.withdraw(10.2);
		maxiAccount.deposit(10000.2);
		
		assertEquals(checkingAccount.dailyInterestEarned(), 0.0, TestUtils.DELTA_MONEY);
		assertEquals(savingsAccount.dailyInterestEarned(), 0.0, TestUtils.DELTA_MONEY);
		assertEquals(maxiAccount.dailyInterestEarned(), 0.0, TestUtils.DELTA_MONEY);
	}
	@Test
	public void checkingMultipleDayInterest()
	{
		Calendar cal = Calendar.getInstance();
		Date first = cal.getTime();
		
		Date [] dates = new Date[3];
		for (int i = 0; i< 3; i++)
			
		{
			dates[i] = DateProvider.getDateWithOffset(first, i-10);
		}
		
		Account checkingAccount = new CheckingAccount();
		checkingAccount.deposit(1560.2, dates[0]);
		checkingAccount.deposit(78430.0, dates[0]);
		checkingAccount.withdraw(100.2, dates[1]);
		checkingAccount.deposit(100000.8, dates[2]);
		
		

		double totalInterest = 0.0;
		double currentSum = 1560.2+78430.0;
		
		double firstDayInterest = currentSum * (1+0.001/DateProvider.MEAN_YEAR) - currentSum;
		currentSum+=firstDayInterest;
		totalInterest+=firstDayInterest;
		currentSum-=100.2;
		
		double secondDayInterest = currentSum * (1+0.001/DateProvider.MEAN_YEAR) - currentSum;
		currentSum+=secondDayInterest;
		totalInterest+=secondDayInterest;
		currentSum+=100000.8;
		
		double restOfInterest = currentSum * Math.pow( 1 + 0.001/ DateProvider.MEAN_YEAR, 10-2) - currentSum;
		totalInterest+=restOfInterest;
		
		assertEquals(totalInterest, checkingAccount.dailyInterestEarned(), TestUtils.DELTA_MONEY);

	}
	@Test
	public void savingsMultipleDayInterest()
	{
		Calendar cal = Calendar.getInstance();
		Date first = cal.getTime();
		
		Date [] dates = new Date[3];
		for (int i = 0; i< 3; i++)
			
		{
			dates[i] = DateProvider.getDateWithOffset(first, i-10);
		}
		
		Account savingsAccount = new SavingsAccount();
		savingsAccount.deposit(15620.2, dates[0]);
		savingsAccount.deposit(300.0, dates[0]);
		savingsAccount.deposit(460.0, dates[1]);
		savingsAccount.deposit(1000.8, dates[2]);
		
		double sum = 15620.2 + 300.0;
		double interest = 0.0;
		double currentInterest = Account.getInterestPerDay(1000.0, 1, 0.001) + Account.getInterestPerDay(sum - 1000, 1, 0.002);
		
		sum += currentInterest;
		interest +=currentInterest;
		System.out.println("Current interest : " +currentInterest);
		sum+= 460.0;
		
		currentInterest = Account.getInterestPerDay(1000, 1, 0.001) + Account.getInterestPerDay(sum - 1000, 1, 0.002);
		interest += currentInterest;
		sum+= currentInterest;
		System.out.println("Current interest : " +currentInterest);
		sum+=1000.8;
		
		for (int i = 0; i<8; i++)
		{
			currentInterest = Account.getInterestPerDay(1000, 1, 0.001) + Account.getInterestPerDay(sum - 1000, 1, 0.002);
			sum += currentInterest;
			System.out.println("Current interest : " +currentInterest);
			interest +=currentInterest;

		}

		assertEquals(interest, savingsAccount.dailyInterestEarned(), TestUtils.DELTA_MONEY);
		
	}
	
	@Test
	public void maxiSavingsMultipleDayInterest()
	{
		Calendar cal = Calendar.getInstance();
		Date first = cal.getTime();
		
		Date [] dates = new Date[3];
		
		dates[0] = DateProvider.getDateWithOffset(first, -21);
		dates[1] = DateProvider.getDateWithOffset(first, -10);
		dates[2] = DateProvider.getDateWithOffset(first, -9);

		Account maxiAccount = new MaxiSavingsAccount();
		maxiAccount.deposit(599.2, dates[0]);
		maxiAccount.withdraw(10.0, dates[0]);
		maxiAccount.deposit(460.0, dates[1]);
		maxiAccount.deposit(10000.8, dates[2]);
		
		double sum = 599.2-10.0;
		double interest = 0.0;
		double currentInterest = 0.0;
		
		// from first date to second date there's 11 days (10 base rate, then 1 high rate, since last transaction is withdrawal)
		currentInterest = Account.getInterestPerDay(sum, 10, 0.001);
		sum+= currentInterest;
		interest+= currentInterest;
		
		currentInterest = Account.getInterestPerDay(sum, 1, 0.05);
		sum += currentInterest;
		interest += currentInterest;
		
		// then, from second date to last date, there's 1 day. Since last withdrawal happened 11 days ago, use high rate.
		sum+=460.0;
		currentInterest = Account.getInterestPerDay(sum, 1, 0.05);
		sum+=currentInterest;
		interest+=currentInterest;
		
		// finally, from last date to now, there's 9 days, all of them high rate, withdrawal was 12 days ago.
		sum+=10000.8;
		currentInterest = Account.getInterestPerDay(sum, 9, 0.05);
		sum+= currentInterest;
		interest+=currentInterest;
		
		assertEquals(maxiAccount.dailyInterestEarned(), interest, TestUtils.DELTA_MONEY);
	}
}

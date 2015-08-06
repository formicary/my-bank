package test.java.com.abc;

import static org.junit.Assert.*;
import main.java.com.abc.Account;

import org.junit.Ignore;
import org.junit.Test;

public class AccountTest {

	private final static double DELTA = 1e-15;
	
	@Test
	public void testInterestEarnedChecking() {
		Account checking = new Account(Account.CHECKING);
		checking.deposit(100);
		assertEquals(0.1, checking.interestEarned(), DELTA);
	}
	
	@Test
	public void testInterestEarnedSavingsLessThan1000() {
		Account savings = new Account(Account.SAVINGS);
		savings.deposit(999);
		assertEquals(0.999, savings.interestEarned(), DELTA);
	}

	@Test
	public void testInterestEarnedSavingsGreaterThan1000() {
		Account savings = new Account(Account.SAVINGS);
		savings.deposit(2100);
		assertEquals(3.2, savings.interestEarned(), DELTA);
	}
	
	@Test
	public void testInterestEarnedMaxiTwoPercent() {
		Account maxi = new Account(Account.MAXI_SAVINGS);
		maxi.deposit(1000);
		assertEquals(50.0, maxi.interestEarned(), DELTA);
	}
	
	@Test
	public void testInterestEarnedMaxiFivePercent() {
		Account maxi = new Account(Account.MAXI_SAVINGS);
		maxi.deposit(1500);
		assertEquals(75.0,  maxi.interestEarned(), DELTA);
	}
	
	
	@Test
	public void testInterestEarnedMaxiTenPercent() {
		Account maxi = new Account(Account.MAXI_SAVINGS);
		maxi.deposit(3000);
		assertEquals(150.0, maxi.interestEarned(), DELTA);
	}
	
	@Ignore
	@Test
	public void testMaxiSavingsRateLess() {
		
	}
}

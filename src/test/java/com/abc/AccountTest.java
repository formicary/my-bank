package test.java.com.abc;

import static org.junit.Assert.*;
import main.java.com.abc.Account;

import org.junit.Test;

public class AccountTest {

	private final static double VERY_SMALL_AMOUNT = 1e-15;
	
	@Test
	public void testInterestEarnedChecking() {
		Account checking = new Account(Account.CHECKING);
		checking.deposit(100);
		assertEquals(0.1, checking.interestEarned(), VERY_SMALL_AMOUNT);
	}
	
	@Test
	public void testInterestEarnedSavingsLessThan1000() {
		Account savings = new Account(Account.SAVINGS);
		savings.deposit(999);
		assertEquals(0.999, savings.interestEarned(), VERY_SMALL_AMOUNT);
	}

}

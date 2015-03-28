package com.abc.interests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import com.abc.Account;
import com.abc.Bank;

import static com.abc.accounts.AccountType.*;

import com.abc.exceptions.ExceededFundsException;
import com.abc.exceptions.InvalidAccountTypeException;
import com.abc.exceptions.InvalidCustomerName;

public class StrategyTest {

	@Test
	public void checkingStrategy() {
		InterestRateStrategy strategy = new CheckingStrategy();
		BigDecimal actual = strategy.computeInterestEarned(new BigDecimal("365000"));
		assertEquals(new BigDecimal("1.00"), actual.setScale(2, RoundingMode.HALF_UP));
	}
	
	@Test
	public void savingsStrategy() {
		InterestRateStrategy strategy = new SavingsStrategy();
		BigDecimal actual = strategy.computeInterestEarned(new BigDecimal("366000"));
		assertEquals(new BigDecimal("2.003"), actual.setScale(3, RoundingMode.HALF_UP));
		
		actual = strategy.computeInterestEarned(new BigDecimal("365"));
		assertEquals(new BigDecimal("0.001"), actual.setScale(3, RoundingMode.HALF_UP));
	}
	
	@Test
	public void formerMaxiSavingsStrategy() {
		InterestRateStrategy strategy = new DefaultMaxiSavingsStrategy();
		BigDecimal actual = strategy.computeInterestEarned(new BigDecimal("367000"));
		assertEquals(new BigDecimal("100.19"), actual.setScale(2, RoundingMode.HALF_UP));
		
		actual = strategy.computeInterestEarned(new BigDecimal("365"));
		assertEquals(new BigDecimal("0.02"), actual.setScale(2, RoundingMode.HALF_UP));
		
		actual = strategy.computeInterestEarned(new BigDecimal("1365"));
		assertEquals(new BigDecimal("0.10"), actual.setScale(2, RoundingMode.HALF_UP));
	}
	
	@Test
	public void currentMaxiSavingsStrategy() {
		Account account;
		try {
			account = new Bank().createCustomer("John").openAccount(MAXISAVINGS);
			InterestRateStrategy strategy = new WithdrawalBasedMaxiStrategy(account);
			BigDecimal actual = strategy.computeInterestEarned(new BigDecimal("365000"));
			
			assertEquals(new BigDecimal("50.00"), actual.setScale(2, RoundingMode.HALF_UP));			
			
			account.deposit(new BigDecimal("365000.00"));
			account.withdraw(new BigDecimal("100.00"));
			account.deposit(new BigDecimal("100"));
			actual = strategy.computeInterestEarned(account.getBalance());
			
			assertEquals(new BigDecimal("1.00"), actual.setScale(2, RoundingMode.HALF_UP));
		
		} catch (InvalidAccountTypeException | InvalidCustomerName | ExceededFundsException e) {
			e.printStackTrace();
		} 
	}
}

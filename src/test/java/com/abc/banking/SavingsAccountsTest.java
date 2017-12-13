package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.abc.banking.AbstractAccount;
import com.abc.banking.Account;
import com.abc.banking.SavingsAccount;
import com.abc.utils.TestingUtils;

public class SavingsAccountsTest {

	@Test
	public void typeNameShallBeCorrectlyAssigned() {
		
		Assert.assertEquals("Savings Account",	new SavingsAccount().getAccountTypeName());
    }
	
	@Test
	public void shallImplementAccountInterface() {
		
		Assert.assertTrue((Object)new SavingsAccount() instanceof Account);
		
	}
	
	@Test
	public void dailyInterestShallBeCalculatedCorrectlyBelowThreshold() throws Exception {
		Account account = new SavingsAccount();
		
		TestingUtils.changePrivateField(
			account.deposit(new BigDecimal("948.00")),
			"transactionDate",
			LocalDate.of(2017,12,1));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.03"), account.getDailyInterest(LocalDate.of(2017, 12, 11)));
	}

	@Test
	public void dailyInterestShallBeCalculatedCorrectlyAboveThreshold() throws Exception {
		Account account = new SavingsAccount();
		
		TestingUtils.changePrivateField(
			account.deposit(new BigDecimal("9533.75")),
			"transactionDate",
			LocalDate.of(2017,12,1));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.50"), account.getDailyInterest(LocalDate.of(2017, 12, 11)));
	}
	
	@Test
	public void accruedInterestShallAccrueCorrectInterest() throws Exception {
		AbstractAccount account = new SavingsAccount();
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("200000.00")),
				"transactionDate",
				LocalDate.of(2017, 12, 1));
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("14.00")),
				"transactionDate",
				LocalDate.of(2017, 12, 3));

		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("198000.00")),
				"transactionDate",
				LocalDate.of(2017, 12, 4));
		
		account.ensureInterestAccrued();
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("2046.87"), account.getBalanceAt(LocalDate.of(2017, 12, 4)));
	}
}

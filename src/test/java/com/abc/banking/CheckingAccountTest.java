package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.abc.banking.AbstractAccount;
import com.abc.banking.Account;
import com.abc.banking.CheckingAccount;
import com.abc.utils.TestingUtils;

public class CheckingAccountTest {

	@Test
    public void typeNameShallBeCorrectlyAssigned() {
		
		Assert.assertEquals("Checking Account", new CheckingAccount().getAccountTypeName());
    }

	@Test
	public void shallImplementAccountInterface() {
		
		Assert.assertTrue((Object)new CheckingAccount() instanceof Account);
	}
	
	@Test
	public void dailyInterestShallBeCalculatedCorrectly() throws Exception {
		Account account = new CheckingAccount();
		
		TestingUtils.changePrivateField(
			account.deposit(new BigDecimal("1948.00")),
			"transactionDate",
			LocalDate.of(2017,12,1));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.05"), account.getDailyInterest(LocalDate.of(2017, 12, 11)));
	}
	
	@Test
	public void accruedInterestShallAccrueCorrectInterest() throws Exception {
		AbstractAccount account = new CheckingAccount();
		
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
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("2030.50"), account.getBalanceAt(LocalDate.of(2017, 12, 4)));
	}

}

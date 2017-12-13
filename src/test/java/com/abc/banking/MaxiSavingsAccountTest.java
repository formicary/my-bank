package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.abc.banking.AbstractAccount;
import com.abc.banking.Account;
import com.abc.banking.MaxiSavingsAccount;
import com.abc.utils.TestingUtils;

public class MaxiSavingsAccountTest {

	@Test
    public void typeNameShallBeCorrectlyAssigned() {
		
		Assert.assertEquals("Maxi Savings Account", new MaxiSavingsAccount().getAccountTypeName());
    }

	@Test
	public void shallImplementAccountInterface() {
		
		Assert.assertTrue((Object)new MaxiSavingsAccount() instanceof Account);
	}
	
	@Test
	public void dailyInterestShallBeCalculatedCorrectlyBasicTest() throws Exception {
		Account account = new MaxiSavingsAccount();
		
		TestingUtils.changePrivateField(
			account.deposit(new BigDecimal("1948.00")),
			"transactionDate",
			LocalDate.of(2017, 12, 1));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.27"), account.getDailyInterest(LocalDate.of(2017, 12, 11)));
	}
	
	@Test
	public void ifWithdrawalWithin10DaysUseCorrectInterestRateCase1() throws Exception {
		Account account = new MaxiSavingsAccount();
		
		TestingUtils.changePrivateField(
			account.deposit(new BigDecimal("366000.00")),
			"transactionDate",
			LocalDate.of(2017, 11, 30));
		
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("1000.00")),
				"transactionDate",
				LocalDate.of(2017, 12, 2));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("10.00"), account.getDailyInterest(LocalDate.of(2017, 12, 11)));
	}

	@Test
	public void ifWithdrawalWithin10DaysUseCorrectInterestRateCase2() throws Exception {
		Account account = new MaxiSavingsAccount();
		
		TestingUtils.changePrivateField(
			account.deposit(new BigDecimal("366000.00")),
			"transactionDate",
			LocalDate.of(2017, 11, 30));
		
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("1000.00")),
				"transactionDate",
				LocalDate.of(2017, 12, 11));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("10.00"), account.getDailyInterest(LocalDate.of(2017, 12, 11)));
	}
	
	@Test
	public void accruedInterestShallAccrueCorrectInterest() throws Exception {
		AbstractAccount account = new MaxiSavingsAccount();
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("200000.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 1));

		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("1.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 2));
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("14.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 12));

		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("198000.00")),
				"transactionDate",
				LocalDate.of(2017, 11, 13));
		
		account.ensureInterestAccrued();
		
//		account.getTransactions().stream().sorted()
//		.forEach(t -> System.out.println(t.getTransactionDate()+"\t"+t.getAmount()+"\t"+account.getBalanceAt(t.getTransactionDate())));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("2122.67"), account.getBalanceAt(LocalDate.of(2017, 11, 13)));
	}

}

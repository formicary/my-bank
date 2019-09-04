package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Date;


public class AccountTest {
	
	private static final double DOUBLE_DELTA = 1e-15;
	
	//-------------DEPOSIT METHOD TESTS-------------------
	/*
	 * While a CheckingAccount is used to test for deposit, withdraw and transfer these
	 * methods are inherited from the abstract class Account and as such is applicable to all
	 *  subclasses (CheckingAccount, SavingsAccount, MaxiSavingsAccount)
	 */
	@Test
	public void TestDeposit() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Deposit(500.00);
		
		assertEquals(500.00, account.amount, DOUBLE_DELTA);
		assertEquals(1,account.transactions.size());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void TestDepositFailure() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Deposit(-1.00);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestDepositFailureZero() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Deposit(0.00);
	}
	
	//-------------WITHDRAW METHOD TESTS-------------------
	@Test
	public void TestWithdraw() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Withdraw(500.0);
		
		assertEquals(-500.00, account.amount, DOUBLE_DELTA);
		assertEquals(1,account.transactions.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestWithdrawFailure() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Withdraw(-1.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestWithdrawFailureZero() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Withdraw(0.0);
	}
	
	//--------------TRANSFER METHOD TESTS---------------------
	@Test
	public void TestTransfer() {
		Customer john = new Customer("John");
		
		CheckingAccount sender = new CheckingAccount(john);
		CheckingAccount recieveer = new CheckingAccount(john);
		
		sender.Transfer(500.0, recieveer);
		
		assertEquals(-500.00, sender.amount, DOUBLE_DELTA);
		assertEquals(500.00, recieveer.amount, DOUBLE_DELTA);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestTransferDifferentOwner() {
		Customer john = new Customer("John");
		Customer bill = new Customer("Bill");
		
		CheckingAccount sender = new CheckingAccount(john);
		CheckingAccount recieveer = new CheckingAccount(bill);
		
		
		sender.Transfer(500.0, recieveer);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestTransferInvalidAmount() {
		Customer john = new Customer("John");
		
		CheckingAccount sender = new CheckingAccount(john);
		CheckingAccount recieveer = new CheckingAccount(john);
		
		sender.Transfer(0.0, recieveer);
	}
	
	//---------------DEPOSIT/TRANSFER TESTS-----------------------
	@Test
	public void TestDepositAndWithdraw() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Deposit(500.0);
		account.Withdraw(100.00);
		account.Deposit(1700.0);
		
		assertEquals(2100.0, account.amount, DOUBLE_DELTA);
		assertEquals(3,account.transactions.size());
	}
	
	//--------------INTEREST METHOD TESTS---------------------
	@Test
	public void TestCheckingAccountDailyInterest() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Deposit(10000.0);
		account.interestEarned();
		
		assertEquals(10000.0273, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestSavingAccountHighRateDailyInterest() {
		SavingAccount account = new SavingAccount(new Customer("John"));
		account.Deposit(10000.0);
		account.interestEarned();
		
		assertEquals(10000.0547, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestSavingAccountLowRateDailyInterest() {
		SavingAccount account = new SavingAccount(new Customer("John"));
		account.Deposit(1000.0);
		account.interestEarned();
		
		assertEquals(1000.0027, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestMaxiSavingAccountRecentWithdrawl() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		account.Deposit(10001.0);
		account.Withdraw(1.0);
		account.interestEarned();
		
		assertEquals(10000.0273, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestMaxiSavingAccountNoRecentWithdrawl() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		account.Deposit(10001.0);
		account.Withdraw(1.0);
		
		Date elevenDayOld = new Date(System.currentTimeMillis()-24*60*60*1000*11); //get date 10 days ago
		
		Transaction t = new Transaction(-1.0);
		t.getTransactionDate().setTime(elevenDayOld.getTime());
		
		account.transactions.get(1).getTransactionDate().setTime(elevenDayOld.getTime());
		
		
		account.interestEarned();
		
		assertEquals(10001.3368, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestMaxiSavingAccountNoWithdrawl() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		account.Deposit(10000.0);
		account.interestEarned();
		
		assertEquals(10001.3368, account.amount, DOUBLE_DELTA);
	}
	/*
	@Test
	public void TestMaxiSavingAccountLowRateDailyInterest() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		account.Deposit(1000.0);
		account.interestEarned();
		
		//1000 + 1000*0.1/100/365.25 = 1000.00
		assertEquals(1000.0547, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestMaxiSavingAccountMedRateDailyInterest() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		account.Deposit(2000.0);
		account.interestEarned();
		
		//1000 + 1000*0.1/100/365.25 = 1000.00
		assertEquals(2000.2737, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestMaxiSavingAccountHighRateDailyInterest() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		account.Deposit(10000.0);
		account.interestEarned();
		
		//1000 + 1000*0.1/100/365.25 = 1000.00
		assertEquals(10002.7378, account.amount, DOUBLE_DELTA);
	}
	*/
	
	@Test
	public void TestCheckingAccountTwoDayInterest() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		account.Deposit(10000.0);
		account.interestEarned();
		account.interestEarned();
		
		assertEquals(10000.0546, account.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TestSavingAccountHighRateTwoDayInterest() {
		SavingAccount account = new SavingAccount(new Customer("John"));
		account.Deposit(10000.0);
		account.interestEarned();
		account.interestEarned();
		
		assertEquals(10000.1094, account.amount, DOUBLE_DELTA);
	}
	
	/*
	@Test
	public void TestMaxiSavingAccountHighRateTwoDayInterest() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		account.Deposit(10000.0);
		account.interestEarned();
		account.interestEarned();
		
		assertEquals(10005.4764, account.amount, DOUBLE_DELTA);
	}
	*/


	//--------------GET ACCOUNT TYPE METHOD TESTS---------------------
	@Test
	public void TestCheckingAccountType() {
		CheckingAccount account = new CheckingAccount(new Customer("John"));
		assertEquals( "Checking Account", account.getAccountType());
	}
	
	@Test
	public void TestSavingAccountType() {
		SavingAccount account = new SavingAccount(new Customer("John"));
		assertEquals("Saving Account", account.getAccountType());
	}
	
	@Test
	public void TestMaxiSavingAccountType() {
		MaxiSavingAccount account = new MaxiSavingAccount(new Customer("John"));
		assertEquals("Maxi-Saving Account", account.getAccountType());
	}
	
}

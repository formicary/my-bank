package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
  
  private static final double DOUBLE_DELTA = 1e-15;
  
  @Test
  public void accountType() {
    Account account = new Account(Account.Type.MAXI_SAVINGS);
    assertEquals(Account.Type.MAXI_SAVINGS, account.getAccountType());
  }
  
  @Test
  public void transactions() {
    Account account = new Account(Account.Type.CHECKING);
    account.transact(200);
    account.transact(-100);
    assertEquals(account.sumTransactions(), 100, DOUBLE_DELTA);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void transactionZero() {
    Account account = new Account(Account.Type.SAVINGS);
    account.transact(0);
  }
  
  @Test
  public void sumTransactionsNegative() {
    Account account = new Account(Account.Type.CHECKING);
    account.transact(100);
    account.transact(-300);
    assertEquals(-200, account.sumTransactions(), DOUBLE_DELTA);
  }
  
  @Test
  public void sumTransactionsPositive() {
    Account account = new Account(Account.Type.SAVINGS);
    account.transact(100);
    account.transact(300);
    assertEquals(400, account.sumTransactions(), DOUBLE_DELTA);
  }
  
  /*
  @Test
  public void checkingInterestEarned() {
    Account account = new Account(Account.Type.CHECKING);
    account.transact(500);
    assertEquals(0.5, account.interestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void savingsInterestEarnedLow() {
    Account account = new Account(Account.Type.SAVINGS);
    account.transact(500);
    assertEquals(0.5, account.interestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void savingsInterestEarnedHigh() {
    Account account = new Account(Account.Type.SAVINGS);
    account.transact(1500);
    assertEquals(2.0, account.interestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void maxiSavingsInterestEarnedStandard() {
    Account account = new Account(Account.Type.MAXI_SAVINGS);
    account.transact(500);
    account.transact(-200);
    assertEquals(0.3, account.interestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void maxiSavingsInterestEarnedNoWithdrawals() {
    Account account = new Account(Account.Type.MAXI_SAVINGS);
    account.transact(500);
    account.transact(200);
    assertEquals(35.0, account.interestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void maxiSavingsInterestEarnedLow() {
    Account account = new Account(Account.Type.MAXI_SAVINGS);
    account.transact(500);
    assertEquals(10.0, account.interestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void maxiSavingsInterestEarnedMed() {
    Account account = new Account(Account.Type.MAXI_SAVINGS);
    account.transact(1500);
    assertEquals(45, account.interestEarned(), DOUBLE_DELTA);
  }
  
  @Test
  public void maxiSavingsInterestEarnedHigh() {
    Account account = new Account(Account.Type.MAXI_SAVINGS);
    account.transact(2500);
    assertEquals(120.0, account.interestEarned(), DOUBLE_DELTA);
  }
  */

}

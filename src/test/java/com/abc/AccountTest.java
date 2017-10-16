package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccountTest {

  @Test(expected = IllegalArgumentException.class) 
  public void testDepositNegativeAmount() {
    Account checkingAccount = new CheckingAccount();
    checkingAccount.deposit(-1.0);
  }
  
  @Test(expected = IllegalArgumentException.class) 
  public void testWithdrawNegativeAmount() {
    Account checkingAccount = new CheckingAccount();
    checkingAccount.withdraw(-1.0);
  }

  @Test
  public void testInterestEarnedSavings() {
    Account savingsAccount = new SavingsAccount();
    savingsAccount.deposit(100.0);
    assertEquals(savingsAccount.interestEarned(), 0.1, 0.001);
  }

  @Test
  public void testSumTransactions() {
    Account checkingAccount = new CheckingAccount();
    // add the transactions manually (ie no deposit/withdraw), so that we are 
    // testing only sumTransactions (and Transaction)
    checkingAccount.addTransaction(new Transaction(200.0));
    checkingAccount.addTransaction(new Transaction(0.0));
    checkingAccount.addTransaction(new Transaction(-15.0));
    checkingAccount.addTransaction(new Transaction(-15.0));
    assertEquals(checkingAccount.sumTransactions(), 170.0, 0.01);
  }

  @Test
  public void testSumTransactionsGoesNegative() {
    Account checkingAccount = new CheckingAccount();
    // add the transactions manually (ie no deposit/withdraw), so that we are 
    // testing only sumTransactions (and Transaction)
    checkingAccount.addTransaction(new Transaction(100.0));
    checkingAccount.addTransaction(new Transaction(-150.0));
    assertEquals(checkingAccount.sumTransactions(), -50.0, 0.01);
  }

}

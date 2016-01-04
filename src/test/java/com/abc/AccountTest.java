package com.abc;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {
  private static final double DELTA = 1e-15;
  private final Customer c = new Customer("Henry");
  private final Account checkingAcc = new Account(c, Account.CHECKING);
  private final Account savingsAcc = new Account(c, Account.SAVINGS);
  private final Account maxiSavingsAcc = new Account(c, Account.MAXI_SAVINGS);
  
  @Before
  public void initialize() {
    c.openAccount(checkingAcc);
    c.openAccount(savingsAcc);
    c.openAccount(maxiSavingsAcc);
  }

  @Test
  public void sumTransactionWorks() {
    assertEquals(0,checkingAcc.sumTransactions(),DELTA);
    checkingAcc.deposit(500);
    assertEquals(500,checkingAcc.sumTransactions(),DELTA);
    checkingAcc.withdraw(1000);
    assertEquals(-500,checkingAcc.sumTransactions(),DELTA);
  }
  
  @Test
  public void canDeposit() {
    checkingAcc.deposit(100);
    assertEquals(checkingAcc.sumTransactions(), 100, DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void canNotDepositNegativeAmount() {
    checkingAcc.deposit(-100);
  }

  @Test
  public void canWithdraw() {
    checkingAcc.withdraw(100);
    assertEquals(checkingAcc.sumTransactions(), -100, DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void canNotWithdrawaNegativeAmount() {
    checkingAcc.withdraw(-100);
  }
  
  @Test
  public void returnsCorrectOwner() {
    assertEquals(checkingAcc.getOwner(),c);
  }
    
  @Test
  public void interestCorrectlyCalculatedForChecking() {
    assertEquals(checkingAcc.interestEarned(),0,DELTA);
    checkingAcc.deposit(500);
    assertEquals(checkingAcc.interestEarned(),500*0.001,DELTA);
    checkingAcc.deposit(500);
    assertEquals(checkingAcc.interestEarned(),1000*0.001,DELTA);
  }
  
  @Test
  public void interestCorrectlyCalculatedForSavings() {
    assertEquals(savingsAcc.interestEarned(),0,DELTA);
    savingsAcc.deposit(1000);
    assertEquals(savingsAcc.interestEarned(),1000*0.001,DELTA);
    savingsAcc.deposit(500);
    assertEquals(savingsAcc.interestEarned(),1000*0.001+500*0.002,DELTA);
  }
  
  @Test
  public void interestCorrectlyCalculatedForMaxi_Savings() {
    assertEquals(maxiSavingsAcc.interestEarned(),0,DELTA);
    maxiSavingsAcc.deposit(1000);
    assertEquals(maxiSavingsAcc.interestEarned(),1000*0.05,DELTA);
    maxiSavingsAcc.withdraw(500);
    assertEquals(maxiSavingsAcc.interestEarned(),500*0.001,DELTA);
  }
   
  @Test
  public void canTransferWithinAccounts() {
    assertEquals(checkingAcc.sumTransactions(),0,DELTA);
    assertEquals(savingsAcc.sumTransactions(),0,DELTA);
    checkingAcc.transfer(500, savingsAcc);
    assertEquals(checkingAcc.sumTransactions(),-500,DELTA);
    assertEquals(savingsAcc.sumTransactions(),500,DELTA);
    savingsAcc.transfer(500,checkingAcc);
    assertEquals(checkingAcc.sumTransactions(),0,DELTA);
    assertEquals(savingsAcc.sumTransactions(),0,DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void canNotTransferNegativeAmount() {
    checkingAcc.transfer(-500, savingsAcc);   
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void canNotTransferToADifferentCustomer() {
    Customer c2 = new Customer("Test");
    Account a = new Account(c2,Account.CHECKING);
    a.transfer(500, checkingAcc);   
  }
  
  @Test
  public void withdrawalMadeWithinNdaysWorksWithNoTransactions() {
    assertFalse(checkingAcc.withdrawalInThePastNDays(1));
    checkingAcc.deposit(5);
    assertFalse(checkingAcc.withdrawalInThePastNDays(1));
    checkingAcc.withdraw(5);
    assertTrue(checkingAcc.withdrawalInThePastNDays(1));
  }
}

package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CommonOperationsHelperTest {

  private CommonOperationsHelper helper;

  @Before
  public void setUp() {
    helper = new CommonOperationsHelper();
  }

  @Test
  public void testDeposit() {
    Account savingsAccount = new SavingAccount();
    helper.deposit(100.0, savingsAccount);
    assertEquals("100.0", String.valueOf(savingsAccount.getBalance()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDepositAmountLessThanZero() {
    Account savingsAccount = new SavingAccount();
    helper.deposit(-100.0, savingsAccount);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWithdrawAmountLessThanZero() {
    Account checkingAccount = new CheckingAccount();
    helper.withdraw(-100.0, checkingAccount);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWithdrawWithInsufficientFunds() {
    Account checkingAccount = new CheckingAccount();
    helper.deposit(100.0, checkingAccount);
    helper.withdraw(300.0, checkingAccount);
  }

  public void testWithdraw() {
    Account checkingAccount = new CheckingAccount();
    helper.deposit(1000.0, checkingAccount);
    helper.withdraw(300.0, checkingAccount);
    assertEquals("700.0", String.valueOf(checkingAccount.getBalance()));
  }

  @Test
  public void testTransferAmount() {
    Account checkingAccount = new CheckingAccount();
    Account savingsAccount = new SavingAccount();
    checkingAccount.deposit(200.0);
    savingsAccount.deposit(4000.0);
    savingsAccount.transferAmount(300.0, checkingAccount);
    assertEquals("500.0", String.valueOf(checkingAccount.getBalance()));
    assertEquals("3700.0", String.valueOf(savingsAccount.getBalance()));
  }

}

package com.abc;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class SavingAccountTest {

  private Account account;
  @Before
  public void setUp() throws Exception {
    account = new SavingAccount();
  }

  @Test
  public void testCalculateInterestForAmountLessThan1000() {
    account.deposit(900.0);
    assertEquals("0.9", String.valueOf(account.calculateInterest()));
  }

  @Test
  public void testCalculateInterestForAmountGreaterThan1000() {
    account.deposit(3000.0);
    assertEquals("5.0", String.valueOf(account.calculateInterest()));
  }

  @Test
  public void testGetAccountType() {
    assertEquals("Saving Account", account.getAccountType());
  }

  @Test
  public void testGetCommonOperation() {
    assertNotNull(account.getCommonOperation());
  }
}
package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import org.junit.Before;
import org.junit.Test;

public class CheckingAccountTest {

  private Account account;

  @Before
  public void setUp() {
    account = new CheckingAccount();
  }

  @Test
  public void testCalculateInterest() {
    account.deposit(30000.0);
    assertEquals("30.0", String.valueOf(account.calculateInterest()));
  }

  @Test
  public void testGetAccountType() {
    assertEquals("Checking Account", account.getAccountType());
  }

  @Test
  public void testGetCommonOperation() {
    assertNotNull(account.getCommonOperation());
  }
}
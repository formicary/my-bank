package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MaxiSavingsAccountTest {

  @InjectMocks
  private MaxiSavingsAccount account;

  @Before
  public void setUp() throws Exception {
    account = new MaxiSavingsAccount();
  }

  @Test
  public void testGetAccountType() {
    assertEquals("Maxi-Savings Account", account.getAccountType());
  }

/*  @Test
  public void testCalculateInterest() {
    Transaction trans = new Transaction(500.0, "withdraw", "Maxi-Savings Account");
    CommonOperationsHelper helper = mock(CommonOperationsHelper.class);
    account = new MaxiSavingsAccount(helper);
    ArrayList<Transaction> transList = new ArrayList<>();
    transList.add(trans);
    when(helper.getLast10DaysWithdrawTransactions(any())).thenReturn(transList);
    account.deposit(3000.0);
    assertEquals("150.0", String.valueOf(account.calculateInterest()));

  }*/

  @Test
  public void testGetCommonOperation() {
    assertNotNull(account.getCommonOperation());
  }
}
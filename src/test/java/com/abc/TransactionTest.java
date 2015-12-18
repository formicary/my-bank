package com.abc;

import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

  private static final double DOUBLE_DELTA = 1e-15;

  @Test
  public void transaction() {
    Transaction t = new Transaction(5.0);
    assertTrue(t instanceof Transaction);
  }

  @Test
  public void transactionAmount() {
    Transaction t = new Transaction(5.0);
    assertEquals(5.0, t.getAmount(), DOUBLE_DELTA);
  }

  @Test
  public void transactionDate() {
    Transaction t = new Transaction(2.0);
    assertEquals(t.getDate().compareTo(Calendar.getInstance().getTime()), 0);
  }

}

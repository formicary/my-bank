package com.abc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TransactionTest {
  @Test
  public void transaction() {
    Transaction t = new Transaction(5);
    assertTrue(t instanceof Transaction);
  }
}

package com.abc.transaction;

import com.abc.DateProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateProvider.class)
public class TransactionTest {

  private Transaction transaction;

  @Mock
  private DateProvider dateProvider;

  @Test
  public void transactionReturnsInitialisedAmount() {
    final BigDecimal amount = BigDecimal.valueOf(5);
    transaction = new Transaction(amount);
    assertEquals(amount, transaction.getAmount());
  }

  @Test
  public void isWithdrawalReturnsFalseWhenAmountIsPositive() {
    final BigDecimal amount = BigDecimal.valueOf(5);
    transaction = new Transaction(amount);
    assertFalse(transaction.isWithdrawal());
  }

  @Test
  public void isWithdrawalReturnsTrueWhenAmountIsNegative() {
    final BigDecimal amount = BigDecimal.valueOf(-5);
    transaction = new Transaction(amount);
    assertTrue(transaction.isWithdrawal());
  }

  @Test
  public void getDateReturnsInitialisedDate() {
    mockStatic(DateProvider.class);
    when(DateProvider.getInstance()).thenReturn(dateProvider);
    final LocalDateTime now = LocalDateTime.now();
    when(dateProvider.now()).thenReturn(now);
    transaction = new Transaction(BigDecimal.ONE);
    assertEquals(now, transaction.getDate());
  }
}

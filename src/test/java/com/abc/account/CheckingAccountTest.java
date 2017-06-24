package com.abc.account;

import com.abc.DateProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateProvider.class)
public class CheckingAccountTest {

  private CheckingAccount account = new CheckingAccount();

  @Mock private DateProvider dateProvider;

  private final BigDecimal ZERO = BigDecimal.ZERO;
  private final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

  @Before
  public void setup() {
    mockStatic(DateProvider.class);
    when(DateProvider.getInstance()).thenReturn(dateProvider);
  }

  @Test
  public void getTransactionsSumWithNoTransactionsReturnsZero() {
    assertEquals(BigDecimal.ZERO, account.getTransactionsSum());
  }

  @Test
  public void getTransactionsSumReturnsDepositValue() {
    final BigDecimal amount = ONE_HUNDRED;
    account.deposit(amount);
    assertEquals(amount, account.getTransactionsSum());
  }

  @Test
  public void getTransactionsSumReturnsWithdrawnValue() {
    final BigDecimal amount = ONE_HUNDRED;
    account.withdraw(amount);
    assertEquals(amount.negate(), account.getTransactionsSum());
  }

  @Test(expected = IllegalArgumentException.class)
  public void depositNegativeValueThrowsException() {
    account.deposit(BigDecimal.valueOf(-100.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdrawNegativeValueThrowsException() {
    account.withdraw(BigDecimal.valueOf(-100.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void depositZeroValueThrowsException() {
    account.deposit(BigDecimal.ZERO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdrawZeroValueThrowsException() {
    account.withdraw(BigDecimal.ZERO);
  }

  @Test
  public void totalInterestPaidAfterOneYear() {
    when(dateProvider.now()).thenReturn(LocalDateTime.now().minusYears(1));
    account = new CheckingAccount();
    when(dateProvider.now()).thenReturn(LocalDateTime.now());
    account.deposit(ONE_HUNDRED);
    final BigDecimal amount = account.getInterestEarned();
    assertEquals(BigDecimal.valueOf(0.1).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
  }

  // Ignoring this test due to the length it takes to complete
  @Ignore
  @Test
  public void totalInterestPaidAfterTenYears() {
    when(dateProvider.now()).thenReturn(LocalDateTime.now().minusYears(10));
    account = new CheckingAccount();
    when(dateProvider.now()).thenReturn(LocalDateTime.now());
    account.deposit(ONE_HUNDRED);
    final BigDecimal amount = account.getInterestEarned();
    assertEquals(BigDecimal.valueOf(0.99).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
  }

  @Test
  public void getName() {
    assertEquals("Checking Account", account.getName());
  }

  @Test
  public void getStatementWithDeposit() {
    account.deposit(ONE_HUNDRED);
    assertEquals(
        "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00", account.getStatement());
  }

  @Test
  public void getStatementWithWithdrawal() {
    account.withdraw(ONE_HUNDRED);
    assertEquals(
        "Checking Account\n" + "  withdrawal $100.00\n" + "Total $-100.00", account.getStatement());
  }

  @Test
  public void getStatementWithWithNoTransactions() {
    assertEquals("Checking Account\n" + "Total $0.00", account.getStatement());
  }

  @Test
  public void getStatementWithWithDepositAndWithdrawal() {
    account.deposit(ONE_HUNDRED);
    account.withdraw(ONE_HUNDRED);
    assertEquals(
        "Checking Account\n" + "  deposit $100.00\n" + "  withdrawal $100.00\n" + "Total $0.00",
        account.getStatement());
  }

  @Test
  public void transferToAccountDecreasesTheFromAccountAmount() {
    final Account to = new CheckingAccount();
    final BigDecimal amount = ONE_HUNDRED;
    account.deposit(amount);
    account.transferTo(to, amount);
    assertEquals(ZERO, account.getTransactionsSum());
  }

  @Test
  public void transferToAccountIncreasesTheFromToAmount() {
    final Account to = new CheckingAccount();
    final BigDecimal amount = ONE_HUNDRED;
    account.deposit(amount);
    account.transferTo(to, amount);
    assertEquals(amount, to.getTransactionsSum());
  }
}

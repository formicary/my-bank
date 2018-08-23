package com.abc.account;

import com.abc.DateProvider;
import org.junit.Before;
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
public class MaxiSavingsAccountTest {

    private MaxiSavingsAccount account = new MaxiSavingsAccount();

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
        final Account account = new MaxiSavingsAccount();
        assertEquals(ZERO, account.getTransactionsSum());
    }

    @Test
    public void getTransactionsSumWithCheckingAccountReturnsDepositValue() {
        final Account account = new MaxiSavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        account.deposit(amount);
        assertEquals(amount, account.getTransactionsSum());
    }

    @Test
    public void getTransactionsSumReturnsWithdrawnValue() {
        final Account account = new MaxiSavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        account.withdraw(amount);
        assertEquals(amount.negate(), account.getTransactionsSum());
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeValueThrowsException() {
        final Account account = new MaxiSavingsAccount();
        account.deposit(ONE_HUNDRED.negate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeValueThrowsException() {
        final Account account = new MaxiSavingsAccount();
        account.withdraw(ONE_HUNDRED.negate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositZeroValueThrowsException() {
        final Account account = new MaxiSavingsAccount();
        account.deposit(ZERO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroValueThrowsException() {
        final Account account = new MaxiSavingsAccount();
        account.withdraw(ZERO);
    }

    @Test
    public void totalInterestPaidAfterOneYearNoWithdrawals() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusYears(1));
        account = new MaxiSavingsAccount();
        account.deposit(ONE_HUNDRED);
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(5.13).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    @Test
    public void totalInterestPaidAfterTenDaysNoWithdrawal() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusDays(10));
        account = new MaxiSavingsAccount();
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        account.deposit(BigDecimal.valueOf(1000));
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(1.37).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    @Test
    public void totalInterestPaidAfterTenDaysWithWithdrawal() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusDays(10));
        account = new MaxiSavingsAccount();
        account.deposit(BigDecimal.valueOf(1000));
        account.withdraw(BigDecimal.ONE);
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(0.03).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    @Test
    public void totalInterestPaidAfterTwentyDaysWithWithdrawalOnFirstDay() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusDays(20));
        account = new MaxiSavingsAccount();
        account.deposit(BigDecimal.valueOf(1000));
        account.withdraw(BigDecimal.ONE);
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(1.26).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    @Test
    public void totalInterestPaidAfterTwentyDaysWithWithdrawalOnTenthDay() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusDays(20));
        account = new MaxiSavingsAccount();
        account.deposit(BigDecimal.valueOf(1000));
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusDays(10));
        account.withdraw(BigDecimal.ONE);
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(1.26).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    @Test
    public void getName() {
        assertEquals("Maxi Savings Account", new MaxiSavingsAccount().getName());
    }

    @Test
    public void getStatementWithDeposit() {
        final Account account = new MaxiSavingsAccount();
        account.deposit(ONE_HUNDRED);
        assertEquals(
                "Maxi Savings Account\n" + "  deposit $100.00\n" + "Total $100.00", account.getStatement());
    }

    @Test
    public void getStatementWithWithdrawal() {
        final Account account = new MaxiSavingsAccount();
        account.withdraw(ONE_HUNDRED);
        assertEquals(
                "Maxi Savings Account\n" + "  withdrawal $100.00\n" + "Total $-100.00",
                account.getStatement());
    }

    @Test
    public void getStatementWithWithNoTransactions() {
        final Account account = new MaxiSavingsAccount();
        assertEquals("Maxi Savings Account\n" + "Total $0.00", account.getStatement());
    }

    @Test
    public void getStatementWithWithDepositAndWithdrawal() {
        final Account account = new MaxiSavingsAccount();
        account.deposit(ONE_HUNDRED);
        account.withdraw(ONE_HUNDRED);
        assertEquals(
                "Maxi Savings Account\n" + "  deposit $100.00\n" + "  withdrawal $100.00\n" + "Total $0.00",
                account.getStatement());
    }

    @Test
    public void transferToAccountDecreasesTheFromAccountAmount() {
        final Account from = new MaxiSavingsAccount();
        final Account to = new MaxiSavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        from.deposit(amount);
        from.transferTo(to, amount);
        assertEquals(ZERO, from.getTransactionsSum());
    }

    @Test
    public void transferToAccountIncreasesTheFromToAmount() {
        final Account from = new MaxiSavingsAccount();
        final Account to = new MaxiSavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        from.deposit(amount);
        from.transferTo(to, amount);
        assertEquals(amount, to.getTransactionsSum());
    }
}
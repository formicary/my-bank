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
public class SavingsAccountTest {

    private SavingsAccount account = new SavingsAccount();

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
        final Account account = new SavingsAccount();
        assertEquals(ZERO, account.getTransactionsSum());
    }

    @Test
    public void getTransactionsSumWithCheckingAccountReturnsDepositValue() {
        final Account account = new SavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        account.deposit(amount);
        assertEquals(amount, account.getTransactionsSum());
    }

    @Test
    public void getTransactionsSumReturnsWithdrawnValue() {
        final Account account = new SavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        account.withdraw(amount);
        assertEquals(amount.negate(), account.getTransactionsSum());
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeValueThrowsException() {
        final Account account = new SavingsAccount();
        account.deposit(ONE_HUNDRED.negate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeValueThrowsException() {
        final Account account = new SavingsAccount();
        account.withdraw(ONE_HUNDRED.negate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositZeroValueThrowsException() {
        final Account account = new SavingsAccount();
        account.deposit(ZERO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroValueThrowsException() {
        final Account account = new SavingsAccount();
        account.withdraw(ZERO);
    }

    @Test
    public void totalInterestPaidUnderOneThousandAfterOneYear() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusYears(1));
        account = new SavingsAccount();
        account.deposit(ONE_HUNDRED);
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(0.1).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    // Ignoring this test due to the length it takes to complete
    @Ignore
    @Test
    public void totalInterestPaidUnderOneThousandAfterTenYears() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusYears(10));
        account = new SavingsAccount();
        account.deposit(ONE_HUNDRED);
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(0.99).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    @Test
    public void totalInterestPaidOverOneThousandAfterOneYear() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusYears(1));
        account = new SavingsAccount();
        account.deposit(BigDecimal.valueOf(1500));
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(2.01).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    // Ignoring this test due to the length it takes to complete
    @Ignore
    @Test
    public void totalInterestPaidOverOneThousandAfterTenYears() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusYears(10));
        account = new SavingsAccount();
        account.deposit(BigDecimal.valueOf(1500));
        when(dateProvider.now()).thenReturn(LocalDateTime.now());
        final BigDecimal amount = account.getInterestEarned();
        assertEquals(BigDecimal.valueOf(20.26).setScale(2, BigDecimal.ROUND_HALF_UP), amount);
    }

    @Test
    public void getName() {
        assertEquals("Savings Account", new SavingsAccount().getName());
    }

    @Test
    public void getStatementWithDeposit() {
        final Account account = new SavingsAccount();
        account.deposit(ONE_HUNDRED);
        assertEquals(
                "Savings Account\n" + "  deposit $100.00\n" + "Total $100.00", account.getStatement());
    }

    @Test
    public void getStatementWithWithdrawal() {
        final Account account = new SavingsAccount();
        account.withdraw(ONE_HUNDRED);
        assertEquals(
                "Savings Account\n" + "  withdrawal $100.00\n" + "Total $-100.00", account.getStatement());
    }

    @Test
    public void getStatementWithWithNoTransactions() {
        final Account account = new SavingsAccount();
        assertEquals("Savings Account\n" + "Total $0.00", account.getStatement());
    }

    @Test
    public void getStatementWithWithDepositAndWithdrawal() {
        final Account account = new SavingsAccount();
        account.deposit(ONE_HUNDRED);
        account.withdraw(ONE_HUNDRED);
        assertEquals(
                "Savings Account\n" + "  deposit $100.00\n" + "  withdrawal $100.00\n" + "Total $0.00",
                account.getStatement());
    }

    @Test
    public void transferToAccountDecreasesTheFromAccountAmount() {
        final Account from = new SavingsAccount();
        final Account to = new SavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        from.deposit(amount);
        from.transferTo(to, amount);
        assertEquals(ZERO, from.getTransactionsSum());
    }

    @Test
    public void transferToAccountIncreasesTheFromToAmount() {
        final Account from = new SavingsAccount();
        final Account to = new SavingsAccount();
        final BigDecimal amount = ONE_HUNDRED;
        from.deposit(amount);
        from.transferTo(to, amount);
        assertEquals(amount, to.getTransactionsSum());
    }
}
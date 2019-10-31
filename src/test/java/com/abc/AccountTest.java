import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void testDepositAmount() {
        Account account = new Account(AccountType.CHECKING,"Test account");
        account.deposit(400);
        assertEquals(400.00, account.getTransactions().get(0).getAmount(), 1e-15);

    }
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDepositAmountException() {
        Account account0 = new Account(AccountType.CHECKING,"Test account0");
        account0.deposit(-400);
    }

    @Test
    public void testDepositCreatesTransactionInstance() {
        Account account1 = new Account(AccountType.CHECKING,"Test account1");
        account1.deposit(400);

        assertNotNull((account1.getTransactions().get(0)));

    }

    @Test
    public void testWithdrawalAmount() {
        Account account2 = new Account(AccountType.SAVINGS,"Test account2");
        account2.deposit(400);
        account2.withdraw(400.00);
        assertEquals(-400.00, account2.getTransactions().get(1).getAmount(), 1e-15);

    }
    @Test(expected = IllegalArgumentException.class)
    public void testCannotWithdrawMoreThanBalance() {
        Account account00 = new Account(AccountType.SAVINGS,"Test account00");
        account00.deposit(400);
        account00.withdraw(400.01);

    }

    @Test
    public void testInterestEarnedFromCHECKINGAccount() {
        Account account3 = new Account((AccountType.CHECKING), "Test account3");
        account3.deposit(10);
        account3.deposit(10, Instant.now().plus(10, ChronoUnit.DAYS));
        assertEquals(0.00027397598,account3.getTotalInterestEarned(), 1e-9);

    }
    @Test
    public void testInterestEarnedFromSAVINGSAccountWithBalanceLessThan1000() {
        Account account4 = new Account((AccountType.SAVINGS), "Test account4");
        account4.deposit(10);
        account4.deposit(10, Instant.now().plus(10, ChronoUnit.DAYS));
        assertEquals(0.00027397598,account4.getTotalInterestEarned(), 1e-9);

    }
    @Test
    public void testInterestEarnedFromSAVINGSAccountWithBalanceGreaterThan1000() {
        Account account5 = new Account((AccountType.SAVINGS), "Test account5");
        account5.deposit(10000);
        account5.deposit(10, Instant.now().plus(10, ChronoUnit.DAYS));
        assertEquals(0.4931628445,account5.getTotalInterestEarned(), 1e-9);

    }
    @Test
    public void testInterestEarnedFromSAVINGSAccountWithBalanceOnTheEdgeCase() {
        Account account6 = new Account((AccountType.SAVINGS), "Test account6");
        account6.deposit(999.99);
        account6.deposit(10, Instant.now().plus(10, ChronoUnit.DAYS));
        assertEquals(0.01095887105,account6.getTotalInterestEarned(), 1e-9);

    }
    @Test
    public void testInterestEarnedFromMAXI_SAVINGSAccountWithoutAnyTransactionsInTheLast10Days() {
        Account account7 = new Account((AccountType.MAXI_SAVINGS), "Test account7");
        account7.deposit(10);
        account7.deposit(10, Instant.now().plus(15, ChronoUnit.DAYS));
        assertEquals(0.02056766041,account7.getTotalInterestEarned(), 1e-9);

    }
    @Test
    public void testInterestEarnedFromMAXI_SAVINGSAccountWithATransactionInTheLast10Days() {
        Account account8 = new Account((AccountType.MAXI_SAVINGS), "Test account8");
        account8.deposit(10);
        account8.deposit(10, Instant.now().plus(5, ChronoUnit.DAYS));
        assertEquals(0.0001369870517,account8.getTotalInterestEarned(), 1e-9);

    }
    @Test
    public void testInterestEarnedFromMAXI_SAVINGSAccountWithATransactionExactly10DaysAgoEdgeCase() {
        // (Expect interest rate to be 0.1%)
        Account account9 = new Account((AccountType.MAXI_SAVINGS), "Test account9");

        Instant now = Instant.now();
        Instant tenDaysTime = now.plus(10,ChronoUnit.DAYS);

        account9.deposit(10,now);
        account9.deposit(10, tenDaysTime);

        assertEquals(0.000273975980507,account9.getTotalInterestEarned(), 1e-9);

    }

    @Test
    public void testBalanceAfterDepositWithoutInterest() {
        Account account10 = new Account(AccountType.MAXI_SAVINGS,"Test account10");
        account10.deposit(4000);
        assertEquals(4000.00, account10.getBalance(), 1e-9);
    }

    @Test
    public void testBalanceAfterDepositWithInterest() {
        Account account11 = new Account(AccountType.CHECKING,"Test account11");
        account11.deposit(100);
        account11.deposit(100,Instant.now().plus(10,ChronoUnit.DAYS));
        assertEquals(200.0027397598, account11.getBalance(), 1e-9);
    }








}

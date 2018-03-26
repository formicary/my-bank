package com.abc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateProvider.class)
public class CheckingAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Bank bank;
    private Account account;
    @Mock public DateProvider dateProvider;

    @Before
    public void setup() {
        bank = new Bank();
        account = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount("one", account);
        bank.addCustomer(bill);

        mockStatic(DateProvider.class);
        when(DateProvider.getInstance()).thenReturn(dateProvider);
    }

    @Test
    public void testAccountType(){
        assertEquals(Account.CHECKING, account.getAccountType());
    }

    @Test
    public void testInterestScenarioOne() {
        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusDays(5));
        account.deposit(3000.0, true);
        when(dateProvider.now()).thenReturn(LocalDateTime.now());

        double expectedInterest = (3000.0 * Math.pow((1 + CheckingAccount.interestRate/365), 5) - 3000.0);
        double interestEarned = account.interestEarned();

        assertEquals(expectedInterest, interestEarned, DOUBLE_DELTA);
    }

    @Test
    public void initialSumIsZero(){
        assertEquals(0, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void checkAmountAfterDeposit(){
        account.deposit(100, true);
        assertEquals(100, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void checkAmountAfterWithdrawal(){
        account.deposit(70, true);
        account.withdraw(50, true);
        assertEquals(20, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIllegalAmountWithdrawal(){
        account.withdraw(50, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNegativeAmountWithdrawal(){
        account.deposit(70, true);
        account.withdraw(-50, true);
    }

    @Test
    public void testTransferDecreasesAmount(){
        final Account toAcc = new CheckingAccount();
        account.deposit(50, true);
        //account.
    }

    @Test
    public void getStatementEmpty(){
        String finalStatement = "Checking Account\n" +
                "Total $0.00";
        assertEquals(finalStatement, account.getStatement());
    }

    @Test
    public void getStatementSingleDeposit(){
        account.deposit(70, true);
        String finalStatement = "Checking Account\n" +
                "  deposit $70.00\n" +
                "Total $70.00";
        assertEquals(finalStatement, account.getStatement());
    }

    @Test
    public void getStatementDepositAndWithdrawal(){
        account.deposit(70, true);
        account.withdraw(70, true);
        String finalStatement = "Checking Account\n" +
                "  deposit $70.00\n" +
                "  withdrawal $70.00\n" +
                "Total $0.00";
        assertEquals(finalStatement, account.getStatement());
    }
}

package com.abc.Accounts;

import com.abc.Utils.IDateProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;

/**
 * Represents tests for the CheckingAccount class.
 */
public class CheckingAccountTests {
    /**
     * The dummy account ID.
     */
    private final int dummyAccountId = 1;

    /**
     * The dummy customer ID.
     */
    private final int dummyCustomerId = 2;

    /**
     * The dummy amount of money.
     */
    private final double dummyAmount = 100000.0;

    /**
     * The dummy date.
     */
    private Date dummyDate;

    /**
     * The date formatter.
     */
    private SimpleDateFormat dateFormatter;

    /**
     * The date provider.
     */
    @Mock
    private IDateProvider mockDateProvider;

    /**
     * The checking account.
     */
    private CheckingAccount account;

    /**
     * Sets up before each test.
     */
    @Before
    public void setUp() {
        this.dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.dummyDate = this.dateFormatter.parse("31-08-2017 12:20:57", new ParsePosition(0));

        this.mockDateProvider = Mockito.mock(IDateProvider.class);

        when(this.mockDateProvider.now()).thenReturn(this.dummyDate);

        this.account = new CheckingAccount(this.mockDateProvider, this.dummyAccountId, this.dummyCustomerId);
    }

    /**
     * Tests that calculating interest earned when the only transaction that has happened occurred
     * today return zero.
     */
    @Test
    public void calculatingInterestEarnedWhenOnlyTransactionHappenedTodayReturnsZero() {
        this.account.deposit(this.dummyAmount);

        double expectedInterestEarned = 0.0;
        double actualInterestEarned = this.account.calculateInterestEarned();

        Assert.assertEquals(actualInterestEarned, expectedInterestEarned, 0.01);
    }

    /**
     * Tests that calculating interest earned correctly calculates the interest earned.
     */
    @Test
    public void calculatingInterestEarnedCorrectlyCalculatesInterestEarned() {
        this.account.deposit(this.dummyAmount);

        // Reset the the current date to one year in the future.
        Date nowDate = this.dateFormatter.parse("31-08-2018 12:20:57", new ParsePosition(0));
        when(this.mockDateProvider.now()).thenReturn(nowDate);

        double expectedInterestEarned = 100.05;
        double actualInterestEarned = this.account.calculateInterestEarned();

        Assert.assertEquals(actualInterestEarned, expectedInterestEarned, 0.01);
    }

    /**
     * Tests that calculating interest earned when there are multiple transactions correctly
     * calculates the interest earned.
     */
    @Test
    public void calculatingInterestEarnedWhenMultipleTransactionsCorrectlyCalculatesInterestEarned() {
        this.account.deposit(this.dummyAmount);

        Date nowDate = this.dateFormatter.parse("31-08-2018 12:20:57", new ParsePosition(0));
        when(this.mockDateProvider.now()).thenReturn(nowDate);

        this.account.deposit(this.dummyAmount);

        nowDate = this.dateFormatter.parse("31-12-2018 12:20:57", new ParsePosition(0));
        when(this.mockDateProvider.now()).thenReturn(nowDate);

        this.account.withdraw(this.dummyAmount / 2);

        nowDate = this.dateFormatter.parse("31-12-2019 12:20:57", new ParsePosition(0));
        when(this.mockDateProvider.now()).thenReturn(nowDate);

        double expectedInterestEarned = 317.19;
        double actualInterestEarned = this.account.calculateInterestEarned();

        Assert.assertEquals(actualInterestEarned, expectedInterestEarned, 0.01);
    }

    /**
     * Getting account statement gets account statement with correct account type.
     */
    @Test
    public void gettingAccountStatementGetsAccountStatementWithCorrectAccountType() {
        String expectedAccountType = "Checking";

        String actualAccountStatement = this.account.getAccountStatement();

        Assert.assertTrue(actualAccountStatement.contains(expectedAccountType));
    }
}

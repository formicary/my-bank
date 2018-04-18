package com.abc.Accounts;

import com.abc.Transactions.ITransaction;
import com.abc.Transactions.TransactionException;
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
 * Represents tests for the AbstractAccount class.
 */
public class AbstractAccountTests {
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
    private final double dummyAmount = 10.0;

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
     * The abstract account.
     */
    private DummyAccount account;

    /**
     * Sets up before each test.
     */
    @Before
    public void setUp() {
        this.dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.dummyDate = this.dateFormatter.parse("31-08-2017 12:20:57", new ParsePosition(0));

        this.mockDateProvider = Mockito.mock(IDateProvider.class);

        when(this.mockDateProvider.now()).thenReturn(this.dummyDate);

        this.account = new DummyAccount(this.mockDateProvider, this.dummyAccountId, this.dummyCustomerId);
    }

    /**
     * Tests that depositing into account deposits into account.
     */
    @Test
    public void depositingIntoAccountDepositsIntoAccount() {
        this.account.deposit(this.dummyAmount);

        ITransaction transaction = this.account.getTransactions().get(0);

        Assert.assertEquals(transaction.getAmount(), this.dummyAmount, 0.001);
    }

    /**
     * Tests that depositing into account when the amount is less than or equal to zero
     * throws a TransactionException.
     */
    @Test(expected = TransactionException.class)
    public void depositingIntoAccountWhenAmountIsLessThanOrEqualToZeroThrowsTransactionException() {
        this.account.deposit(-this.dummyAmount);
    }

    /**
     * Tests that withdrawing from account withdraws from account.
     */
    @Test
    public void withdrawingFromAccountWithdrawsFromAccount() {
        double withdrawAmount = this.dummyAmount / 2;

        this.account.deposit(this.dummyAmount);
        this.account.withdraw(withdrawAmount);

        ITransaction transaction = this.account.getTransactions().get(1);

        Assert.assertEquals(transaction.getAmount(), -withdrawAmount, 0.001);
    }

    /**
     * Tests that withdrawing from account when the amount is less than or equal to zero
     * throws a TransactionException.
     */
    @Test(expected = TransactionException.class)
    public void withdrawingFromAccountWhenAmountIsLessThanOrEqualToZeroThrowsTransactionException() {
        this.account.withdraw(-this.dummyAmount);
    }

    /**
     * Tests that withdrawing from the account when the amount is more than the account's balance
     * throws a TransactionException.
     */
    @Test(expected = TransactionException.class)
    public void withdrawingFromAccountWhenAmountIsMoreThanAccountBalanceThrowsTransactionException() {
        this.account.deposit(this.dummyAmount / 2);

        this.account.withdraw(this.dummyAmount);
    }

    /**
     * Tests that summing the transactions in account sums the transactions.
     */
    @Test
    public void summingTransactionsSumsTransactions() {
        this.account.deposit(this.dummyAmount);
        this.account.deposit(this.dummyAmount);
        this.account.deposit(this.dummyAmount);
        this.account.withdraw(this.dummyAmount);

        double sumTransactions = this.account.sumTransactions();

        Assert.assertEquals(sumTransactions, dummyAmount * 2, 0.001);
    }

    /**
     * Tests that calculating the interest earned when no transactions exist in the account
     * returns zero.
     */
    @Test
    public void calculatingInterestEarnedWhenNoTransactionsExistReturnsZero() {
        double expectedInterestEarned = 0;
        double actualInterestEarned = this.account.calculateInterestEarned();

        Assert.assertEquals(actualInterestEarned, expectedInterestEarned, 0.001);
    }

    /**
     * Tests that calculating interest earned correctly attempts to accrue interest between transaction dates.
     * We should expect interest earned is zero as the dummy implementation of the AbstractAccount class we are
     * using has an interest rate of 0.
     */
    @Test
    public void calculatingInterestEarnedCorrectlyAttemptsToAccrueInterestBetweenTransactionDates() {
        this.account.deposit(this.dummyAmount);

        Date dummyTransactionDate = this.dateFormatter.parse("23-09-2017 12:20:57", new ParsePosition(0));
        when(this.mockDateProvider.now()).thenReturn(dummyTransactionDate);

        this.account.deposit(this.dummyAmount);

        dummyTransactionDate = this.dateFormatter.parse("23-10-2017 12:20:57", new ParsePosition(0));
        when(this.mockDateProvider.now()).thenReturn(dummyTransactionDate);

        this.account.deposit(this.dummyAmount);

        dummyTransactionDate = this.dateFormatter.parse("23-11-2017 12:20:57", new ParsePosition(0));
        when(this.mockDateProvider.now()).thenReturn(dummyTransactionDate);

        this.account.withdraw(this.dummyAmount);

        double expectedInterestEarned = 0;
        double actualInterestEarned = this.account.calculateInterestEarned();

        Assert.assertEquals(actualInterestEarned, expectedInterestEarned, 0.001);
    }

    /**
     * Getting the account statement gets the account statement.
     */
    @Test
    public void gettingAccountStatementGetsAccountStatement() {
        this.account.deposit(this.dummyAmount);
        this.account.withdraw(this.dummyAmount);

        String expectedAccountType = "Dummy";
        String expectedTransactionOne = "deposit $10.00";
        String expectedTransactionTwo = "withdrawal $10.00";
        String expectedBalance = "Total $0.00";

        String actualAccountStatement = this.account.getAccountStatement();

        Assert.assertTrue(actualAccountStatement.contains(expectedAccountType));
        Assert.assertTrue(actualAccountStatement.contains(expectedTransactionOne));
        Assert.assertTrue(actualAccountStatement.contains(expectedTransactionTwo));
        Assert.assertTrue(actualAccountStatement.contains(expectedBalance));
    }
}

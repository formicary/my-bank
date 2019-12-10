package com.abc;

import com.abc.account_types.BaseAccount;
import com.abc.shared.Constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.abc.TestConstants.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BaseAccountTests {
    BaseAccountTestClass account;

    @Before
    public void initEach(){
        account = new BaseAccountTestClass();
    }

    @Rule
    public ExpectedException expectedError = ExpectedException.none();

    @Test
    public void deposit_WhenCalledWithValueLessThan0_ThrowsIllegalArgumentError() {
        expectedError.expect(IllegalArgumentException.class);
        expectedError.expectMessage("Amount must be greater than zero");

        account.deposit(-10);
    }

    @Test
    public void deposit_WhenCalledWithValue_AddsTransaction() {
        account.deposit(10);

        Transaction transaction = account.transactions.get(0);
        assertEquals(10, transaction.amount, DOUBLE_DELTA);
    }

    @Test
    public void withdraw_WhenCalledWithValueLessThan0_ThrowsIllegalArgumentError() {
        expectedError.expect(IllegalArgumentException.class);
        expectedError.expectMessage("Amount must be greater than zero");

        account.withdraw(-10);
    }

    @Test
    public void withdraw_WhenCalledWithValue_AddsTransactionAndSetsDate() {
        account.withdraw(50);

        Transaction transaction = account.transactions.get(0);
        assertEquals(-50, transaction.amount, DOUBLE_DELTA);
        assertNotNull(account.lastWithdrawal);
    }

    @Test
    public void sumAllTransactions_WhenCalledWithOneTransaction_ReturnsCorrectSum() {
        account.deposit(100);
        double result = account.sumTransactions();

        assertEquals(100, result, DOUBLE_DELTA);
    }

    @Test
    public void sumAllTransactions_WhenCalledWithTwoTransactions_ReturnsCorrectSum() {
        account.deposit(100);
        account.deposit(66);

        double result = account.sumTransactions();

        assertEquals(166, result, DOUBLE_DELTA);
    }

    @Test
    public void sumAllTransactions_WhenCalledWithNoTransactions_Returns0() {
        double result = account.sumTransactions();

        assertEquals(0, result, DOUBLE_DELTA);
    }

    @Test
    public void getAccountSummary_WhenCalledWithNoTransactionInAccount_ReturnsEmptySavingsSummary() {
        String result = account.getAccountSummary();

        assertEquals("SavingsAccount\nTotal: $0.00", result);
    }

    @Test
    public void getAccountSummary_WhenCalledWithNegativeTransactionInAccount_ReturnsNegativeSavingsSummary() {
        account.withdraw(50);

        String result = account.getAccountSummary();

        assertEquals("SavingsAccount\n- Withdraw: $-50.00\nTotal: $-50.00", result);
    }

    @Test
    public void getAccountSummary_WhenCalledWithPositiveTransactionInAccount_ReturnsPositiveSavingsSummary() {
        account.deposit(100);

        String result = account.getAccountSummary();

        assertEquals("SavingsAccount\n- Deposit: $100.00\nTotal: $100.00", result);
    }
}

class BaseAccountTestClass extends BaseAccount{
    public Constants.AccountTypes getAccountType() {
        return Constants.AccountTypes.SavingsAccount;
    }

    public double getInterestEarned() {
        return 0;
    }
}

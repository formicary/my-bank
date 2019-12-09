package com.abc;

import com.abc.account_types.BaseAccount;
import com.abc.account_types.CheckingAccount;
import com.abc.account_types.SavingsAccount;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class BaseAccountTests {
    private static final double DOUBLE_DELTA = 1e-15;

    // @ BEFORE

    @Rule
    public ExpectedException expectedError = ExpectedException.none();

    @Test
    public void Deposit_WhenCalledWithValueLessThan0_ThrowsIllegalArgumentError(){
        expectedError.expect(IllegalArgumentException.class);
        expectedError.expectMessage("Amount must be greater than zero");

        BaseAccountTestClass account = new BaseAccountTestClass();
        account.deposit(-10);

        //BaseAccount account = Mockito.mock(BaseAccount.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void Deposit_WhenCalledWithValue_AddsTransaction(){
        //BaseAccount account = Mockito.mock(BaseAccount.class, Mockito.CALLS_REAL_METHODS);
        BaseAccountTestClass account = new BaseAccountTestClass();

        account.deposit(10);

        Transaction transaction = account.transactions.get(0);
        assertEquals(10, transaction.amount, DOUBLE_DELTA);
    }

    @Test
    public void Withdraw_WhenCalledWithValueLessThan0_ThrowsIllegalArgumentError(){
        expectedError.expect(IllegalArgumentException.class);
        expectedError.expectMessage("Amount must be greater than zero");

        BaseAccountTestClass account = new BaseAccountTestClass();
        account.withdraw(-10);
    }

    @Test
    public void Withdraw_WhenCalledWithValue_AddsTransaction(){
        BaseAccountTestClass account = new BaseAccountTestClass();

        account.withdraw(50);

        Transaction transaction = account.transactions.get(0);
        assertEquals(-50, transaction.amount, DOUBLE_DELTA);
    }

    @Test
    public void SumAllTransactions_WhenCalledWithOneTransaction_ReturnsCorrectSum(){
        BaseAccountTestClass account = new BaseAccountTestClass();

        account.deposit(100);
        double result = account.sumTransactions();

        assertEquals(100, result, DOUBLE_DELTA);
    }

    @Test
    public void SumAllTransactions_WhenCalledWithTwoTransactions_ReturnsCorrectSum(){
        BaseAccountTestClass account = new BaseAccountTestClass();

        account.deposit(100);
        account.deposit(66);
        double result = account.sumTransactions();

        assertEquals(166, result, DOUBLE_DELTA);
    }

    @Test
    public void SumAllTransactions_WhenCalledWithNoTransactions_Returns0(){
        BaseAccountTestClass account = new BaseAccountTestClass();

        double result = account.sumTransactions();

        assertEquals(0, result, DOUBLE_DELTA);
    }

    // Need to decide on leading capital
    @Test
    public void getAccountSummary_WhenCalledWithNoTransactionInAccount_ReturnsEmptySavingsSummary(){
        SavingsAccount account = new SavingsAccount();

        String result = account.getAccountSummary();

        assertEquals("SavingsAccount\nTotal: $0.00", result);
    }

    @Test
    public void getAccountSummary_WhenCalledWithNegativeTransactionInAccount_ReturnsNegativeSavingsSummary(){
        SavingsAccount account = new SavingsAccount();
        account.withdraw(50);

        String result = account.getAccountSummary();

        assertEquals("SavingsAccount\nWithdraw: $50.00\nTotal Balance: -$50.00", result);
    }

    @Test
    public void getAccountSummary_WhenCalledWithPositiveTransactionInAccount_ReturnsPositiveSavingsSummary(){
        SavingsAccount account = new SavingsAccount();
        account.deposit(100);

        String result = account.getAccountSummary();

        assertEquals("SavingsAccount\nDeposit: $100.00\nTotal: $100.00", result);
    }

    // Make a test for both?

    @Test
    public void getInterestEarned_WhenCalledWithBalanceIsLessThan1000_ReturnsCorrectInterest(){
        SavingsAccount account = new SavingsAccount();

        account.deposit(500);

        double result = account.getInterestEarned();

        assertEquals(0.05, result, DOUBLE_DELTA);
    }
}

class BaseAccountTestClass extends BaseAccount{

    public String getAccountSummary() {
        return null;
    }

    public Constants.AccountTypes getAccountType() {
        return null;
    }

    public double getInterestEarned() {
        return 0;
    }
}

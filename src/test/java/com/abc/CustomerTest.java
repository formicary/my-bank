package com.abc;

import com.abc.account_types.BaseAccount;
import com.abc.account_types.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void openAccount_WhenCalledWithAccountTypeSavings_OpensSavingsAccount(){
        Customer customer = new Customer("John");

        // Maybe we should pass in the Factory?
        BaseAccount account = customer.openAccount(Constants.AccountTypes.SavingsAccount);

        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    public void getNumberOfAccounts_WhenCalledWith2Accounts_Returns2(){
        Customer customer = new Customer("John");

        customer.openAccount(Constants.AccountTypes.CheckingAccount);
        customer.openAccount(Constants.AccountTypes.SavingsAccount);

        int result = customer.getNumberOfAccounts();

        assertEquals(2, result);
    }

    @Test
    public void getTotalInterestEarned_WhenCalledWithNoAccounts_Returns0(){
        Customer customer = new Customer("John");

        double result = customer.getTotalInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }

    @Test
    public void getTotalInterestEarned_WhenCalledWith2Accounts_ReturnsCorrectInterest(){
        Customer customer = new Customer("John");

        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(100);
        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(200);

        double result = customer.getTotalInterestEarned();

        assertEquals(0.03, result, DOUBLE_DELTA);
    }

    @Test
    public void getStatement_WhenCalledWithNoAccounts_ReturnsCorrectEmptySummary(){
        Customer customer = new Customer("John");

        String result = customer.getStatement();

        assertEquals("Statement for John\nTotal In All Accounts: $0.00", result);
    }

    @Test
    public void getStatement_WhenCalledWith2Accounts_ReturnsCorrectSummary(){
        Customer customer = new Customer("John");

        customer.openAccount(Constants.AccountTypes.SavingsAccount).deposit(100);
        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(500);

        String result = customer.getStatement();

        assertEquals("Statement for John\nSavingsAccount\n- Deposit: $100.00\nTotal: $100.00\nCheckingAccount\n- Deposit: $500.00\nTotal: $500.00\nTotal In All Accounts: $600.00", result);
    }
}

package com.abc;

import com.abc.account_types.BaseAccount;
import com.abc.account_types.SavingsAccount;
import com.abc.shared.Constants;
import org.junit.Before;
import org.junit.Test;

import static com.abc.TestConstants.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTests {
    Customer customer;

    @Before
    public void initEach(){
        customer = new Customer("John");
    }

    @Test
    public void openAccount_WhenCalledWithAccountTypeSavings_OpensSavingsAccount(){
        BaseAccount account = customer.openAccount(Constants.AccountTypes.SavingsAccount);

        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    public void getNumberOfAccounts_WhenCalledWith2Accounts_Returns2(){
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
        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(100);
        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(200);

        double result = customer.getTotalInterestEarned();

        assertEquals(0.03, result, DOUBLE_DELTA);
    }

    @Test
    public void getStatement_WhenCalledWithNoAccounts_ReturnsCorrectEmptySummary(){
        String result = customer.getAccountsStatement();

        assertEquals("Statement for John\nTotal In All Accounts: $0.00", result);
    }

    @Test
    public void getStatement_WhenCalledWith2Accounts_ReturnsCorrectSummary(){
        customer.openAccount(Constants.AccountTypes.SavingsAccount).deposit(100);
        customer.openAccount(Constants.AccountTypes.CheckingAccount).deposit(500);

        String result = customer.getAccountsStatement();

        assertEquals("Statement for John\nSavingsAccount\n- Deposit: $100.00\nTotal: $100.00\nCheckingAccount\n- Deposit: $500.00\nTotal: $500.00\nTotal In All Accounts: $600.00", result);
    }
}

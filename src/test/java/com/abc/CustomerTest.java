package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.AccountCreator;
import com.abc.Accounts.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testCustomerStatement() {

        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        Customer oscar = new Customer("Oscar").openAccount(savingsAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts() {
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);
        Customer oscar = new Customer("Oscar")
                .openAccount(savingsAccount)
                .openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);
        Account maxiSavingsAccount = AccountCreator.createAccount(AccountType.MAXI_SAVINGS);

        Customer oscar = new Customer("Oscar")
                .openAccount(savingsAccount)
                .openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransferFunds() {
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);

        Customer oscar = new Customer("Oscar")
                .openAccount(savingsAccount)
                .openAccount(checkingAccount);

        savingsAccount.deposit(1000.0);
        oscar.transferFunds(savingsAccount, checkingAccount, 100);

        assertEquals(900.0, savingsAccount.getBalance(), DOUBLE_DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsDoesNotOwnSourceAccount() {
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);

        Customer oscar = new Customer("Oscar")
                .openAccount(checkingAccount);

        savingsAccount.deposit(1000.0);
        oscar.transferFunds(savingsAccount, checkingAccount, 100);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsDoesNotOwnDestAccount() {
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        Account checkingAccount = AccountCreator.createAccount(AccountType.CHECKING);

        Customer oscar = new Customer("Oscar")
                .openAccount(checkingAccount);

        savingsAccount.deposit(1000.0);
        oscar.transferFunds(checkingAccount, savingsAccount, 100);


    }
}

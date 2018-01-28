package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testCustomerStatementGeneration() {
        Bank bank = new Bank();
        Customer henry = new Customer("Henry", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);
        Account savingsAccount = henry.openAccount(Account.SAVINGS);

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
    public void testTransferFunds() {
        Bank bank = new Bank();

        Customer henry = new Customer("Henry", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);
        Account savingsAccount = henry.openAccount(Account.SAVINGS);
        checkingAccount.deposit(100.00);
        henry.transferFundsBetweenAccounts(checkingAccount, savingsAccount, 50.00);
        assertEquals(savingsAccount.getBalance(), 50.00, DOUBLE_DELTA);
        assertEquals(checkingAccount.getBalance(), 50.00, DOUBLE_DELTA);
    }

    @Test
    public void testTransferFundsBetweenTwoDifferentCustomers() {
        Bank bank = new Bank();

        Customer henry = new Customer("Henry", bank);
        Customer hacker = new Customer("hacker", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);
        Account savingsAccount = hacker.openAccount(Account.SAVINGS);
        checkingAccount.deposit(100.00);
        try {
            henry.transferFundsBetweenAccounts(checkingAccount, savingsAccount, 50.00);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertEquals(savingsAccount.getBalance(), 0.00, DOUBLE_DELTA);
        assertEquals(checkingAccount.getBalance(), 100.00, DOUBLE_DELTA);
    }

    @Test
    public void testTransferInsufficientFunds() {
        Bank bank = new Bank();

        Customer henry = new Customer("Henry", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);
        Account savingsAccount = henry.openAccount(Account.SAVINGS);
        checkingAccount.deposit(100.00);
        try {
            henry.transferFundsBetweenAccounts(checkingAccount, savingsAccount, 150.00);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertEquals(savingsAccount.getBalance(), 0.00, DOUBLE_DELTA);
        assertEquals(checkingAccount.getBalance(), 100.00, DOUBLE_DELTA);
    }


    @Test
    public void testOneAccount() {
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar", bank);
        oscar.openAccount(Account.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts() {
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar", bank);
        oscar.openAccount(Account.SAVINGS);
        oscar.openAccount(Account.CHECKING);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar", bank);
        oscar.openAccount(Account.SAVINGS);
        oscar.openAccount(Account.CHECKING);
        oscar.openAccount(Account.MAXI_SAVINGS);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
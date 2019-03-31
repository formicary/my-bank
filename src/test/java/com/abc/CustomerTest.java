package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testStatementGeneration(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferOneAccountNotOwned(){
        Account notOwnedAccount = new Account(Account.MAXI_SAVINGS);
        Account ownedAccunt = new Account(Account.SAVINGS);
        Customer bob = new Customer("Bob");

        bob.openAccount(ownedAccunt);
        ownedAccunt.deposit(2000.0);
        bob.transferMoney(ownedAccunt, notOwnedAccount, 1000.0);
    }

    @Test
    public void testTransferBothAccountsOwned(){
        Account firstAccount = new Account(Account.MAXI_SAVINGS);
        Account secondAccount = new Account(Account.SAVINGS);
        Customer bob = new Customer("Bob");

        bob.openAccount(firstAccount);
        bob.openAccount(secondAccount);
        firstAccount.deposit(2000.0);
        secondAccount.deposit(2000.0);
        bob.transferMoney(firstAccount, secondAccount, 1000.0);

        assertEquals(1000.0, firstAccount.sumAllTransactions(),DOUBLE_DELTA);
        assertEquals(3000.0, secondAccount.sumAllTransactions(),DOUBLE_DELTA);
    }
}

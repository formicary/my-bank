package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testStatmentOutput(){
        Customer henry = new Customer("Henry");

        Account checkingAccount = henry.openAccount(Account.AccountType.CHECKING);
        Account savingsAccount = henry.openAccount(Account.AccountType.SAVINGS);

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
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(Account.AccountType.SAVINGS);

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(Account.AccountType.SAVINGS);
        oscar.openAccount(Account.AccountType.CHECKING);

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        
        oscar.openAccount(Account.AccountType.SAVINGS);
        oscar.openAccount(Account.AccountType.CHECKING);
        oscar.openAccount(Account.AccountType.MAXI_SAVINGS);
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testValidTransfer() {
        Customer alice = new Customer("Alice");
        Account aliceSavings = alice.openAccount(Account.AccountType.SAVINGS);
        aliceSavings.deposit(100.00);

        Customer bob = new Customer("Bob");
        Account bobSavings = bob.openAccount(Account.AccountType.SAVINGS);

        alice.transfer(aliceSavings, bobSavings, 50.0);

        assertEquals(50.0, aliceSavings.calculateBalance(), DOUBLE_DELTA);
        assertEquals(50.0, bobSavings.calculateBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverwithdrawTransfer() {
        Customer alice = new Customer("Alice");
        Account aliceSavings = alice.openAccount(Account.AccountType.SAVINGS);
        aliceSavings.deposit(100.00);

        Customer bob = new Customer("Bob");
        Account bobSavings = bob.openAccount(Account.AccountType.SAVINGS);

        alice.transfer(aliceSavings, bobSavings, 200.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnownedTransfer() {
        Customer alice = new Customer("Alice");
        Account aliceSavings = alice.openAccount(Account.AccountType.SAVINGS);
        aliceSavings.deposit(100.00);

        Customer bob = new Customer("Bob");
        Account bobSavings = bob.openAccount(Account.AccountType.SAVINGS);

        bob.transfer(aliceSavings, bobSavings, 200.0);
    }
}

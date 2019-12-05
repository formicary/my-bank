package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-9;

    @Test //Test customer statement generation
    public void testStatementOutput(){
        Customer henry = new Customer("Henry");

        Account checkingAccount = henry.openAccount(Account.AccountType.CHECKING);
        Account savingsAccount = henry.openAccount(Account.AccountType.SAVINGS);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        henry.transfer(savingsAccount, checkingAccount, 2000.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  DEPOSIT $100.00\n" +
                "  TRANSFER_IN $2,000.00\n" +
                "Total $2,100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  DEPOSIT $4,000.00\n" +
                "  WITHDRAWAL $200.00\n" +
                "  TRANSFER_OUT $2,000.00\n" +
                "Total $1,800.00\n" +
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
    public void testManyAcounts() {
        Customer oscar = new Customer("Oscar");
        
        for (int i = 0; i < 5; i++) {
            oscar.openAccount(Account.AccountType.SAVINGS);
        }
        assertEquals(5, oscar.getNumberOfAccounts());
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

    @Test
    public void testInterestOneDay() {
        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Customer alice = new Customer("Alice");
        Account aliceChecking = alice.openAccount(Account.AccountType.CHECKING);
        aliceChecking.deposit(100.0);

        c.add(Calendar.DATE, 1);
        DateProvider.getInstance().setCustomDate(c.getTime());

        alice.updateInterestPayments();

        assertEquals(100.0 + (100.0 * 0.001 / 365.25), aliceChecking.calculateBalance(), DOUBLE_DELTA);

    }

    @Test
    public void testInterestOneWeek() {
        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Customer alice = new Customer("Alice");
        Account aliceChecking = alice.openAccount(Account.AccountType.CHECKING);
        aliceChecking.deposit(100.0);

        for (int i = 0; i < 7; i++) {
            c.add(Calendar.DATE, 1);
            c.add(Calendar.MILLISECOND, 1);
            DateProvider.getInstance().setCustomDate(c.getTime());

            alice.updateInterestPayments();
        }

        assertEquals(100.0 * Math.pow(1 + (0.001 / 365.25), 7.0), aliceChecking.calculateBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestOneYear() {
        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Customer alice = new Customer("Alice");
        Account aliceChecking = alice.openAccount(Account.AccountType.CHECKING);
        aliceChecking.deposit(100.0);

        for (int i = 0; i < 365; i++) {
            c.add(Calendar.DATE, 1);
            c.add(Calendar.MILLISECOND, 1);
            DateProvider.getInstance().setCustomDate(c.getTime());

            alice.updateInterestPayments();
        }

        assertEquals(100.0 * Math.pow(1 + (0.001 / 365.25), 365.0), aliceChecking.calculateBalance(), DOUBLE_DELTA);
    }
}

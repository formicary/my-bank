package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testStatementGeneration() {
        // To standardise the test I have brought out the values from the strings and results.
        // This way the values can be changed or created here without having to go through the entire code and change the statement string.
        final String newline = "\n";
        final double deposit1 = 100.0;
        final double deposit2 = 4000.0;
        final double withdrawal1 = 200.0;
        final double totalChecking;
        totalChecking = deposit1;
        final double totalSaving;
        totalSaving = deposit2 - withdrawal1;

        Account checkingAccount = new Account(AccountTypes.CHECKING);
        Account savingsAccount = new Account(AccountTypes.SAVINGS);
        // Changed name of customer to distinguish from other tests.
        Customer jill = new Customer("Jill", checkingAccount);
        jill.openAnotherAccount(savingsAccount);

        checkingAccount.deposit(deposit1);
        savingsAccount.deposit(deposit2);
        savingsAccount.withdraw(withdrawal1);
        System.out.println(jill.getStatement());
        String expectedStatement = String.format("Statement for " + jill.getName() + newline +
                newline +
                "Checking Account" + newline +
                "  deposit $%,.2f" + newline +
                "Total $%,.2f" + newline +
                newline +
                "Savings Account" + newline +
                "  deposit $%,.2f" + newline +
                "  withdrawal $%,.2f" + newline +
                "Total $%,.2f" + newline +
                newline +
                "Total In All Accounts $%,.2f", deposit1, totalChecking, deposit2, withdrawal1, totalSaving, totalChecking + totalSaving);
        System.out.println(expectedStatement);
        assertEquals(expectedStatement, jill.getStatement());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeposits() {
        Customer tim = new Customer("Tim", new Account(AccountTypes.CHECKING));
        tim.openAnotherAccount(new Account(AccountTypes.SAVINGS));
        tim.getAccount(0).deposit(new BigDecimal("10000000000"));
        tim.getAccount(0).deposit(0);
        assertEquals("10000000000", tim.getAccount(0).sumTransactions().stripTrailingZeros().toPlainString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawals() {
        Customer james = new Customer("James", new Account(AccountTypes.CHECKING));
        james.getAccount(0).withdraw(0);
        james.getAccount(0).deposit(500);
        james.getAccount(0).withdraw(499.99);
        assertEquals("0.01", james.getAccount(0).sumTransactions().stripTrailingZeros().toPlainString());
        james.getAccount(0).withdraw(0.01);
        james.getAccount(0).withdraw(new BigDecimal("10000000000"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransfers() {
        Customer jack = new Customer("Jack", new Account(AccountTypes.CHECKING));
        jack.openAnotherAccount(new Account(AccountTypes.SAVINGS));
        jack.getAccount(0).deposit(500);
        jack.getAccount(0).transfer(100, jack.getAccount(1));
        assertEquals(100, jack.getAccount(1).sumTransactions().intValue());
        assertEquals(400, jack.getAccount(0).sumTransactions().intValue());
        jack.getAccount(0).transfer(0, jack.getAccount(1));
        jack.getAccount(0).transfer(10000, jack.getAccount(1));
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar", new Account(AccountTypes.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts() {
        Customer bob = new Customer("Bob", new Account(AccountTypes.SAVINGS));
        bob.openAnotherAccount(new Account(AccountTypes.CHECKING));
        assertEquals(2, bob.getNumberOfAccounts());
    }

    // Added another account creation to make it 3 accounts
    @Test
    public void testThreeAccounts() {
        Customer tom = new Customer("Tom", new Account(AccountTypes.SAVINGS));
        tom.openAnotherAccount(new Account(AccountTypes.CHECKING));
        tom.openAnotherAccount(new Account(AccountTypes.MAXI_SAVINGS));
        assertEquals(3, tom.getNumberOfAccounts());
    }
}

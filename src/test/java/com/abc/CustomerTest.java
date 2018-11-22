package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testStatementGeneration() {

        Account checkingAccount = new Account(AccountTypes.CHECKING);
        Account savingsAccount = new Account(AccountTypes.SAVINGS);
        // Changed name of customer to distinguish from other tests.
        Customer jill = new Customer("Jill", checkingAccount);
        jill.openAnotherAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        System.out.println(jill.getStatement());
        assertEquals("Statement for Jill\n" +
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
                "Total In All Accounts $3,900.00", jill.getStatement());
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
    // and changed name of customer to distinguish from other tests.
    @Test
    public void testThreeAccounts() {
        Customer tom = new Customer("Tom", new Account(AccountTypes.SAVINGS));
        tom.openAnotherAccount(new Account(AccountTypes.CHECKING));
        tom.openAnotherAccount(new Account(AccountTypes.MAXI_SAVINGS));
        assertEquals(3, tom.getNumberOfAccounts());
    }
}

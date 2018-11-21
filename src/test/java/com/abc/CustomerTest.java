package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        Account checkingAccount = new Account(AccountTypes.CHECKING);
        Account savingsAccount = new Account(AccountTypes.SAVINGS);

        Customer henry = new Customer("Henry", checkingAccount);
        henry.openAnotherAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        System.out.println(henry.getStatement());
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
        Customer oscar = new Customer("Oscar", new Account(AccountTypes.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts() {
        Customer bob = new Customer("Bob", new Account(AccountTypes.SAVINGS));
        bob.openAnotherAccount(new Account(AccountTypes.CHECKING));
        assertEquals(2, bob.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer tom = new Customer("Tom", new Account(AccountTypes.SAVINGS));
        tom.openAnotherAccount(new Account(AccountTypes.CHECKING));
        tom.openAnotherAccount(new Account(AccountTypes.MAXI_SAVINGS));
        assertEquals(3, tom.getNumberOfAccounts());
    }
}

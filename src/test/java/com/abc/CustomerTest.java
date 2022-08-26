package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        Customer henry = new Customer("Henry");

        Account checkingAccount = new Account(henry, AccountType.CHECKING);
        Account savingsAccount = new Account(henry, AccountType.SAVINGS);

        henry.openAccount(checkingAccount).openAccount(savingsAccount);

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
    public void testGetNumberOfAccountsWith0Accounts() {
        Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    @Test
    public void testGetNumberOfAccountsWith3Accounts() {
        Customer oscar = new Customer("Oscar");
        for (int i = 0; i < 3; i++) {
            oscar.openAccount(new Account(oscar, AccountType.SAVINGS));
        }
        assertEquals(3, oscar.getNumberOfAccounts());
    }

}

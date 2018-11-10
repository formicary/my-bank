package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountTypes.CHECKING);
        Account savingsAccount = new Account(AccountTypes.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

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
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountTypes.SAVINGS));
        oscar.openAccount(new Account(AccountTypes.CHECKING));
        oscar.openAccount(new Account(AccountTypes.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

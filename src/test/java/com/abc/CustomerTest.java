package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        LocalDateTime time = DateProvider.getNow();
        Account checkingAccount = new AccountChecking();
        Account savingsAccount = new AccountSavings();

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0, time);
        savingsAccount.deposit(4000.0, time);
        savingsAccount.withdraw(200.0, time);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "\tdeposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "\tdeposit $4,000.00\n" +
                "\twithdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new AccountSavings());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new AccountSavings());
        oscar.openAccount(new AccountChecking());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new AccountSavings());
        oscar.openAccount(new AccountChecking());
        oscar.openAccount(new AccountMaxiSavings());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

}

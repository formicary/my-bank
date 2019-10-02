package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.Checking;
import com.abc.accounts.Savings;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test // Test customer name
    public void testName() {
        Customer alex = new Customer("Alex");
        assertEquals("Alex", alex.getName());
    }

    @Test // Test customer statement generation
    public void testApp(){

        Checking checkingAccount = new Checking();
        Savings savingsAccount = new Savings();

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
        Customer oscar = new Customer("Oscar").openAccount(new Savings());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Savings());
        oscar.openAccount(new Checking());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Savings());
        oscar.openAccount(new Savings());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

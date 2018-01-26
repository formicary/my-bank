package com.abc;

import org.junit.Ignore;
import org.junit.Test;
import org.omg.PortableInterceptor.ACTIVE;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){


        Bank bank = new Bank();
        Customer henry = new Customer("Henry", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);
        Account savingsAccount = henry.openAccount(Account.SAVINGS);

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

    @Test //Test customer statement generation
    public void testAppOverdrawn(){
        Bank bank = new Bank();

        Customer henry = new Customer("Henry", bank);
        Account checkingAccount = henry.openAccount(Account.CHECKING);

        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(100.0);
        System.out.println(henry.getStatement());

    }

    @Test
    public void testOneAccount(){
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar", bank);
        oscar.openAccount(Account.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar", bank);
        oscar.openAccount(Account.SAVINGS);
        oscar.openAccount(Account.CHECKING);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Bank bank = new Bank();

        Customer oscar = new Customer("Oscar", bank);
        oscar.openAccount(Account.SAVINGS);
        oscar.openAccount(Account.CHECKING);
        oscar.openAccount(Account.MAXI_SAVINGS);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

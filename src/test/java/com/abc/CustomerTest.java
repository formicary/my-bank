package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.Type.CHECKING);
        Account savingsAccount = new Account(Account.Type.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount, savingsAccount);

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
    public void testTransfer() {
        Account checkingAccount = new Account(Account.Type.CHECKING);
        Account savingsAccount = new Account(Account.Type.SAVINGS);

        Customer ryan = new Customer("Ryan").openAccount(checkingAccount, savingsAccount);

        checkingAccount.deposit(1000);
        savingsAccount.deposit(1000);
        ryan.transfer(500, checkingAccount, savingsAccount);

        assertEquals("Statement for Ryan\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $500.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", ryan.getStatement());

    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.Type.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.Type.SAVINGS));
        oscar.openAccount(new Account(Account.Type.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.Type.SAVINGS));
        oscar.openAccount(new Account(Account.Type.CHECKING));
        assertNotEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testInterest(){
        Account checkingAccount = new Account(Account.Type.CHECKING);
        Account checkingAccount2 = new Account(Account.Type.CHECKING);
        Customer lewis = new Customer("Lewis").openAccount(checkingAccount, checkingAccount2);
        checkingAccount.deposit(1000);
        checkingAccount.withdraw(500);
        checkingAccount2.deposit(1000);
        assertEquals(2.5, lewis.totalInterestEarned());
    }
}

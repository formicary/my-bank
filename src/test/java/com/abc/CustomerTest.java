package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    private String accountName1 = "first account";
    private String accountName2 = "second account";

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(accountName1, Account.CHECKING);
        Account savingsAccount = new Account(accountName2, Account.SAVINGS);

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
        Customer oscar = new Customer("Oscar").openAccount(new Account(accountName1, Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(accountName1, Account.SAVINGS));
        oscar.openAccount(new Account(accountName2, Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(accountName1, Account.SAVINGS));
        oscar.openAccount(new Account(accountName2, Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransfer(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(accountName1, Account.SAVINGS));
        oscar.openAccount(new Account(accountName2, Account.SAVINGS));
        oscar.transferBetweenAccounts("first account", "second account", 500);
        assertEquals((int) oscar.getAccount("first account").sumTransactions(), -500);
        assertEquals((int) oscar.getAccount("second account").sumTransactions(), 500);

    }

}

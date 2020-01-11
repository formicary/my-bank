package com.abc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.abc.accounttypes.*;

public class CustomerTest {

    private Customer oscar;

    @Before
    public void setup(){
        oscar = new Customer("Oscar");
    }

    //TODO: remove "test" prefixes
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(new CheckingAccount());
        Account savingsAccount = new Account(new SavingsAccount());

        oscar.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        //TODO: assert on individual values instead of the string
        assertEquals("Statement for Oscar\n" +
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
                "Total In All Accounts $3,900.00", oscar.getStatement());
    }

    @Test
    public void testOneAccount(){
        oscar.openAccount(new Account(new SavingsAccount()));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        oscar.openAccount(new Account(new SavingsAccount()));
        oscar.openAccount(new Account(new CheckingAccount()));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        oscar.openAccount(new Account(new SavingsAccount()));
        oscar.openAccount(new Account(new CheckingAccount()));
        oscar.openAccount(new Account(new MaxiSavingsAccount()));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void getNumberOfAccounts_NoAccounts(){
        assertEquals(0, oscar.getNumberOfAccounts());
    }

}

package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;
    Bank bank = new Bank();

    //Test customer statement generation
    @Test
    public void testGetStatement(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

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
    public void canCustomerOpenAnAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        //TODO
        bank.addCustomer(oscar);
        assertTrue(bank.doesCustomerHaveAccount(oscar.getName()));
    }

    @Test
    public void testGetNumberOfAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());

        oscar.openAccount(new CheckingAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTotalInterestEarned(){
        Account checking = new CheckingAccount();
        Customer oscar = new Customer("Oscar")
                .openAccount(checking);
        checking.deposit(1000);

        assertEquals(1.0, oscar.totalInterestEarned(), DOUBLE_DELTA);
        assertFalse(2.0 == oscar.totalInterestEarned());
    }

    @Test
    public void testTransferBetweenAccounts(){
        Account checking = new CheckingAccount();
        Account savings = new SavingsAccount();
        Customer oscar = new Customer("Oscar")
                .openAccount(checking);
        bank.addCustomer(oscar);
        bank.accountSetup(checking, oscar);
        bank.accountSetup(savings, oscar);
        checking.deposit(1000);
        oscar.transfer(200, checking, savings);

        assertEquals(800, checking.getBalance(), DOUBLE_DELTA);
        assertEquals(200, savings.getBalance(), DOUBLE_DELTA);
    }
}

package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    /**
     * Test for sending money from one account to the other
     */
    @Test
    public void testTransfer() {
        Account from = new CheckingAccount(100.0);
        Account to = new CheckingAccount();

        Customer oscar = new Customer("Oscar");
        oscar.openAccount(from);
        oscar.openAccount(to);
        from.transfer(oscar, from, to, 25.0);

        assertEquals(to.getBalance(), 25.0, 0.00000001);
        assertEquals(from.getBalance(), 75.0, 0.00000001);

    }
    
    /**
     * Test for sending money from one account to the other when the amount is too high
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTransferInsufficientFunds() throws IllegalArgumentException {
        Account from = new CheckingAccount(10.0);
        Account to = new SavingsAccount();

        Customer oscar = new Customer("Oscar");
        oscar.openAccount(from);
        oscar.openAccount(to);
        from.transfer(oscar, from, to, 25.0);

    }

    /**
     * Test for sending money from one account to the other when the account is of a different Customer
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTransferDifferentCustomer() throws IllegalArgumentException {
        Account from = new CheckingAccount(10.0);
        Account to = new SavingsAccount();

        Customer oscar = new Customer("Oscar");
        oscar.openAccount(from);
        Customer notOscar = new Customer("Oscar");
        notOscar.openAccount(to);
        from.transfer(oscar, from, to, 25.0);

    }
    
}

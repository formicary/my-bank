package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);


        henry.deposit(100.0, checkingAccount);
        henry.deposit( 4000.0, savingsAccount);
        henry.withdraw(200.0, savingsAccount);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  withdrawal $200.00\n" +
                "  deposit $4,000.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){

        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());

        Account checking = new Account(Account.AccountType.CHECKING);
        Account maxi =  new Account(Account.AccountType.MAXI_SAVINGS);

        Customer andy = new Customer("Andy");
        andy.openAccount(checking);
        andy.openAccount(maxi);

        andy.deposit(100, maxi);
        andy.transfer(maxi,checking,50);

        assertTrue(maxi.getCachedBalance() == checking.getCachedBalance());

        try {
            andy.deposit(-15.50, maxi);
        }catch (Exception exc){
        } assertTrue(maxi.getCachedBalance() == checking.getCachedBalance());

        try {
            andy.deposit(15.555, maxi);
        }catch (Exception exc){
        } assertTrue(maxi.getCachedBalance() == checking.getCachedBalance());

        try { // boundary
            andy.deposit(15.000, maxi);
        }catch (Exception exc){
        } assertTrue(maxi.getCachedBalance() == checking.getCachedBalance()+15);
    }
}

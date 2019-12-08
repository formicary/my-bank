package com.abc;

import com.abc.types.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests of the Customer class
 */
public class CustomerTest {

    @Test
    /**
     * Test customer statement generation
     */
    public void testCustomerStatementGeneration(){
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

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
    /**
     * Test opening one account
     */
    public void testOpeningOneAccounts(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    /**
     * Test opening two accounts
     */
    public void testOpeningTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    /**
     * Test opening three accounts
     */
    public void testOpeningThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

}

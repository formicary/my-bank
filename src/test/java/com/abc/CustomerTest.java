package com.abc;

import org.junit.Test;

import static com.abc.BankTest.DOUBLE_DELTA;
import static org.junit.Assert.*;

import static com.abc.Account.*;

public class CustomerTest {

    @Test
    public void testApp(){

        Customer henry;
        Account checkingAccount, savingsAccount, maxiSavingsAccount;

        henry = new Customer("Henry");
        checkingAccount = henry.openAccount(AccountType.CHECKING);
        savingsAccount = henry.openAccount(AccountType.SAVINGS);

        checkingAccount.deposit(1000d);
        savingsAccount.deposit(4000d);
        savingsAccount.withdraw(200d);

        assertEquals(2, henry.getNumberOfAccounts());
        assertEquals(checkingAccount.getInterestEarned() + savingsAccount.getInterestEarned(), henry.getTotalInterestEarned(), DOUBLE_DELTA);
        assertEquals("Statement for Henry\n"
                + "\n"
                + "Checking Account\n"
                + "  deposit $1,000.00\n"
                + "Total $1,000.00\n"
                + "\n"
                + "Savings Account\n"
                + "  deposit $4,000.00\n"
                + "  withdrawal $200.00\n"
                + "Total $3,800.00\n"
                + "\n"
                + "Total in all Accounts $4,800.00"
                , henry.printStatement());

        try {
            henry.transferBetweenAccounts(AccountType.SAVINGS, AccountType.MAXI_SAVINGS_I, 1500d);
            fail("Exception not thrown!");
        } catch (Exception e) {
            assertEquals("No SAVINGS or MAXI_SAVINGS_I account!", e.getMessage());
        }
        maxiSavingsAccount = henry.openAccount(AccountType.MAXI_SAVINGS_I);
        try {
            assertTrue(henry.transferBetweenAccounts(AccountType.SAVINGS, AccountType.MAXI_SAVINGS_I, 1500d));
            assertTrue(henry.transferBetweenAccounts(AccountType.CHECKING, AccountType.MAXI_SAVINGS_I,500d));
            assertFalse(henry.transferBetweenAccounts(AccountType.CHECKING, AccountType.SAVINGS,600d));
            assertEquals(1000-500, checkingAccount.getBalance(), DOUBLE_DELTA);
            assertEquals(3800-1500, savingsAccount.getBalance(), DOUBLE_DELTA);
            assertEquals(1500+500, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testMaxNumberOfAccounts(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(AccountType.SAVINGS);
        oscar.openAccount(AccountType.CHECKING);
        oscar.openAccount(AccountType.MAXI_SAVINGS_I);
        oscar.openAccount(AccountType.SAVINGS);
        oscar.openAccount(AccountType.MAXI_SAVINGS_II);
        assertEquals(AccountType.values().length, oscar.getNumberOfAccounts());
    }

}

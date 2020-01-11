package com.abc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.*;

public class CustomerTest {

    private Customer oscar;
    private Account savingsAccount;
    private Account checkingAccount;

    @Before
    public void setup(){
        oscar = new Customer("Oscar");
        savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);

    }

    //TODO: remove "test" prefixes
    @Test //Test customer statement generation
    public void testApp(){
        oscar.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
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
        oscar.openAccount(AccountFactory.createAccount(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(AccountFactory.createAccount(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void getNumberOfAccounts_NoAccounts(){
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void totalInterestEarned_CheckingAccount(){
        oscar.openAccount(checkingAccount);

    }

}

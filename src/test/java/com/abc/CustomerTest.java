package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    @Test
    public void testStatement(){
        Customer john = new Customer("John");

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        john.openAccount(checkingAccount);
        checkingAccount.deposit(100.0);
        john.openAccount(savingsAccount);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        //Testing by string verification ensures both deposits + withdrawals are listed correctly
        //and doesn't need a larger test case.
        assertEquals("Statement for John\n" +
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
                "Total In All Accounts $3,900.00", john.getStatement());
    }

    @Test
    public void testTransfer() {
        Customer john = new Customer("John");
        Account checking = new Account(Account.CHECKING);
        Account savings = new Account(Account.SAVINGS);

        john.openAccount(checking);
        john.openAccount(savings);
        checking.deposit(100);

        assertTrue(john.transferBetweenAccounts(checking, savings, 100));
        assertFalse(john.transferBetweenAccounts(checking, savings, 100));
        assertTrue(john.transferBetweenAccounts(savings, checking, 100));
    }

}

package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testDeposit(){
        Account savings = new Account(Account.SAVINGS);
        Customer oscar = new Customer("Oscar").openAccount(savings); // object reference is retained

        oscar.depositIntoAccount(0, 50.0);
        assertEquals(50.0, savings.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdraw(){
        Account savings = new Account(Account.SAVINGS);
        Customer oscar = new Customer("Oscar").openAccount(savings); // object reference is retained

        oscar.depositIntoAccount(0, 500.0);
        assertEquals(500.0, savings.sumTransactions(), DOUBLE_DELTA);

        oscar.withdrawFromAccount(0, 150.0);
        assertEquals(350.0, savings.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testTransfer(){
        Account savings = new Account(Account.SAVINGS);
        Account checking = new Account(Account.CHECKING);

        Customer oscar = new Customer("Oscar").openAccount(savings); // object reference is retained
        oscar.openAccount(checking); // object reference is retained
        assertEquals(2, oscar.getNumberOfAccounts());

        oscar.depositIntoAccount(0, 500.0);
        assertEquals(500.0, savings.sumTransactions(), DOUBLE_DELTA);

        oscar.depositIntoAccount(1, 75.0);
        assertEquals(75.0, checking.sumTransactions(), DOUBLE_DELTA);

        oscar.transferBetweenAccounts(0, 1);
        assertEquals(0.0, savings.sumTransactions(), DOUBLE_DELTA);
        assertEquals(575.0, checking.sumTransactions(), DOUBLE_DELTA);

    }

}

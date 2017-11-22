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
    public void oneAccountCanBeAdded(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void twoAccountsCanBeAdded(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void threeAccountsCanBeAdded() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void getTotalFundsRetrieveRightAmount() {
        Customer javier = new Customer("Javier");
        Account checkingAcc = new Account(Account.CHECKING);
        Account savingAcc = new Account(Account.SAVINGS);

        javier.openAccount(checkingAcc).openAccount(savingAcc);
        checkingAcc.deposit(450);
        savingAcc.deposit(50);

        assertEquals(500, javier.getTotalFunds(), DOUBLE_DELTA);

    }

    @Test
    public void bankTransferWorks() {
        Customer javier = new Customer("Javier");
        Account checkingAcc = new Account(Account.CHECKING);
        Account savingAcc = new Account(Account.SAVINGS);

        javier.openAccount(checkingAcc).openAccount(savingAcc);
        checkingAcc.deposit(200);

        javier.bankTransfer(50, checkingAcc, savingAcc);

        assertEquals(150, checkingAcc.sumTransactions(),DOUBLE_DELTA);
        assertEquals(50,savingAcc.sumTransactions(),DOUBLE_DELTA);
        assertEquals(200, javier.getTotalFunds(),DOUBLE_DELTA);
    }
}

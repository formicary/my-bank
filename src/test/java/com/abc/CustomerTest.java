package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

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

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test //Test Transfer function
    public void testTransfer(){
        Account checking = new Account(Account.CHECKING);
        Account savings = new Account(Account.SAVINGS);
        checking.deposit(100);
        Customer oscar = new Customer("Oscar")
                .openAccount(checking);
        oscar.openAccount(savings);
        oscar.transferMoney(checking,savings,80);

        assertEquals("Statement for Oscar\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $80.00\n" +
                "Total $20.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $80.00\n" +
                "Total $80.00\n" +
                "\n" +
                "Total In All Accounts $100.00", oscar.getStatement());
    }

    @Test //Test new interest rules
    public void testNewMaxi(){
        Account withdraw = new Account(Account.MAXI_SAVINGS);
        Account noWithdraw = new Account(Account.MAXI_SAVINGS);
        withdraw.deposit(1100);
        withdraw.withdraw(100);
        noWithdraw.deposit(100);

        assertEquals(1, withdraw.interestEarned(),0);
        assertEquals(5, noWithdraw.interestEarned(),0);
    }
}

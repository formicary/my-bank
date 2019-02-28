package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/*----------------------------------------------------------------------------- 
                            Tests for Customers
-----------------------------------------------------------------------------*/
public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

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
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransfers(){
        Customer oscar = new Customer("Oscar");

        CheckingAccount account1= new CheckingAccount();
        SavingsAccount account2= new SavingsAccount();
        oscar.openAccount(account1).openAccount(account2);

        account1.deposit(1000);
        account2.deposit(500);
        oscar.makeTransfer(account1, account2, 500);

        assertEquals(1000, account2.sumTransactions(), DOUBLE_DELTA);
        assertEquals(500, account1.sumTransactions(), DOUBLE_DELTA);
    }
}

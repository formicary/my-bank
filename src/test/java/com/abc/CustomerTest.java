package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry")
                .openAccount(checkingAccount).openAccount(savingsAccount);

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
    public void oneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void twoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount())
                .openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void threeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount())
                .openAccount(new CheckingAccount())
                .openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void twoSameAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new CheckingAccount())
                .openAccount(new CheckingAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void transfer() {
        Account savings = new SavingsAccount();
        Account checking = new CheckingAccount();

        checking.deposit(200);

        Customer jeff = new Customer("Jeff")
                .openAccount(savings)
                .openAccount(checking)
                .transfer(checking, savings, 200);

        assertEquals(200, savings.sumTransactions(), DOUBLE_DELTA);
    }
}

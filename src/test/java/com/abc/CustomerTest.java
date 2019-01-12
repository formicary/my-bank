package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount())
                .openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount())
                .openAccount(new CheckingAccount())
                .openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransferFunds(){
        Account savingsAccount = new SavingsAccount();
        Account checkingAccount = new CheckingAccount();

        Customer oscar = new Customer("Oscar")
                .openAccount(savingsAccount)
                .openAccount(checkingAccount);

        savingsAccount.deposit(1000.0);
        oscar.transferFunds(savingsAccount, checkingAccount, 100);

        assertEquals(900.0, savingsAccount.sumTransactions(), DOUBLE_DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsFail(){
        Account savingsAccount = new SavingsAccount();
        Account checkingAccount = new CheckingAccount();

        Customer oscar = new Customer("Oscar")
                .openAccount(checkingAccount);

        savingsAccount.deposit(1000.0);
        oscar.transferFunds(savingsAccount, checkingAccount, 100);


    }
}

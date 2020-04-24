package com.abc;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account" +
                " deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Saving Account" +
                " deposit $4,000.00\n" +
                " withdraw $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransferAmount() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingAccount();

        Customer john = new Customer("John").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(200.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.transferAmount(300.0, checkingAccount);
        assertEquals("500.0", String.valueOf(checkingAccount.getBalance()));
        assertEquals("3700.0", String.valueOf(savingsAccount.getBalance()));
    }
}

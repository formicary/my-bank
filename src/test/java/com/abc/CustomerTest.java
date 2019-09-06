package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        checkingAccount.deposit(200.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $200.00\n" +
                "Total in $300.00\n" +
                "Total out $0.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total in $4,000.00\n" +
                "Total out $200.00\n" +
                "\n" +
                "Total in all accounts $4,100.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());

    }

    @Test
    public void testTwoAccount() {
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
    public void testTransfer() {

        Customer bob = new Customer("Bob");
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        bob.openAccount(checkingAccount);
        bob.openAccount(savingsAccount);

        checkingAccount.deposit(200);
        bob.transfer(100, checkingAccount, savingsAccount);
        
        assertEquals("Statement for Bob\n" +
        "\n" +
        "Checking Account\n" +
        "  deposit $200.00\n" +
        "  withdrawal $100.00\n" +
        "Total in $200.00\n" +
        "Total out $100.00\n" +
        "\n" +
        "Savings Account\n" +
        "  deposit $100.00\n" +
        "Total in $100.00\n" +
        "Total out $0.00\n" +
        "\n" +
        "Total in all accounts $200.00", bob.getStatement());
    }
}

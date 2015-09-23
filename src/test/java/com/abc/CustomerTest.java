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

    // Test one account
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    // Test multiple accounts
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    // Test interest
    @Test
    public void testInterest() {
        Customer oscar = new Customer("Oscar");
        Account savingsAccount = new SavingsAccount();
        oscar.openAccount(savingsAccount);
        savingsAccount.deposit(1000.0);

        assertEquals(1.0, oscar.totalInterestEarned(), DOUBLE_DELTA);
    }

    // Test interest for multiple accounts
    @Test
    public void testInterest2() {
        Customer oscar = new Customer("Oscar");
        Account savingsAccount = new SavingsAccount();
        oscar.openAccount(savingsAccount);
        savingsAccount.deposit(1000.0);
        Account checkingAccount = new CheckingAccount();
        oscar.openAccount(checkingAccount);
        checkingAccount.deposit(1000.0);

        assertEquals(2.0, oscar.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test //Test customer transfer between accounts
    public void testTransfer(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(3000.0);
        henry.transfer(savingsAccount, checkingAccount, 1000.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $1,000.00\n" +
                "Total $2,000.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $3,000.00\n" +
                "  withdrawal $1,000.00\n" +
                "Total $2,000.00\n" +
                "\n" +
                "Total In All Accounts $4,000.00", henry.getStatement());
    }

    //Test invalid transfer
    @Ignore
    public void testTransferInvalid(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(3000.0);
        henry.transfer(savingsAccount, checkingAccount, 4000.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $3,000.00\n" +
                "Total $3,000.00\n" +
                "\n" +
                "Total In All Accounts $4,000.00", henry.getStatement());
    }


}
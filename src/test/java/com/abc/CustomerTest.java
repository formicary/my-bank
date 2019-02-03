package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        // included test cases for other 2 account types
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Account superSavingsAccount = new Account(Account.SUPER_SAVINGS);


        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount)
                .openAccount(maxiSavingsAccount).openAccount(superSavingsAccount); // added in 2 new accounts

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiSavingsAccount.deposit(3000.0); // Deposit for Maxi Savings account
        maxiSavingsAccount.withdraw(100.0); // Withdraw for Maxi Savings account
        superSavingsAccount.deposit(150.0); // Deposit for Super Savings account
        superSavingsAccount.withdraw(20.0); // Withdraw for Super Savings account

        // Altered expected return statement
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
                "Maxi Savings Account\n" +
                "  deposit $3,000.00\n" +
                "  withdrawal $100.00\n" +
                "Total $2,900.00\n" +
                "\n" +
                "Super Savings Account\n" +
                "  deposit $150.00\n" +
                "  withdrawal $20.00\n" +
                "Total $130.00\n" +
                "\n" +
                "Total In All Accounts $6,930.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){ // corrected test spelling
        Customer oscar = new Customer("Oscar") .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test // removed ignore annotation
    public void testThreeAccounts() { // corrected test spelling
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS)); // added in third account, checking for Maxi Savings Account
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test // created new test to test for 4 accounts (newly added Super Savings account
    public void testFourAccounts() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        oscar.openAccount(new Account(Account.SUPER_SAVINGS));
        assertEquals(4, oscar.getNumberOfAccounts());
    }

    // Test for each account type to ensure exception is thrown when withdrawing too little (0 or less than 0)
    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestA() {

        Account checkingAccount = new Account(Account.CHECKING);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);

        checkingAccount.withdraw(-100.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestB() {
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(savingsAccount);

        savingsAccount.withdraw(-100.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestC() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount);

        maxiSavingsAccount.withdraw(0.0);

    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestD() {
        Account superSavingsAccount = new Account(Account.SUPER_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(superSavingsAccount);

        superSavingsAccount.withdraw(-6000.0);
    }
    // Test for each account type to ensure exception is thrown when withdrawing too much (more than account balance)
    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestE() {

        Account checkingAccount = new Account(Account.CHECKING);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);

        checkingAccount.withdraw(100.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestF() {
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(savingsAccount);

        savingsAccount.withdraw(100.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestG() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount);

        maxiSavingsAccount.withdraw(3000.0);

    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawalTestH() {
        Account superSavingsAccount = new Account(Account.SUPER_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(superSavingsAccount);

        superSavingsAccount.withdraw(1.0);
    }

    // Test for each account type to ensure exception is thrown when depositing too little (0 or less than 0)
    @Test (expected = IllegalArgumentException.class)
    public void depositTestA() {

        Account checkingAccount = new Account(Account.CHECKING);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);

        checkingAccount.deposit(-100.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void depositTestB() {
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(savingsAccount);

        savingsAccount.deposit(-200.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void depositTestC() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount);

        maxiSavingsAccount.deposit(-3000.0);

    }

    @Test (expected = IllegalArgumentException.class)
    public void depositTestD() {
        Account superSavingsAccount = new Account(Account.SUPER_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(superSavingsAccount);

        superSavingsAccount.deposit(0.0);
    }

    // Test new transfer method and accountPreset method
    @Test
    public void transferTest() {
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);

        checkingAccount.transfer(henry, 55.55, checkingAccount, savingsAccount);

        assertEquals(savingsAccount.getBalance(), 55.55, 0.00000000000001);
    }

    @Test
    public void transferTestInsufficientFunds() throws IllegalArgumentException {
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);

        checkingAccount.transfer(henry, 500, checkingAccount, savingsAccount);
    }

    @Test (expected = IllegalArgumentException.class)
    public void transferTestMissingAccount() {
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);

        checkingAccount.deposit(100.0);

        checkingAccount.transfer(henry, 55.55, checkingAccount, savingsAccount);
    }
}

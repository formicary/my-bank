package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testStatement(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account checkingAccount2 = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);
        henry.openAccount(checkingAccount2);
        henry.openAccount(savingsAccount);

        try {
            checkingAccount.deposit(100.0);
            checkingAccount2.deposit(100.0);
            checkingAccount2.withdraw(100.0);
            savingsAccount.deposit(4000.0);
            savingsAccount.withdraw(200.0);
        } catch (Exception e) {
        	e.toString();
        }


        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account:\n" +
                "  Deposit of $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Checking Account:\n" +
                "  Deposit of $100.00\n" +
                "  Withdrawal of $100.00\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account:\n" +
                "  Deposit of $4,000.00\n" +
                "  Withdrawal of $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testStatement_NoAccounts(){
        Customer henry = new Customer("Henry");

        assertEquals("Henry has no open accounts", henry.getStatement());
    }
    
    @Test //Test that the right exception is caught and correct message is provided
    public void testDeposit_ltOne(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);

        String message = "";
        try {
            checkingAccount.deposit(-100.0);
        } catch (Exception e) {
        	message = e.toString();
        }

        assertEquals("java.lang.IllegalArgumentException: Amount must be greater than zero.", message);
    }

    @Test //Test that the right exception is caught and correct message is provided
    public void testWithdrawal_ltOne(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);

        String message = "";
        try {
        	checkingAccount.deposit(100.0);
            checkingAccount.withdraw(-100.0);
        } catch (Exception e) {
        	message = e.toString();
        }

        assertEquals("java.lang.IllegalArgumentException: Amount must be greater than zero.", message);
    }

    @Test //Test that the right exception is caught and correct message is provided
    public void testWithdrawal_InsufficientFunds(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);

        String message = "";
        try {
        	checkingAccount.deposit(100.0);
            checkingAccount.withdraw(200.0);
        } catch (Exception e) {
        	message = e.toString();
        }

        assertEquals("java.lang.IllegalArgumentException: Insufficient funds in account!", message);
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testFourAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        oscar.openAccount(new Account(Account.AccountType.SUPER_SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.MAXI_SAVINGS));

        assertEquals(4, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransfer(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        
        Customer george = new Customer("George");
        george.openAccount(checkingAccount);
        george.openAccount(maxiSavingsAccount);

        try {
            checkingAccount.deposit(1000.0);
            maxiSavingsAccount.deposit(500.0);
            george.transfer(checkingAccount, maxiSavingsAccount, 500.0);
        } catch (Exception e) {
        	e.toString();
        }

        //It is not good practice to have multiple asserts in one unit test for reasons
        //revolving around being sure your test fails due to a single clear reason
        //However, it seems redundant to have the same unit test twice in order to
        //check each account separately
        assertEquals(500.0, checkingAccount.getAccountTotal(), DOUBLE_DELTA);
        assertEquals(1000.0, maxiSavingsAccount.getAccountTotal(), DOUBLE_DELTA);
    }

    @Test //Test that the right exception is caught and correct message is provided
    public void testTransfer_InvalidAccount(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        
        Customer george = new Customer("George");
        george.openAccount(checkingAccount);

        String message = "";
        try {
            checkingAccount.deposit(1000.0);
            george.transfer(checkingAccount, maxiSavingsAccount, 500.0);
        } catch (Exception e) {
        	message = e.toString();
        }

        assertEquals("java.lang.IllegalArgumentException: One or more of the accounts specified do not exist for this customer.", message);
    }

    @Test //Test that the right exception is caught and correct message is provided
    public void testTransfer_ltOne(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        
        Customer george = new Customer("George");
        george.openAccount(checkingAccount);
        george.openAccount(maxiSavingsAccount);

        String message = "";
        try {
            checkingAccount.deposit(1000.0);
            maxiSavingsAccount.deposit(500.0);
            george.transfer(checkingAccount, maxiSavingsAccount, -100.0);
        } catch (Exception e) {
        	message = e.toString();
        }

        assertEquals("java.lang.IllegalArgumentException: Amount must be greater than zero.", message);
    }

    @Test //Test that the right exception is caught and correct message is provided
    public void testTransfer_InsufficientFunds(){
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        
        Customer george = new Customer("George");
        george.openAccount(checkingAccount);
        george.openAccount(maxiSavingsAccount);

        String message = "";
        try {
            checkingAccount.deposit(500.0);
            maxiSavingsAccount.deposit(500.0);
            george.transfer(checkingAccount, maxiSavingsAccount, 1000.0);
        } catch (Exception e) {
        	message = e.toString();
        }

        assertEquals("java.lang.IllegalArgumentException: Insufficient funds in account!", message);
    }

}

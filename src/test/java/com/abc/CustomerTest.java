package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry");

        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

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

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    //new after this
    @Test
    public void testTransfer(){
        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);
        savingsAccount.deposit(500);
        checkingAccount.deposit(100);
        oscar.transferMoney(200,savingsAccount,checkingAccount);
        assertEquals("Statement for Oscar\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $200.00\n" +
                "Total $300.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $500.00\n" +
                "  withdrawal $200.00\n" +
                "Total $300.00\n" +
                "\n" +
                "Total In All Accounts $600.00", oscar.getStatement());

    }

    @Test
    public void testAccountSavings (){
        Customer johnny = new Customer("Johnny");
        Account savingaccount = new Account(Account.SAVINGS);
        Account maxisavingaccount = new Account(Account.MAXI_SAVINGS);
        johnny.openAccount(maxisavingaccount);
        johnny.openAccount(savingaccount);
        savingaccount.deposit(200);
        savingaccount.withdraw(100);
        savingaccount.deposit(3000);
        maxisavingaccount.deposit(5000);
        assertEquals(2, johnny.getNumberOfAccounts());
        assertEquals(255.2,johnny.totalInterestEarned(),0);


    }
}
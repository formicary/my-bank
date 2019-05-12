/*Edited by: Foyaz Hasnath*/
package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    //test deposit
    public void testDeposit(){
        //Create an account for customer "Jack"
        Customer jack = new Customer("Jack");
        Account checkingAccount = new Account(Account.CHECKING);
        jack.openAccount(checkingAccount);
        //deposit
        checkingAccount.deposit(100.0);

        //check deposit transaction recorded
        assertEquals("CHECKING: 100.0\n" +
                              "SAVINGS:\n" +
                              "MAXI_SAVINGS:",
                               jack.getTransactions()
        );
    }
    @Test
    //test withdrawal
    public void testWithdrawal(){
        //Create an account for customer "Harry"
        Customer harry = new Customer("Harry");
        Account checkingAccount = new Account(Account.CHECKING);
        harry.openAccount(checkingAccount);
        //deposit
        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(100.0);

        //check deposit then withdrawal transactions recorded
        assertEquals("CHECKING: 100.0,-100.0\n" +
                              "SAVINGS:\n" +
                              "MAXI_SAVINGS:",
                               harry.getTransactions()
        );
    }

    @Test
    //test transfer from checking -> savings for customer "John"
    public void testTransfer(){
        //Create an account for customer "John"
        Customer john = new Customer("John");
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        john.openAccount(checkingAccount);
        john.openAccount(savingsAccount);
        //deposit
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(100.0);
        //transfer from checking -> savings
        checkingAccount.transfer(savingsAccount,50.0);

        //check transfer transactions recorded
        assertEquals("CHECKING: 100.0,-50.0\n" +
                              "SAVINGS: 100.0,50.0\n" +
                              "MAXI_SAVINGS:",
                               john.getTransactions()
        );
    }

    @Test
    //check all transactions for each account for customer "Jack"
    public void testViewCustomerTransactions(){
        //Create an account for customer "Jack"
        Customer jack = new Customer("Jack");
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        jack.openAccount(checkingAccount);
        jack.openAccount(savingsAccount);

        //deposit and withdraw from accounts
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(1000.0);
        savingsAccount.withdraw(200.0);

        //check summary
        assertEquals("CHECKING: 100.0\n" +
                              "SAVINGS: 1000.0,-200.0\n" +
                              "MAXI_SAVINGS:",
                              jack.getTransactions()
        );
    }




}

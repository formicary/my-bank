package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {
/** This test case tests the customer class
 * Specifically, its ability to create an account of each account type is tested
 * Furthermore, the statement printed out are checked for accuracy.
 * Finally the application ability to store more than one account to an user is 
 * checked
*/ 
    @Test //Test customer statement generation
    public void testApp(){
        // An example arrayList of transaction has been created 
        //in order to construct accounts.
        ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
        Transaction t,t1,t2;
        t = new Transaction(25, Calendar.getInstance().getTime());
        assertTrue(t instanceof Transaction);
        t1 = new Transaction(-7, Calendar.getInstance().getTime());
        assertTrue(t1 instanceof Transaction);
        t2 = new Transaction(-12, Calendar.getInstance().getTime());
        assertTrue(t2 instanceof Transaction);
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        
        Account checkingAccount = new Account(Account.CHECKING, testTransactions);
        Account savingsAccount = new Account(Account.SAVINGS, testTransactions);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0, Calendar.getInstance().getTime());      
        savingsAccount.deposit(4000.0, Calendar.getInstance().getTime());
        savingsAccount.withdraw(200,Calendar.getInstance().getTime() );
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

    @Test // Test the ability to create one account
    public void testOneAccount(){
        ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
        Transaction t,t1,t2;
        t = new Transaction(12, Calendar.getInstance().getTime());
        t1 = new Transaction(-9, Calendar.getInstance().getTime());
        t2 = new Transaction(-3, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS,testTransactions));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test//tests the capability of the program to assing two accounts to one user.
    public void testTwoAccount(){
        ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
        Transaction t,t1,t2;
        t = new Transaction(12, Calendar.getInstance().getTime());
        t1 = new Transaction(-9, Calendar.getInstance().getTime());
        t2 = new Transaction(-3, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS, testTransactions));
        oscar.openAccount(new Account(Account.CHECKING,testTransactions));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test//test the capability of the program to assingn 3 accounts to a single user.
    public void testThreeAcounts() {
        ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
        Transaction t,t1,t2;
        t = new Transaction(12, Calendar.getInstance().getTime());
        t1 = new Transaction(-9, Calendar.getInstance().getTime());
        t2 = new Transaction(-3, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS,testTransactions));
        oscar.openAccount(new Account(Account.CHECKING,testTransactions));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS, testTransactions));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    @Test // checks the ability of a customer to transfer between two accounts.
    public void test_transferring_between_accounts(){
        ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
        Transaction t,t1,t2;
        t = new Transaction(12, Calendar.getInstance().getTime());
        t1 = new Transaction(-9, Calendar.getInstance().getTime());
        t2 = new Transaction(-3, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Account savingsAccount = new Account(Account.SAVINGS, testTransactions);
        Account checkingAccount = new Account(Account.CHECKING, testTransactions);
        Customer oscar = new Customer("Oscar")
                .openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        savingsAccount.deposit(100, Calendar.getInstance().getTime());
        checkingAccount.deposit(100,Calendar.getInstance().getTime());
        /** the transfer between accounts method bas been called which will 
         * transfer the 100 deposited to the savings account into the checking
         * account, thus leaving the saving account with 0 and the checking with 200
         */
        oscar.transferBetweenAccounts(savingsAccount,checkingAccount,100);
        assertEquals(200, checkingAccount.sumTransactions(), 1);
        assertEquals(0,savingsAccount.sumTransactions(),1);
        
    }
}

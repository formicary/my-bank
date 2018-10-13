package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;
	Factory cf = new CustomerFactory();
	Factory af = new AccountFactory();

    @Test //Test customer statement generation
    public void testApp(){
    	Customer henry = (Customer) cf.create("Henry", DateProvider.getInstance().now());
    	
        Account checkingAccount = (Account) af.create(henry, AccountEnum.CHECKINGACCOUNT);
        Account savingsAccount = (Account) af.create(henry, AccountEnum.SAVINGSACCOUNT);
        henry.addAccount(checkingAccount);
        henry.addAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "CHECKING ACCOUNT\n" +
                "Deposit: $100.00\n" +
                "Total: $100.00\n" +
                "\n" +
                "SAVINGS ACCOUNT\n" +
                "Deposit: $4,000.00\n" +
                "Withdraw: $200.00\n" +
                "Total: $3,800.00\n" +
                "\n" +
                "Total In All Accounts: $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = (Customer)cf.create("Oscar", DateProvider.getInstance().now());
        Account savingsAccount = (Account)af.create(oscar, AccountEnum.SAVINGSACCOUNT);
        oscar.addAccount(savingsAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = (Customer)cf.create("Oscar", DateProvider.getInstance().now());
        Account savingsAccount = (Account) af.create(oscar, AccountEnum.SAVINGSACCOUNT);
        Account checkingAccount = (Account) af.create(oscar, AccountEnum.CHECKINGACCOUNT);
        oscar.addAccount(savingsAccount);
        oscar.addAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
    	Customer oscar = (Customer)cf.create("Oscar", DateProvider.getInstance().now());
        Account savingsAccount = (Account) af.create(oscar, AccountEnum.SAVINGSACCOUNT);
        Account checkingAccount = (Account) af.create(oscar, AccountEnum.CHECKINGACCOUNT);
        Account maxiSavingAccount = (Account) af.create(oscar, AccountEnum.MAXISAVINGACCOUNT);
        oscar.addAccount(savingsAccount);
        oscar.addAccount(checkingAccount);
        oscar.addAccount(maxiSavingAccount);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void totalInterestEarned() {
    	Customer oscar = (Customer)cf.create("Oscar", DateProvider.getInstance().now());
        Account savingsAccount = (Account) af.create(oscar, AccountEnum.SAVINGSACCOUNT);
        Account checkingAccount = (Account) af.create(oscar, AccountEnum.CHECKINGACCOUNT);
        Account maxiSavingAccount = (Account) af.create(oscar, AccountEnum.MAXISAVINGACCOUNT);
        
        oscar.addAccount(savingsAccount);
        oscar.addAccount(checkingAccount);
        oscar.addAccount(maxiSavingAccount);
        
        savingsAccount.deposit(1000);
        checkingAccount.deposit(2500);
        maxiSavingAccount.deposit(500);
        
        assertEquals(28.5,oscar.totalInterestEarned(),DOUBLE_DELTA);
    }
}

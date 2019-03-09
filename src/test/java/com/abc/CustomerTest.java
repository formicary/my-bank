package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;

public class CustomerTest {
	
    @Test //Test customer statement generation
    public void testApp(){

    	Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        ICustomer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        checkingAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        savingsAccount.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        savingsAccount.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        
        
        String correctStatement = "Statement for Henry\n";
        correctStatement += "\nAccountNo.1 - Checking Account\n  deposit $100.00 - 01/01/2019\nTotal $100.00\n";
        correctStatement += "\nAccountNo.2 - Savings Account\n  deposit $4,000.00 - 01/01/2019\n  withdrawal $200.00 - 03/01/2019\nTotal $3,800.00\n";
        correctStatement += "\nTotal In All Accounts $3,900.00";
        assertEquals(correctStatement, henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        ICustomer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        ICustomer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        ICustomer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void getname(){
        ICustomer oscar = new Customer("Oscar");
        assertEquals("Oscar", oscar.getName());
    }
    
    @Test
    public void transfermoney(){
    	ICustomer oscar = new Customer("Oscar");
        Account account1 = new CheckingAccount();
        Account account2 = new SavingsAccount();
        oscar.openAccount(account1);
        oscar.openAccount(account2);
        account1.deposit(2000);
        account2.deposit(1000);
        account1.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        account2.getTransactions().get(0).setTransactionDate(2019,1,1,0,0,0);
        
        oscar.internalTransfer(1,2,1500);
        account1.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        account2.getTransactions().get(1).setTransactionDate(2019,1,3,0,0,0);
        
        String correctStatement = "Statement for Oscar\n";
        correctStatement += "\nAccountNo.1 - Checking Account\n  deposit $2,000.00 - 01/01/2019\n  withdrawal $1,500.00 - 03/01/2019\nTotal $500.00\n";
        correctStatement += "\nAccountNo.2 - Savings Account\n  deposit $1,000.00 - 01/01/2019\n  deposit $1,500.00 - 03/01/2019\nTotal $2,500.00\n";
        correctStatement += "\nTotal In All Accounts $3,000.00";
        assertEquals(correctStatement, oscar.getStatement());
    }
    
    
}

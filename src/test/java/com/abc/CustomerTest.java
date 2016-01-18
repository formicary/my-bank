package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test //Test customer statement generation
    public void testApp(){

    	CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();

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

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }


    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    } 
    
    // Checking whether source account updates account balance after withdrawal
    @Test
    public void transferBalanceWithdrawal()
    {
    	Customer jack = new Customer("Jack");
    	CheckingAccount sourceAccount = new CheckingAccount();
    	SavingsAccount destinationAccount = new SavingsAccount();
    	
    	jack.openAccount(sourceAccount);
    	jack.openAccount(destinationAccount);
       
    	sourceAccount.deposit(300.0);
    	destinationAccount.deposit(500.0);
        
        jack.transferBalance(sourceAccount, destinationAccount, 200.0);
        assertEquals(100.0, sourceAccount.totalBalance,DOUBLE_DELTA);
    }
    
    /* Checking whether destination account updates account balance 
    after account is added with new funds */
    @Test
    public void transferBalanceDeposit()
    {
    	Customer jack = new Customer("Jack");
    	CheckingAccount sourceAccount = new CheckingAccount();
    	SavingsAccount destinationAccount = new SavingsAccount();
    	
    	jack.openAccount(sourceAccount);
    	jack.openAccount(destinationAccount);
       
    	sourceAccount.deposit(300.0);
    	destinationAccount.deposit(500.0);
        
        jack.transferBalance(sourceAccount, destinationAccount, 200.0);
        assertEquals(700.0, destinationAccount.totalBalance,DOUBLE_DELTA);
    }
    
    @Test (expected = Exception.class)
    public void transferBalanceSameAccount()
    {
    	Customer jack = new Customer("Jack");
    	CheckingAccount sourceAccount = new CheckingAccount();
    	jack.openAccount(sourceAccount);
    	sourceAccount.deposit(300.0);    	
    	jack.transferBalance(sourceAccount, sourceAccount, 200.0);
    }
    
}

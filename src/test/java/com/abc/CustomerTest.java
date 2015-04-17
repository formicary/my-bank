package mybank;


import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

  //  @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

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
    
    public void testTranfers(){
    	//new Customer Tracy opened out three account,0,1,2
    	Customer Tracy = new Customer("Tracy").openAccount(new Account(AccountType.CHECKING)).openAccount(new Account(AccountType.SAVINGS)).
    			openAccount(new Account(AccountType.MAXI_SAVINGS));
    	Tracy.getAccount().get(0).deposit(500.0);
    	Tracy.getAccount().get(1).deposit(1500);
    	Tracy.getAccount().get(2).deposit(3300);
    	
    	//Method transfersBetweenAccount (double amount, Account sendingAccount, Account recevingAccount)
    	Tracy.transfersBetweenAccount(100, new Account(AccountType.CHECKING), new Account(AccountType.SAVINGS) );
    	double expectResultChecking = 500-100;
    	double ResultChecking = Tracy.getAccount().get(0).sumTransactions();
    	assertEquals(expectResultChecking, ResultChecking);
    	
    	
    	double expectResultSaving = 1500+100;
    	double resultSaving = Tracy.getAccount().get(1).sumTransactions();
    	
    	assertEquals(expectResultSaving, resultSaving);
    	
    	
    	Tracy.transfersBetweenAccount(323, new Account(AccountType.MAXI_SAVINGS), new Account(AccountType.SAVINGS));
    	double expectResultMaxSaving = 3300-323;
    	double ResultMaxSaving = Tracy.getAccount().get(3).sumTransactions();
    	assertEquals(expectResultMaxSaving, ResultMaxSaving);
    	
    	
    	double expectResultSaving2 = 1500+323;
    	double resultSaving2 = Tracy.getAccount().get(1).sumTransactions();
    	
    	assertEquals(expectResultSaving2, resultSaving2);
    	
    }

   // @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

  //  @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

   // @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
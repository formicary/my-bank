package test.java.com.abc;

import org.junit.Test;

import main.java.com.abc.Account;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import main.java.com.abc.Transaction;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    //Below tests should ideally be moved to new AccountTest.java test file
    
    
    //Attempt to deposit a negative amount into a checking account
    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeAmount(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	checking.deposit(-1000);
    }
    
    //Attempt to withdraw a negative amount from a checking account
    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeAmount(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	checking.withdraw(-1000);
    }
    
    //Attempt to withdraw an amount greater than is available from a checking account
    @Test(expected = IllegalArgumentException.class)
    public void withdrawGreaterAmount(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	checking.deposit(-1e10);
    }
    
	@Test
    public void depositAmount(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	checking.deposit(500);
    	
    	assertEquals(500, checking.sumTransactions());
    }
	
	@Test
    public void withdrawAmount(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	//Ensure balance is greater than 0
    	checking.deposit(500);
    	checking.withdraw(100);
    	
    	assertEquals(400, checking.sumTransactions());
    }
	
	
}

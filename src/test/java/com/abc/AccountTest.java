package test.java.com.abc;

import static org.junit.Assert.assertEquals;
import main.java.com.abc.Account;
import org.junit.Test;

public class AccountTest{
    
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

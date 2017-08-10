import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Banking.AccountType;
import Banking.CheckingAccount;
import Banking.MaxiSavingAccount;
import Banking.SavingAccount;

public class AccountTest {

	@Test
	public void canMakeDeposit(){
		CheckingAccount accountCheck = new CheckingAccount();
		SavingAccount accountSave = new SavingAccount();
		MaxiSavingAccount accountMaxSave = new MaxiSavingAccount();
		
		double checkAmount = 5, saveAmount = 200.5, maxSaveAmount = 90.001;
		
		accountCheck.deposit(checkAmount);
		accountSave.deposit(saveAmount);
		accountMaxSave.deposit(maxSaveAmount);
		
		assertEquals(accountCheck.sumTransactions(),checkAmount,0);
		assertEquals(accountSave.sumTransactions(),saveAmount,0);
		assertEquals(accountMaxSave.sumTransactions(),maxSaveAmount,0);	
	}
	
	@Test (expected = Exception.class)
	public void cantMakeDeposit(){
		CheckingAccount accountCheck = new CheckingAccount();
		SavingAccount accountSave = new SavingAccount();
		MaxiSavingAccount accountMaxSave = new MaxiSavingAccount();
		
		double checkAmount = -5, saveAmount = -200.5, maxSaveAmount = -0;
		
		accountCheck.deposit(checkAmount);
		accountSave.deposit(saveAmount);
		accountMaxSave.deposit(maxSaveAmount);
		
		assertEquals(accountCheck.sumTransactions(),0,0);
		assertEquals(accountSave.sumTransactions(),0,0);
		assertEquals(accountMaxSave.sumTransactions(),0,0);	
	}
	
	@Test
	public void canMakeWithdrawal(){
		CheckingAccount accountCheck = new CheckingAccount();
		SavingAccount accountSave = new SavingAccount();
		MaxiSavingAccount accountMaxSave = new MaxiSavingAccount();
		
		double checkAmount = 25, saveAmount = 260.5, maxSaveAmount = 190.001;
		
		accountCheck.withdraw(checkAmount);
		accountSave.withdraw(saveAmount);
		accountMaxSave.withdraw(maxSaveAmount);
		
		assertEquals(accountCheck.sumTransactions(),-checkAmount,0);
		assertEquals(accountSave.sumTransactions(),-saveAmount,0);
		assertEquals(accountMaxSave.sumTransactions(),-maxSaveAmount,0);
	}
	
	@Test	(expected = Exception.class)
	public void cantMakeWithdrawal(){
		CheckingAccount accountCheck = new CheckingAccount();
		SavingAccount accountSave = new SavingAccount();
		MaxiSavingAccount accountMaxSave = new MaxiSavingAccount();
		
		double checkAmount = -25, saveAmount = -260.5, maxSaveAmount = -190.001;
		
		accountCheck.withdraw(checkAmount);
		accountSave.withdraw(saveAmount);
		accountMaxSave.withdraw(maxSaveAmount);
		
		assertEquals(accountCheck.sumTransactions(),-checkAmount,0);
		assertEquals(accountSave.sumTransactions(),-saveAmount,0);
		assertEquals(accountMaxSave.sumTransactions(),-maxSaveAmount,0);
	}
	
	@Test
	public void canSumTransactions(){
		CheckingAccount accountCheck = new CheckingAccount();
		SavingAccount accountSave = new SavingAccount();
		MaxiSavingAccount accountMaxSave = new MaxiSavingAccount();
		
		
		accountCheck.deposit(60);
		accountCheck.withdraw(25);
		accountCheck.withdraw(5);
		
		accountSave.deposit(5);
		accountSave.withdraw(5);
		accountSave.deposit(20);
		
		accountMaxSave.deposit(120);
		accountMaxSave.deposit(120);
		accountMaxSave.withdraw(40);
		
		assertEquals(accountCheck.sumTransactions(),30,0);
		assertEquals(accountSave.sumTransactions(),20,0);
		assertEquals(accountMaxSave.sumTransactions(),200,0);
	}
	
	@Test
	public void canGetAccountType(){
		CheckingAccount accountCheck = new CheckingAccount();
		SavingAccount accountSave = new SavingAccount();
		MaxiSavingAccount accountMaxSave = new MaxiSavingAccount();
		
		assertEquals(accountCheck.getAccountType(),AccountType.CHECKING);
		assertEquals(accountSave.getAccountType(),AccountType.SAVING);
		assertEquals(accountMaxSave.getAccountType(),AccountType.MAXI_SAVINGS);
	}
		
}

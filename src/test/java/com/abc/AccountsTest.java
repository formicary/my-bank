package test.java.com.abc;

import static org.junit.Assert.*;
import main.java.com.abc.Accounts.AccountBase;
import main.java.com.abc.Accounts.AccountFactory;
import main.java.com.abc.Accounts.AccountType;
import main.java.com.abc.Bank.Bank;
import main.java.com.abc.Customer.Customer;

import org.junit.Test;

public class AccountsTest {
    private static final double DOUBLE_DELTA = 0.0001;
    
    @Test
    public void TransferTest() {
    	System.out.println("######################################################\n" + "TransferTest\n");

        Bank bank = new Bank();

        AccountBase AcheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);
        AccountBase BcheckingAccount = AccountFactory.CreateAccount(AccountType.Checking);
        AcheckingAccount.StopInterest();
        BcheckingAccount.StopInterest();
        
        Customer bill = new Customer("Bill");
        Customer bob = new Customer("Bob");

        bill.OpenAccount(AcheckingAccount);
        bob.OpenAccount(BcheckingAccount);

        bank.addCustomer(bill);
        bank.addCustomer(bob);

        AcheckingAccount.Deposit(10000.0);
        AcheckingAccount.Transfer(BcheckingAccount, 5000.0);
                
        
        assertEquals(AcheckingAccount.GetBalanceSafe(), 5000, DOUBLE_DELTA);
        assertEquals(BcheckingAccount.GetBalanceSafe(), 5000, DOUBLE_DELTA);


    }
    

    
    @Test
    public void CheckingInterestTest() {
    	System.out.println("######################################################\n" + "checking interest Test\n");

        Bank bank = new Bank();

        AccountBase checkingAccount = AccountFactory.CreateAccount(AccountType.Checking);
        Customer bill = new Customer("Bill");
        bill.OpenAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.Deposit(100.0);
        
        try {
			Thread.sleep( 1010 );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        assertEquals(0.10004987954705946, checkingAccount.GetInterestEarned(), DOUBLE_DELTA);
        bill.CloseAccount(checkingAccount);

        
    }
    
    @Test
    public void SavingInterestTest() {
    	System.out.println("######################################################\n" + "SavingInterestTest\n");

        Bank bank = new Bank();

        AccountBase SavingsAccount = AccountFactory.CreateAccount(AccountType.Savings);
        Customer bill = new Customer("Bill");
        bill.OpenAccount(SavingsAccount);
        bank.addCustomer(bill);
        
        SavingsAccount.Deposit(500.0);
        
        assertEquals(0.001, SavingsAccount.GetAnnualRate(), DOUBLE_DELTA);

        try {
			Thread.sleep( 1010 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        assertEquals(0.500249, SavingsAccount.GetInterestEarned(), DOUBLE_DELTA);
        
        //MAKE IT 1000 DOLLOAR in the account
        SavingsAccount.Deposit(500.0);
        assertEquals(0.002, SavingsAccount.GetAnnualRate(), DOUBLE_DELTA);

        try {
			Thread.sleep( 1010 );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals(2.503247, SavingsAccount.GetInterestEarned(), DOUBLE_DELTA);
        bill.CloseAccount(SavingsAccount);

    }
    
    @Test
    public void MaxiSavingsAccount() {
    	System.out.println("######################################################\n" + "MaxiSavingsAccount Test\n");

        Bank bank = new Bank();

        AccountBase MaxiAccount = AccountFactory.CreateAccount(AccountType.MaxiSavings);
        Customer bill = new Customer("Bill");
        bill.OpenAccount(MaxiAccount);
        bank.addCustomer(bill);
        MaxiAccount.Deposit(3000.0);

        assertEquals(0.001, MaxiAccount.GetAnnualRate(), DOUBLE_DELTA);


        
        try {
			Thread.sleep( 10010 );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        assertEquals(30.150460, MaxiAccount.GetInterestEarned(), DOUBLE_DELTA);
        
        assertEquals(0.05, MaxiAccount.GetAnnualRate(), DOUBLE_DELTA);

        try {
			Thread.sleep( 1010 );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        assertEquals(185.498688, MaxiAccount.GetInterestEarned(), DOUBLE_DELTA);
                
        bill.CloseAccount(MaxiAccount);

    }
}

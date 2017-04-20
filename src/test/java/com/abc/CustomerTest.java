package com.abc;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;

import com.abc.implementation.Account;
import com.abc.implementation.CheckingAccount;
import com.abc.implementation.Customer;
import com.abc.implementation.MaxiSavingsAccount;
import com.abc.implementation.RateHelper;
import com.abc.implementation.SavingsAccount;
import com.abc.implementation.TransactionHelper;
import com.abc.interfaces.IAccount;
import com.abc.interfaces.ICustomer;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test //Test customer statement generation
    public void getStatement(){

        Account checkingAccount = new CheckingAccount(new RateHelper(), new TransactionHelper());
        Account savingsAccount = new SavingsAccount(new RateHelper(), new TransactionHelper());

        ICustomer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

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
    public void totalInterestEarned()
    {
    	IAccount checkingAccount = mock(IAccount.class);
    	when(checkingAccount.compoundInterestEarned()).thenReturn(50.0);
    	IAccount savingsAccount = mock(IAccount.class);
    	when(savingsAccount.compoundInterestEarned()).thenReturn(120.0);
    	
    	ICustomer bill = new Customer("Bill");
    	bill.openAccount(checkingAccount).openAccount(savingsAccount);
    	
    	assertEquals(170, bill.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testOneAccount(){
        ICustomer oscar = new Customer("Oscar").openAccount(new SavingsAccount(new RateHelper(), new TransactionHelper()));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        ICustomer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount(new RateHelper(), new TransactionHelper()));
        oscar.openAccount(new CheckingAccount(new RateHelper(), new TransactionHelper()));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        ICustomer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount(new RateHelper(), new TransactionHelper()));
        oscar.openAccount(new CheckingAccount(new RateHelper(), new TransactionHelper()));
        oscar.openAccount(new MaxiSavingsAccount(new RateHelper(), new TransactionHelper()));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void TransferFundsSuccess()
    {
    	ICustomer oscar = new Customer("Oscar");
    	IAccount fromAccount = new CheckingAccount(null, null);
    	IAccount toAccount = new SavingsAccount(null, null);
    	
    	fromAccount.deposit(500);
    	
    	oscar.openAccount(toAccount);
    	oscar.openAccount(fromAccount);
    	
    	assertTrue(oscar.TransferFunds(fromAccount, toAccount, 100.0));
    	
    	assertEquals(400, fromAccount.getTotalBalance(), DOUBLE_DELTA);
    	assertEquals(100, toAccount.getTotalBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void TransferFundsFailInsufficientBalance()
    {
    	ICustomer oscar = new Customer("Oscar");
    	IAccount fromAccount = new CheckingAccount(null, null);
    	IAccount toAccount = new SavingsAccount(null, null);
    	
    	toAccount.deposit(500);
    	
    	oscar.openAccount(toAccount);
    	oscar.openAccount(fromAccount);
    	
    	assertFalse(oscar.TransferFunds(fromAccount, toAccount, 100.0));
    }
    
    @Test
    public void TransferFundsFailNegativeAmount()
    {
    	ICustomer oscar = new Customer("Oscar");
    	IAccount fromAccount = new CheckingAccount(null, null);
    	IAccount toAccount = new SavingsAccount(null, null);
    	
    	fromAccount.deposit(500);
    	
    	oscar.openAccount(toAccount);
    	oscar.openAccount(fromAccount);
    	
    	assertFalse(oscar.TransferFunds(fromAccount, toAccount, -100.0));
    }
}

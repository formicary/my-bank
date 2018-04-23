package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CustomerTest {
	
	@Test
    public void deposit() {
        
        Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        
        checkingAccount.deposit(100.0);

        BigDecimal hundred = new BigDecimal(100.0).setScale(2, BigDecimal.ROUND_HALF_UP);;
        assertEquals(hundred, checkingAccount.getBalance());
    }
    
    @Test
    public void withdraw() {
        
        Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        
        checkingAccount.withdraw(100.0);

        BigDecimal hundred = new BigDecimal(-100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(hundred, checkingAccount.getBalance());
    }
	
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Account savingsAccount = AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT);
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
    public void oneAccount(){
    	
        Customer oscar = new Customer("Oscar")
        		.openAccount(AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void twoAccount(){
    	
		Customer oscar = new Customer("Oscar")
				.openAccount(AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT))
				.openAccount(AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT));
		assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void threeAcounts() {
    	
		Customer oscar = new Customer("Oscar")
				.openAccount(AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT))
				.openAccount(AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT));
		assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void customerAccountsTransfer(){

    	Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Account savingsAccount = AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT);
        Customer thomas = new Customer("Thomas").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(300.0);
        savingsAccount.deposit(1500);
        
        thomas.makeTransfer(checkingAccount, savingsAccount, 100.0);
        
        assertEquals("Statement for Thomas\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $300.00\n" +
                "  withdrawal $100.00\n" +
                "Total $200.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,500.00\n" +
                "  deposit $100.00\n" +
                "Total $1,600.00\n" +
                "\n" +
                "Total In All Accounts $1,800.00", thomas.getStatement());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void wrongCustomerAccountsTransfer(){

    	Account thomasCheckingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Account thomasSavingsAccount = AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT);
        Customer thomas = new Customer("Thomas")
        		.openAccount(thomasCheckingAccount).openAccount(thomasSavingsAccount);
        
        Account henryCheckingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Customer henry = new Customer("Thomas").openAccount(henryCheckingAccount);

        thomasCheckingAccount.deposit(300.0);
        thomasSavingsAccount.deposit(1500);
        
        thomas.makeTransfer(henryCheckingAccount, thomasSavingsAccount, 100.0);
    }
    
    
    @Test 
    public void withdrawalInTenDays() throws ParseException {
    	  	
		Account maxiSavingsAccount = AccountFactory.getAccount(AccountType.MAXI_SAVINGS_ACCOUNT);
		Customer thomas = new Customer("Thomas").openAccount(maxiSavingsAccount);
		
		maxiSavingsAccount.withdraw(100);
		maxiSavingsAccount.withdraw(100);
		
		maxiSavingsAccount.lastWithdrawal = DateProvider.setDate(2018, 03, 2, 12, 30, 0);;
		maxiSavingsAccount.newestWithdrawal = DateProvider.setDate(2018, 03, 13, 12, 29, 59);
        
        assertTrue(maxiSavingsAccount.getWithdrawalGap() <= 10); 
    }
    
    @Test 
    public void testWithdrawalAfterTenDays() throws ParseException {
    	  	
		Account maxiSavingsAccount = AccountFactory.getAccount(AccountType.MAXI_SAVINGS_ACCOUNT);   
		Customer thomas = new Customer("Thomas").openAccount(maxiSavingsAccount);
		
		maxiSavingsAccount.withdraw(100);
		maxiSavingsAccount.withdraw(222);
		
		maxiSavingsAccount.lastWithdrawal = DateProvider.setDate(2018, 03, 2, 12, 30, 0);	
		maxiSavingsAccount.newestWithdrawal = DateProvider.setDate(2018, 03, 13, 12, 30, 0);
        
        assertTrue(maxiSavingsAccount.getWithdrawalGap() > 10); 
    }
    
    @Test
    public void maxiSavingChangeInterest() {
    	
    	Account maxiSavingsAccount = AccountFactory.getAccount(AccountType.MAXI_SAVINGS_ACCOUNT);
		Customer thomas = new Customer("Thomas").openAccount(maxiSavingsAccount);
		
		maxiSavingsAccount.deposit(1000);
		
		//interest 5% yearly
		BigDecimal fifty = new BigDecimal(50.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			assertEquals(fifty, maxiSavingsAccount.interestEarned());
	
		maxiSavingsAccount.deposit(200);
		maxiSavingsAccount.withdraw(100);
		maxiSavingsAccount.withdraw(100);
		
		maxiSavingsAccount.setLastWithdrawal(DateProvider.setDate(2018, 03, 5, 12, 30, 0)); 
		maxiSavingsAccount.setNewestWithdrawal(DateProvider.setDate(2018, 03, 10, 12, 29, 59));
		
		//interest rate change to 0.1% yearly
		BigDecimal one = new BigDecimal(1.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			assertEquals(one, maxiSavingsAccount.interestEarned());
		
		maxiSavingsAccount.deposit(200);
		maxiSavingsAccount.withdraw(100);
		maxiSavingsAccount.withdraw(100);
		
		maxiSavingsAccount.setLastWithdrawal(DateProvider.setDate(2018, 03, 13, 12, 29, 59));
		maxiSavingsAccount.setNewestWithdrawal(DateProvider.setDate(2018, 03, 30, 12, 29, 59));
		
			//back to 5% yearly
			assertEquals(fifty, maxiSavingsAccount.interestEarned());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testNegativeDeposit() {
    	
        Account savingsAccount = AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT);
        
        Customer thomas = new Customer("Thomas").openAccount(savingsAccount);

        savingsAccount.deposit(-100.0);
    }
}

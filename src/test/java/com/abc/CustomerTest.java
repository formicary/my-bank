package com.abc;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import com.abc.DateProvider;


public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

	
    @Test //Test customer statement generation
    public void getStatement_correctStatmentString(){
    	
    	LocalDateTime date =  DateProvider.getInstance().now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    	String today = date.format(formatter);
    	 
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        System.out.print(henry.getStatement());

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00 On " + today + "\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00 On " + today + "\n" +
                "  withdrawal $200.00 On " + today + "\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }
    
    
    @Test
    public void getAccountTypes_correctType() {
    	Account savingAccount = new Account(AccountType.SAVINGS);
    	Account checkingAccount = new Account(AccountType.CHECKING);
    	Account maxiAccount = new Account(AccountType.MAXI_SAVINGS);
    	
    	Customer janet = new Customer("Janet");
    	janet.openAccount(savingAccount);
    	janet.openAccount(checkingAccount);
    	janet.openAccount(maxiAccount);
    	
    	List<Account> accounts = janet.getAllAccounts();
    	
    	assertEquals("Savings Account", accounts.get(0).getAccountTypeString() );
    	assertEquals("Checking Account", accounts.get(1).getAccountTypeString() );
    	assertEquals("Maxi Savings Account", accounts.get(2).getAccountTypeString() );
    }

    
    @Test
    public void getNumberOfAccounts_oneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    
    @Test
    public void getNumberOfAccounts_twoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS))
        		.openAccount(new Account(AccountType.CHECKING));
        
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    
    @Test
    public void getNumberOfAccounts_threeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS))
        		.openAccount(new Account(AccountType.CHECKING))
        		.openAccount(new Account(AccountType.MAXI_SAVINGS));
        
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    
    @Test
    public void getAccount_customerAccount_returnCorrectAccount() {
    	Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.CHECKING));
    	oscar.openAccount(new Account(AccountType.SAVINGS));
    	
    	Account account = oscar.getAccount(AccountType.SAVINGS);
    	assertEquals("Savings Account", account.getAccountTypeString());
    	
    	account = oscar.getAccount(AccountType.CHECKING);
    	assertEquals("Checking Account", account.getAccountTypeString());

    	
    }
    
    
    @Test(expected = NoSuchElementException.class)
    public void getAccount_noAccount_exceptionThrown() {
    	Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.CHECKING));
    	oscar.openAccount(new Account(AccountType.SAVINGS));
    	
    	oscar.getAccount(AccountType.MAXI_SAVINGS);
    }
    
    
    @Test
    public void transferBetweenAccount_correctAccountBalance() {
    	Customer oscar = new Customer("Oscar")
    			.openAccount(new Account(AccountType.CHECKING))
    			.openAccount(new Account(AccountType.MAXI_SAVINGS));
    	
    	Account checkingAccount = oscar.getAccount(AccountType.CHECKING);
    	Account maxiAccount = oscar.getAccount(AccountType.MAXI_SAVINGS);
    	maxiAccount.deposit(5000);
    	oscar.transferBetweenAccount( checkingAccount, maxiAccount, 3000);
    	
    	assertEquals(3000, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(2000, maxiAccount.sumTransactions(), DOUBLE_DELTA);    	
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void transferBetweenAccount_notCustomerAccount_exceptionThrown() {
    	Customer oscar = new Customer("Oscar")
    			.openAccount(new Account(AccountType.CHECKING));
    	
    	Account checkingAccount = oscar.getAccount(AccountType.CHECKING);
    	Account maxiAccount = new Account(AccountType.MAXI_SAVINGS);
    	checkingAccount.deposit(5000);
    	
    	oscar.transferBetweenAccount( maxiAccount, checkingAccount, 3000); 	
    }
}

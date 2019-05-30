package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;

public class CustomerTest {
private static final TestOnlyCurrentTime testOnlyCurrentTime = TestOnlyCurrentTime.getInstance();
	
    @Before
    public void init() {
		Date date = Calendar.getInstance().getTime();
		testOnlyCurrentTime.setDate(date);
    }

    @Test //Test customer statement generation
    public void statementForAccount_TwoAccountsGiven_ShouldReturnStatement(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  interest $0.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  interest $0.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void getNumberOfAccounts_OneAccountGiven_ShouldReturnOne(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void getNumberOfAccounts_TwoAccountGiven_ShouldReturnTwo(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void getNumberOfAccounts_ThreeAccountGiven_ShouldReturnThree() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void getNumberOfAccounts_NoAccountGiven_ShouldReturnZero() {
        Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void openAccount_DuplicateAccountTypeGiven_ShouldAddTwoAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    
}

package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);
		henry.openAccount(savingsAccount);

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
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
	
	@Test
	public void testTransfer(){
		Account firstAccount = new Account(Account.AccountType.CHECKING);
		Account secondAccount = new Account(Account.AccountType.CHECKING);
		
		Customer jane = new Customer("Jane");
		jane.openAccount(firstAccount);
		jane.openAccount(secondAccount);
		
		firstAccount.deposit(300.0);
		firstAccount.transfer(150.0, secondAccount);
		
		assertEquals(150.0, firstAccount.getBalance(), DOUBLE_DELTA);
		assertEquals(150.0, secondAccount.getBalance(), DOUBLE_DELTA);
	}

	@Test
	public void testTransferBetweenTwoCustomers(){
		Account firstAccount = new Account(Account.AccountType.CHECKING);
		Account secondAccount = new Account(Account.AccountType.CHECKING);
		
		Customer john = new Customer("John");
		Customer jane = new Customer("Jane");

		john.openAccount(firstAccount);
		jane.openAccount(secondAccount);
		
		try{
			firstAccount.deposit(300.0);
			firstAccount.transfer(150.0, secondAccount);
			fail("No exception thrown");
		}catch (Exception e){
			assertEquals(e.getMessage(), "Accounts are owned by two different customers");
		}
	}
	
	@Test
	public void testCustomerOpenTakenAccount(){
		Account firstAccount = new Account(Account.AccountType.CHECKING);
		
		Customer john = new Customer("John");
		Customer jane = new Customer("Jane");
		
		john.openAccount(firstAccount);

		Customer result = jane.openAccount(firstAccount);
		
		assertEquals(result, null);
	}
}

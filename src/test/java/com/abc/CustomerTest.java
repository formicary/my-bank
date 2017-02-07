package com.abc;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;
    
	private Customer oscar;
	private Account checkingAccount;
	private Account savingsAccount;
	private Account maxiSavingsAccount;
	
	@Before
	public void setUp() throws Exception {
        checkingAccount = new Account(Account.CHECKING);
        savingsAccount = new Account(Account.SAVINGS);
        maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        oscar = new Customer("Oscar");
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	/**
	 * Test customer statement generation
	 */
	@Test
	public void testCustomerStatement() {
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        String statementExpected = "Statement for Oscar\n" +
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
                "Total In All Accounts $3,900.00";

        assertEquals(statementExpected, oscar.getStatement());
	}
	
	@Test
	public void depositFunds() {
		checkingAccount.deposit(100.0);
		assertEquals(100.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
	}
	
	@Test
	public void withdrawFunds() {
		checkingAccount.deposit(100.0);
		checkingAccount.withdraw(50.0);
		assertEquals(50.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
	}
	
	@Test
	public void transferFunds() {
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingsAccount = new Account(Account.SAVINGS);
		Customer sarah = new Customer("Sarah");
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(400.0);
		
		sarah.openAccount(checkingAccount);
		sarah.openAccount(savingsAccount);
        
		sarah.transfer(checkingAccount, savingsAccount, 40.0);
		assertEquals(60.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
	}
		
	/**
	 * Test opening one customer account
	 */
    @Test
    public void testOpenOneAccount() {
        oscar.openAccount(savingsAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /**
     * Test opening two customer accounts
     */
    @Test
    public void testOpenTwoAccounts(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    /**
     * Test opening three customer accounts
     * TODO ? creation of a third type of bank account
     */
    @Test
    public void testOpenThreeAcounts() {
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(maxiSavingsAccount);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
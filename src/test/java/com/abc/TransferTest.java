package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TransferTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private Bank bank;
	private Customer andrew;
	private Customer john;
	private CheckingAccount ca = new CheckingAccount();
	private SavingsAccount sa = new SavingsAccount();	
	private MaxiSavingsAccount ma = new MaxiSavingsAccount();

	@Before
	public void setUp() {
		bank = new Bank();
		andrew = new Customer("Andrew");
		john = new Customer("John");		
		ca.setAccountNo(1);
    	sa.setAccountNo(2);
    	ma.setAccountNo(3);		
	}
	/**
	 * Test the transfer of an amount from checking to savings
	 */
    @Test
    public void testTransferToSavingsAcount() {
    	andrew.openAccount(ca);
    	andrew.openAccount(sa);
    	ca.deposit(500.00);
    	bank.addCustomer(andrew);
    	andrew.getAccount(1).transferTo(andrew.getAccount(2), 100.00);
    	assertEquals(100.00, andrew.getAccount(2).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(400.00, andrew.getAccount(1).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(andrew.getAccount(2), sa);
    }
    /**
     * Test the transfer of an amount from checking to maxi savings
     */
    @Test
    public void testTransferToMaxiSavingsAcount() {   	
    	andrew.openAccount(ca);
    	andrew.openAccount(ma);
    	ca.deposit(500.00);
    	bank.addCustomer(andrew);
    	andrew.getAccount(1).transferTo(andrew.getAccount(3), 100.00);
    	assertEquals(100.00, andrew.getAccount(3).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(400.00, andrew.getAccount(1).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(andrew.getAccount(3), ma);
    }
    /**
     * Test the transfer of an amount between two customers
     */
    @Test
    public void testTransferToCustomer() {
    	andrew.openAccount(ca);
    	john.openAccount(sa);
    	andrew.getAccount(1).deposit(1000.00);
    	bank.addCustomer(andrew);
    	bank.addCustomer(john);
    	andrew.getAccount(1).transferTo(john.getAccount(2), 600.00);
    	assertEquals(600.00, john.getAccount(2).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(400.00, andrew.getAccount(1).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(john.getAccount(2), sa);
    	assertEquals(andrew.getAccount(1), ca);
    }    
    /**
     * Test multiple transfers from one customer
     */
    @Test
    public void testTransferMultiple() {
    	andrew.openAccount(ca);
    	john.openAccount(sa);
    	andrew.getAccount(1).deposit(1000.00);
    	bank.addCustomer(andrew);
    	bank.addCustomer(john);
    	andrew.getAccount(1).transferTo(john.getAccount(2), 600.00);
    	andrew.getAccount(1).transferTo(john.getAccount(2), 20.00);
    	assertEquals(620.00, john.getAccount(2).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(380.00, andrew.getAccount(1).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(john.getAccount(2), sa);
    	assertEquals(andrew.getAccount(1), ca);
    }
    /**
     * Test transfer from customer one to customer two
     * and transfer from customer two to customer one
     */
    @Test
    public void testTransferTwoAcc() {
    	andrew.openAccount(ca);
    	john.openAccount(sa);
    	andrew.getAccount(1).deposit(1000.00);
    	bank.addCustomer(andrew);
    	bank.addCustomer(john);
    	andrew.getAccount(1).transferTo(john.getAccount(2), 600.00);
    	john.getAccount(2).transferTo(andrew.getAccount(1), 20.00);
    	assertEquals(580.00, john.getAccount(2).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(420.00, andrew.getAccount(1).sumTransactions(), DOUBLE_DELTA);
    	assertEquals(john.getAccount(2), sa);
    	assertEquals(andrew.getAccount(1), ca);
    	assertEquals("Statement for Andrew\n" +
                "\n" +
                "Account No: 1\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  transfer out $600.00\n" +
                "  transfer in $20.00\n" +
                "Total $420.00\n" +
                "\n" +
                "Total In All Accounts $420.00", andrew.getStatement());    	
    	assertEquals("Statement for John\n" +
                "\n" +
                "Account No: 2\n" +
                "Savings Account\n" +
                "  transfer in $600.00\n" +
                "  transfer out $20.00\n" +
                "Total $580.00\n" +
                "\n" +
                "Total In All Accounts $580.00", john.getStatement());
    }    

}

package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount(1134583);
        Account savingsAccount = new SavingsAccount(1152374);

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(1123647));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount(1134521));
        oscar.openAccount(new CheckingAccount(1187645));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount(1127636));
        oscar.openAccount(new CheckingAccount(1147861));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testBankTransfer() throws Exception {
    	Customer pete = new Customer("Pete").openAccount(new CheckingAccount(1134584))
    			.openAccount(new SavingsAccount(1128945));
    	
    	pete.getAccount(1134584).deposit(1000);
    	pete.transfer(1134584, 1128945, 500);
    	
    	assertEquals("Withdrawal didn't work as expected",500, pete.getAccount(1134584).getBalance(), DOUBLE_DELTA);
    	assertEquals("Deposit didn't work as expected",500, pete.getAccount(1128945).getBalance(), DOUBLE_DELTA);
    }
    
    @Test (expected = Exception.class)
    public void testInvalidAccountNumberException() throws Exception {
    	Customer pete = new Customer("Pete").openAccount(new CheckingAccount(1134584));
    	
    	pete.getAccount(1134584).deposit(1000);
    	pete.transfer(1134584, 1128945, 500);
    }
}

package test.java.com.abc;

import static org.junit.Assert.assertEquals;
import main.java.com.abc.Account;
import main.java.com.abc.Customer;

import org.junit.Ignore;
import org.junit.Test;

public class CustomerTest {

	private final static double VERY_SMALL_AMOUNT = 1e-15;
	
    @Test //Test customer statement generation
    public void testApp(){

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
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testInterestEarnedOneAccount() {
    	Customer peter = new Customer("Peter");
    	Account peteChecking = new Account(Account.CHECKING);
    	
    	peter.openAccount(peteChecking);
    	
    	peteChecking.deposit(100);
    	
    	assertEquals(0.1, peter.totalInterestEarned(), VERY_SMALL_AMOUNT);
    }
    
    @Test
    public void testInterestEarnedTwoAccounts() {
    	Customer peter = new Customer("Peter");
    	Account peteChecking = new Account(Account.CHECKING);
    	Account peteSavings = new Account(Account.SAVINGS);
    	
    	peter.openAccount(peteChecking);
    	peter.openAccount(peteSavings);
    	
    	peteChecking.deposit(100);
    	peteSavings.deposit(100);
    	
    	
    	assertEquals(0.2, peter.totalInterestEarned(), VERY_SMALL_AMOUNT);
    }
    
    @Test
    public void testInterestEarnedThreeAccounts() {
    	Customer peter = new Customer("Peter");
    	Account peteChecking = new Account(Account.CHECKING);
    	Account peteSavings = new Account(Account.SAVINGS);
    	Account peteMaxi = new Account(Account.MAXI_SAVINGS);
    	
    	peter.openAccount(peteChecking);
    	peter.openAccount(peteSavings);
    	peter.openAccount(peteMaxi);
    	
    	peteChecking.deposit(100);
    	peteSavings.deposit(100);
    	peteMaxi.deposit(100);
    	
    	
    	assertEquals(2.2, peter.totalInterestEarned(), VERY_SMALL_AMOUNT);
    }
}

package test.java.com.abc;

import main.java.com.abc.Account;
import main.java.com.abc.Bank;
import main.java.com.abc.Customer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void testInvalidDepositAmount(){
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Customer abhinav = new Customer("Abhinav").openAccount(checkingAccount);
		bank.addCustomer(abhinav);

		expectedException.expect(IllegalArgumentException.class);
		assertEquals(1, checkingAccount.deposit(-100.0));
	}
	
	@Test
	public void testInvalidWithdrwalAmount(){
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Customer abhinav = new Customer("Abhinav").openAccount(checkingAccount);
		bank.addCustomer(abhinav);

		expectedException.expect(IllegalArgumentException.class);
		assertEquals(1, checkingAccount.withdraw(-100.0));
	}
	
	@Test
	public void testTransfer(){
		Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer abhinav = new Customer("Abhinav").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(1000.0);
        
        abhinav.transfer(abhinav, checkingAccount, savingsAccount, 100);
        String statment = "Statement for Abhinav\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  deposit $100.00\n" +
                "Total $1,100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $100.00\n" +
                "Total $900.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00";
        assertEquals(statment, abhinav.getStatement());
        
	}
    
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

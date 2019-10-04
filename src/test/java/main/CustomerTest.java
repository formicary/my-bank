package main;

import org.junit.Test;
import helper.AccountTypes;
import main.Account;
import main.Customer;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

	@Test // Test customer statement generation
	public void testApp() {

		Account checkingAccount = new Account(AccountTypes.CHECKING);
		Account savingsAccount = new Account(AccountTypes.SAVINGS);

		Customer henry = new Customer(2, "Henry").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n"
				+ "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n" + "Total $3,800.00\n"
				+ "\n" + "Total In All Accounts $3,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer(1, "Oscar").openAccount(new Account(AccountTypes.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer(1, "Oscar").openAccount(new Account(AccountTypes.SAVINGS));
		oscar.openAccount(new Account(AccountTypes.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAccounts() {
		Customer oscar = new Customer(1, "Oscar").openAccount(new Account(AccountTypes.SAVINGS));
		oscar.openAccount(new Account(AccountTypes.CHECKING));
		oscar.openAccount(new Account(AccountTypes.MAXI_SAVINGS));
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	// Custom test which checks that money is withdrawn from one account and
	// successfully deposited to the other account.
	@Test
	public void TestTransferMoney() {

		Account checkingAccount = new Account(AccountTypes.CHECKING);
		Account savingsAccount = new Account(AccountTypes.SAVINGS);

		Customer george = new Customer(2, "George").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.0);
		savingsAccount.deposit(4000.0);

		george.transferMoneyToOtherAcc(500, 0, 1);

		assertEquals(String.valueOf(-500.0), String.valueOf(checkingAccount.transactions.get(1).amount));
		assertEquals(String.valueOf(500.0), String.valueOf(savingsAccount.transactions.get(1).amount));

	}
}

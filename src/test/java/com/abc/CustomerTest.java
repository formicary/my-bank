package com.abc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.SavingsAccount;

public class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;

	private static DateProvider dp = new DateProvider();

	@BeforeAll
	public static void SetUp() {
		dp = new DateProvider();
	}

	@Test // Test customer statement generation
	public void testApp() {

		Account checkingAccount = new CheckingAccount(dp);
		Account savingsAccount = new SavingsAccount(dp);

		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n"
				+ "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n" + "Total $3,800.00\n"
				+ "\n" + "Total In All Accounts $3,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(dp));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(dp));
		oscar.openAccount(new CheckingAccount(dp));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Disabled
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(dp));
		oscar.openAccount(new CheckingAccount(dp));
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTransferBetweenSelfAccounts() {

		Account src = new CheckingAccount(dp);
		Account dest = new SavingsAccount(dp);

		Customer henry = new Customer("Henry").openAccount(src).openAccount(dest);

		src.deposit(1000.0);
		dest.deposit(4000.0);

		henry.selfTransfer(src, dest, 100.0);

		assertEquals(src.sumTransactions(), 900.0, DOUBLE_DELTA);
		assertEquals(dest.sumTransactions(), 4100.0, DOUBLE_DELTA);

	}

	@Test
	public void testTransferBetweenSelfAccountsInsufficientSource() {

		Account src = new CheckingAccount(dp);
		Account dest = new SavingsAccount(dp);

		Customer henry = new Customer("Henry").openAccount(src).openAccount(dest);

		src.deposit(10.0);
		dest.deposit(4000.0);

		assertThrows(IllegalArgumentException.class, () -> {
			henry.selfTransfer(src, dest, 100.0);
		});

		assertEquals(src.sumTransactions(), 10.0, DOUBLE_DELTA);
		assertEquals(dest.sumTransactions(), 4000.0, DOUBLE_DELTA);

	}

	@Test
	public void testTransferBetweenSelfAccountsNegativeAmount() {

		Account src = new CheckingAccount(dp);
		Account dest = new SavingsAccount(dp);

		Customer henry = new Customer("Henry").openAccount(src).openAccount(dest);

		src.deposit(1000.0);
		dest.deposit(4000.0);

		assertThrows(IllegalArgumentException.class, () -> {
			henry.selfTransfer(src, dest, -100.0);
		});

		assertEquals(src.sumTransactions(), 1000.0, DOUBLE_DELTA);
		assertEquals(dest.sumTransactions(), 4000.0, DOUBLE_DELTA);

	}

	@Test
	public void testTransferBetweenSelfAccountsZeroAmount() {

		Account src = new CheckingAccount(dp);
		Account dest = new SavingsAccount(dp);

		Customer henry = new Customer("Henry").openAccount(src).openAccount(dest);

		src.deposit(1000.0);
		dest.deposit(4000.0);

		assertThrows(IllegalArgumentException.class, () -> {
			henry.selfTransfer(src, dest, 0.0);
		});

		assertEquals(src.sumTransactions(), 1000.0, DOUBLE_DELTA);
		assertEquals(dest.sumTransactions(), 4000.0, DOUBLE_DELTA);

	}
}

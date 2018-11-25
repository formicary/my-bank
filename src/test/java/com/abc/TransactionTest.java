package test.java.com.abc;

import org.junit.Test;

import main.java.com.abc.Account;
import main.java.com.abc.Customer;
import main.java.com.abc.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
	@Test
	public void transaction() {
		Transaction t = new Transaction(5);
		assertTrue(t instanceof Transaction);
	}

	public class TransferTest {
		@Test
		public void transfer() {
			Transaction t = new Transaction(2.5);
			boolean output = true;
			boolean val;
			double amount = 2.5;
			Account from = null;
			Account to = null;
			Customer customer = null;

			val = t.transferMoney(amount, from, to, customer);
			assertEquals(output,val);
		}

	}
}

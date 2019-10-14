package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TellerTest {

	private static Teller tellerAlison;
	private static Bank abcBank;
	private static Customer customerNick;
	private static Customer customerMatt;
	private static BigDecimal amount;

	@BeforeClass
	public static void init() {
		tellerAlison = new Teller("Alison");
		BankManager managerRobert = new BankManager("Robert");
		// Instantiate a bank with teller and bank manager
		abcBank = new Bank("ABC Bank", managerRobert, tellerAlison);
		customerMatt = new Customer("Matt");
		customerNick = new Customer("Nick");
		amount = new BigDecimal(500);
	}

	@Before
	public void reset() {
		abcBank.setCustomers(new HashMap<Customer, List<Account>>());

		// Make accounts for both customers
		// Account number: 0
		tellerAlison.openAccount(customerMatt, 0);
		// Account number: 1
		tellerAlison.openAccount(customerNick, 0);
	}

	@Test
	public void notAMemberOfBank() {
		Teller tellerLuna = new Teller("Luna");

		// Attempt to call a function that requires a bank
		assertEquals("Luna does not belong to this bank.", tellerLuna.getStatement(customerMatt));
	}

	@Test
	public void openNonExistentAccount() {
		// Attempt to create account type 3 which does not exist
		assertEquals("Account Type: 3 does not exist.", tellerAlison.openAccount(customerMatt, 3));
	}

	@Test
	public void openAccount() {
		// Create a Checking Account for the customer with the account number 2
		assertEquals("Account Number: 2 opened successfully.", tellerAlison.openAccount(customerMatt, 0));

		// Check that the account was created
		assertEquals(2, abcBank.getAccounts(customerMatt).get(1).getAccountNumber());
	}

	@Test
	public void depositNegativeValue() {
		BigDecimal negativeAmount = new BigDecimal(-1);

		// Attempt to deposit a negative value into the account
		assertEquals("Deposit amount must be a positive value.", tellerAlison.deposit(negativeAmount, 0));
	}

	@Test
	public void nonExistentAccountNumber() {
		// Attempt to deposit money into a non-existent account number
		assertEquals("Account Number: 3 does not exist.", tellerAlison.deposit(amount, 3));
	}

	@Test
	public void deposit() {
		// Deposit $500 into account number 0
		assertEquals("$500.00 deposited into Account Number: 0.", tellerAlison.deposit(amount, 0));

		// Check new balance
		assertTrue(abcBank.getAccount(0).getBalance().equals(amount));
	}

	@Test
	public void withdrawNegativeValue() {
		BigDecimal negativeAmount = new BigDecimal(-1);

		// Attempt to withdraw a negative value
		assertEquals("Withdrawal amount must be positive.", tellerAlison.withdraw(negativeAmount, customerMatt, 0));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void noAccountWithBank() {
		Customer customerLuna = new Customer("Luna");

		// Customer who does not belong to bank attempts to withdraw money
		assertEquals("Luna does not have an account with ABC Bank.", tellerAlison.withdraw(amount, customerLuna, 0));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void notAccountOwner() {
		// Attempt to withdraw $500 from account number 1 which belongs to customerNick
		assertEquals("Matt does not own the Account Number: 1.", tellerAlison.withdraw(amount, customerMatt, 1));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void insufficientFunds() {
		// Attempt to withdraw $500 from account number: 0 with insufficient funds
		assertEquals("Account Number: 0 has insufficient funds.", tellerAlison.withdraw(amount, customerMatt, 0));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void withdraw() {
		tellerAlison.deposit(amount, 0);

		// Balance should be 500
		assertTrue(abcBank.getAccount(0).getBalance().equals(amount));

		// Withdraw $500 from account number: 0 which belongs to customerMatt
		assertEquals("$500.00 withdrawn from Account Number: 0.", tellerAlison.withdraw(amount, customerMatt, 0));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void sameTransferAccountNumbers() {
		// Attempt to transfer between the same account
		assertEquals("The Transferor (0) and Transferee (0) account numbers must be different.",
				tellerAlison.transfer(amount, customerMatt, 0, 0));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void notCustomerAccount() {
		// Attempt to transfer/receive money from an account that does not belong to
		// customerMatt
		assertEquals("Transferor Account: 1 or Transferee Account: 0 does not belong to Matt.",
				tellerAlison.transfer(amount, customerMatt, 1, 0));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void transferorInsufficientFunds() {
		// Create a new account for the customer with account number 2
		tellerAlison.openAccount(customerMatt, 0);

		// Attempt to transfer between the same account
		assertEquals("Account: 0 has insufficient funds.", tellerAlison.transfer(amount, customerMatt, 0, 2));

		// Balance should be 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	public void transfer() {
		// Create a new account for the customer with account number 2
		tellerAlison.openAccount(customerMatt, 0);

		tellerAlison.deposit(amount, 0);

		// Transfer 500 to account number: 2
		assertEquals("$500.00 has been transferred from Account: 0 to Account: 2.",
				tellerAlison.transfer(amount, customerMatt, 0, 2));

		// Balance should be 0 for account number 0
		assertTrue(abcBank.getAccount(0).getBalance().equals(BigDecimal.ZERO));
		// Balance should be 500 for account number 2
		assertTrue(abcBank.getAccount(2).getBalance().equals(amount));
	}

	@Test
	public void statement() {
		BigDecimal withdrawAmount = new BigDecimal(250);
		// Open account, deposit x2 and withdraw from customerMatt
		tellerAlison.openAccount(customerMatt, 0);
		tellerAlison.deposit(amount, 0);
		tellerAlison.deposit(amount, 2);
		tellerAlison.withdraw(withdrawAmount, customerMatt, 2);

		// Check balances
		assertTrue(abcBank.getAccount(0).getBalance().equals(amount));
		assertTrue(abcBank.getAccount(2).getBalance().equals(withdrawAmount));

		String expectedStatement = "STATEMENT (MATT):\n\n• Account Number: 0, Account Type: Checking Account\n - Deposit: $500.00, Date: "
				+ LocalDate.now()
				+ "\n- Balance: $500.00\n\n• Account Number: 2, Account Type: Checking Account\n - Deposit: $500.00, Date: "
				+ LocalDate.now() + "\n - Withdrawal: $250.00, Date: " + LocalDate.now()
				+ "\n- Balance: $250.00\n\n• Total Balance: $750.00";

		assertEquals(expectedStatement, tellerAlison.getStatement(customerMatt));
	}

	@AfterClass
	public static void finalise() {
		tellerAlison = null;
		abcBank = null;
		customerMatt = null;
		customerNick = null;
		amount = null;
	}

}

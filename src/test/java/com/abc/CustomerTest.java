package com.abc;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;



public class CustomerTest {
	private static final int accountLimit = Integer.MAX_VALUE / 2;
	private final Bank bank = new Bank();
	private Account checkingAccount = new Account(Account.CHECKING);
	private Account savingsAccount = new Account(Account.SAVINGS);
	
	@Test
	public void CustomerCanRequestStatement() {
		Customer oscar = new Customer("Oscar", bank, checkingAccount);
		oscar.openAccount(savingsAccount, bank);
		
		checkingAccount.accountDeposit(new BigDecimal("100.00"));
		savingsAccount.accountDeposit(new BigDecimal("4000.00"));
		savingsAccount.accountWithdraw(new BigDecimal("200.00"));

		assertEquals(
				"Statement for Oscar\n" + "\n" + "Checking Account with ID: "+checkingAccount.getAccountId()+"\n" + "  deposit $100.00\n"
						+ "Total $100.00\n" + "\n" + "Savings Account with ID: "+savingsAccount.getAccountId()+"\n" + "  deposit $4,000.00\n"
						+ "  withdrawal $200.00\n" + "Total $3,800.00\n" + "\n" + "Total In All Accounts $3,900.00",
				oscar.getStatement());
	}



	@Test
	public void customerCanOpenAccount() {
		Customer oscar = new Customer("Oscar", bank, (new Account(Account.SAVINGS)));
		
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void customerCanDepositAndWithdraw() {
		Customer oscar = new Customer("Oscar", bank, savingsAccount);
		oscar.deposit(savingsAccount.getAccountId(), new BigDecimal("500.00"));
		oscar.withdraw(savingsAccount.getAccountId(), new BigDecimal("200.00"));
		
		assertEquals(new BigDecimal("300.00"), savingsAccount.returnAccountBalance());
	}

	@Test
	public void customerCannotWithdrawMoreThanBalance() {
		Customer oscar = new Customer("Oscar", bank, savingsAccount);
		oscar.withdraw(savingsAccount.getAccountId(), new BigDecimal("200.00"));
		
		assertEquals(new BigDecimal("0.00"), savingsAccount.returnAccountBalance());
	}

	@Test
	public void customerCannotDepositMoreThanHalfMaxInt() {
		Customer oscar = new Customer("Oscar", bank, savingsAccount);
		oscar.deposit(savingsAccount.getAccountId(), new BigDecimal(accountLimit+1));
		
		assertEquals(new BigDecimal("0.00"),
				savingsAccount.returnAccountBalance());
	}

	@Test
	public void customersCanTransferBetweenAccounts() {
		Customer oscar = new Customer("Oscar", bank, checkingAccount);
		oscar.openAccount(savingsAccount, bank);

		checkingAccount.accountDeposit(new BigDecimal("100.00"));
		savingsAccount.accountDeposit(new BigDecimal("4000.00"));
		int id1 = checkingAccount.getAccountId();
		int id2 = savingsAccount.getAccountId();

		oscar.transferToAccount(id1, id2, new BigDecimal("50.00"));
		assertEquals(new BigDecimal("50.00"), checkingAccount.returnAccountBalance());
	}

}

package com.abc;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;

import com.abc.Account.Account;
import com.abc.Account.AccountType;
import com.abc.Account.CreateAccount;
import com.abc.Bank.Bank;
import com.abc.Bank.Customer;

public class BankTest {

	@Test
	public void customer_summary_single_account_test() {
		Bank bank = new Bank();
		Customer sunny = new Customer("Sunny");
		Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);
		sunny.openAccount(checkingAcc);
		bank.addCustomer(sunny);

		assertEquals("Customer Summary\n - Sunny (1 account)", bank.customerSummaryReport());
	}

	@Test
	public void customer_summary_two_accounts_test() {
		Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);
		Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
		Bank bank = new Bank();
		Customer sunny = new Customer("Sunny");
		sunny.openAccount(checkingAcc).openAccount(savingsAcc);
		bank.addCustomer(sunny);

		assertEquals("Customer Summary\n - Sunny (2 accounts)", bank.customerSummaryReport());
	}

	@Test
	public void total_interest_paid_checking_account_test() {
		Bank bank = new Bank();
		Account checkingAcc = CreateAccount.createAccount(AccountType.CHECKING);
		Customer jenny = new Customer("Jenny").openAccount(checkingAcc);
		bank.addCustomer(jenny);

		checkingAcc.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), -Double.MAX_VALUE);
	}

	@Test
	public void total_interest_paid_savings_account_test() {
		Bank bank = new Bank();
		Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
		bank.addCustomer(new Customer("Jenny").openAccount(savingsAcc));

		savingsAcc.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), -Double.MAX_VALUE);
	}

	@Test
	public void total_interest_paid_maxi_savings_account_without_withdrawals_test() {
		Bank bank = new Bank();
		Account maxiSavingsAcc = CreateAccount.createAccount(AccountType.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Jenny").openAccount(maxiSavingsAcc));

		maxiSavingsAcc.deposit(3000.0);

		assertEquals(150.0, bank.totalInterestPaid(), -Double.MAX_VALUE);
	}

	@Test
	public void total_interest_paid_maxi_savings_account_no_withdrawals_within_ten_days_test() {
		// Override the date set on the transaction object to test interest (Maxi
		// Savings Account)
		Bank bank = new Bank();
		Account maxiSavingsAcc = CreateAccount.createAccount(AccountType.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Jenny").openAccount(maxiSavingsAcc));
		Instant date = Instant.parse("2019-01-01T00:00:01.00Z");// DateProvider.generateDate(2019, 0, 1);
		maxiSavingsAcc.deposit(2000.0);
		maxiSavingsAcc.getTransactions().get(0).setTransactionDate(date);
		maxiSavingsAcc.withdraw(1000.0);
		maxiSavingsAcc.getTransactions().get(1).setTransactionDate(date);
		assertEquals(50.0, bank.totalInterestPaid(), -Double.MAX_VALUE);
	}

	@Test
	public void total_interest_paid_maxi_savings_account_withdrawals_within_ten_days_test() {
		Bank bank = new Bank();
		Account maxiSavingsAcc = CreateAccount.createAccount(AccountType.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Jenny").openAccount(maxiSavingsAcc));

		maxiSavingsAcc.deposit(3000.0);
		maxiSavingsAcc.withdraw(1000.0);
		assertEquals(2.0, bank.totalInterestPaid(), -Double.MAX_VALUE);
	}

	@Test
	public void report_total_interest_paid_all_accounts_test() {
		Bank bank = new Bank();
		Account savingsAccJenny = CreateAccount.createAccount(AccountType.SAVINGS);
		Account savingsAccSunny = CreateAccount.createAccount(AccountType.SAVINGS);

		savingsAccJenny.deposit(1000.0);
		savingsAccSunny.deposit(1000.0);

		Account checkingAccJenny = CreateAccount.createAccount(AccountType.CHECKING);
		Account checkingAccSunny = CreateAccount.createAccount(AccountType.CHECKING);

		checkingAccJenny.deposit(1000.0);
		checkingAccSunny.deposit(1000.0);

		bank.addCustomer(new Customer("Jenny").openAccount(savingsAccJenny).openAccount(checkingAccJenny));
		bank.addCustomer(new Customer("Sunny").openAccount(savingsAccSunny).openAccount(checkingAccSunny));

		assertEquals(4.0, bank.totalInterestPaid(), -Double.MAX_VALUE);
	}

	@Test
	public void report_total_customers_and_accounts_test() {
		Bank bank = new Bank();
		Account savingAccJenny = CreateAccount.createAccount(AccountType.SAVINGS);

		Account savingAccSunny = CreateAccount.createAccount(AccountType.SAVINGS);
		Account checkingAccSunny = CreateAccount.createAccount(AccountType.CHECKING);

		bank.addCustomer(new Customer("Jenny").openAccount(savingAccJenny));
		bank.addCustomer(new Customer("Sunny").openAccount(savingAccSunny).openAccount(checkingAccSunny));

		assertEquals(2, bank.getCustomers().size());
		assertEquals(1, bank.getCustomers().get(0).getAccounts().size());
		assertEquals(2, bank.getCustomers().get(1).getAccounts().size());
	}

	@Test
	public void test_first_customer() {
		Bank bank = new Bank();
		Customer jenny = new Customer("Jenny");
		Customer sunny = new Customer("Sunny");

		bank.addCustomer(jenny);
		bank.addCustomer(sunny);

		assertEquals("Jenny", bank.getFirstCustomer());
	}

}

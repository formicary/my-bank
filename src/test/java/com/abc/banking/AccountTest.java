package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.abc.banking.AbstractAccount;
import com.abc.banking.Account;
import com.abc.banking.CheckingAccount;
import com.abc.banking.MaxiSavingsAccount;
import com.abc.banking.SavingsAccount;
import com.abc.banking.Transaction;
import com.abc.banking.exceptions.TransactionException;
import com.abc.utils.TestingUtils;

@RunWith(Parameterized.class)
public class AccountTest {

	Class<Account> accountClass;
	
	public AccountTest(Class<Account> accountClass) {
		this.accountClass = accountClass;
	}
	
	@Parameters
    public static Collection<Object[]> data() {
            
    	Collection<Object[]> parameterList = new ArrayList<>();
            
            parameterList.add(new Object[] { CheckingAccount.class });
            parameterList.add(new Object[] { SavingsAccount.class });
            parameterList.add(new Object[] { MaxiSavingsAccount.class });
            
            return parameterList;
    }
	
	@Test
	public void dailyInterestOnBlankAccountShallBeZero() throws Exception {
		
		Account account = accountClass.newInstance();

		account.getDailyInterest(LocalDate.now());

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.00"), account.getDailyInterest(LocalDate.of(2017, 12, 11)));
	}
	
	@Test
	public void totalAccruedInterestOnBlankAccountShallBeZero() throws Exception {
		
		Account account = accountClass.newInstance();

		account.ensureInterestAccrued();

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.00"), account.getBalance());
	}
	
	@Test
	public void depositShallIncreaseBalance() throws Exception {
		
		Account account = accountClass.newInstance();
		
		account.deposit(new BigDecimal("1000.00"));
		account.deposit(new BigDecimal("500.00"));

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("1500.00"), account.getBalance());
	}
	
	@Test
	public void withdrawalShallDecreaseBalance() throws Exception {
		
		Account account = accountClass.newInstance();
		
		account.deposit(new BigDecimal("1000.00"));
		account.withdraw(new BigDecimal("500.00"));

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("500.00"), account.getBalance());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void depositOfZeroAmountShallThrowException() throws Exception {
		Account account = accountClass.newInstance();
		
		account.deposit(BigDecimal.ZERO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void depositOfNegativeAmountShallThrowException() throws Exception {
		Account account = accountClass.newInstance();
		
		account.deposit(new BigDecimal("-100.20"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void withdrawalOfZeroAmountShallThrowException() throws Exception {
		Account account = accountClass.newInstance();
		
		account.withdraw(BigDecimal.ZERO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void withdrawalOfNegativeAmountShallThrowException() throws Exception {
		Account account = accountClass.newInstance();
		
		account.withdraw(new BigDecimal("-100.20"));
	}

	@Test
	public void depositShallReturnCorrectTransaction() throws Exception {
		
		Account account = accountClass.newInstance();
		
		Transaction trx = account.deposit(new BigDecimal("15.00"));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("15.00"), trx.getAmount());
		Assert.assertEquals(LocalDate.now(), trx.getTransactionDate());
		Assert.assertEquals(false, trx.isAccruedInterest());
	}	

	@Test
	public void withdrawalShallReturnCorrectTransaction() throws Exception {
		
		Account account = accountClass.newInstance();
		
		Transaction trx = account.deposit(new BigDecimal("15.00"));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("15.00"), trx.getAmount());
		Assert.assertEquals(LocalDate.now(), trx.getTransactionDate());
		Assert.assertEquals(false, trx.isAccruedInterest());
	}	

	@Test(expected = TransactionException.class)
	public void withdrawalShallNotAllowNegativeBalance() throws Exception {
		
		Account account = accountClass.newInstance();
		
		account.deposit(new BigDecimal("15.00"));
		
		account.withdraw(new BigDecimal("15.01"));
	}	

	@Test(expected = IllegalArgumentException.class)
	public void depositShallFailOnNullArgument() throws Exception {
		
		Account account = accountClass.newInstance();
		
		account.deposit(null);
	}	

	@Test(expected = IllegalArgumentException.class)
	public void withDrawalShallFailOnNullArgument() throws Exception {
		
		Account account = accountClass.newInstance();
		
		account.withdraw(null);
	}	

	@Test(expected = UnsupportedOperationException.class)
	public void getTransactionsShallReturnUnmodifiableCollection() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		account.deposit(new BigDecimal("15.00"));
		
		Collection<Transaction> transactions = account.getTransactions();
		
		transactions.add(new Transaction(new BigDecimal("15.00")));
	}	

	@Test
	public void getTransactionsShallContainAllTransactions() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		Transaction trx1 = account.deposit(new BigDecimal("15.00"));
		Transaction trx2 = account.deposit(new BigDecimal("150.00"));
		Transaction trx3 = account.withdraw(new BigDecimal("50.00"));
		
		Collection<Transaction> transactions = account.getTransactions();
		
		Assert.assertTrue(transactions.contains(trx1));
		Assert.assertTrue(transactions.contains(trx2));
		Assert.assertTrue(transactions.contains(trx3));
		
		Assert.assertEquals(3, transactions.size());
	}
	
	@Test
	public void getBalanceShallReturnActualBalance() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		account.deposit(new BigDecimal("15.00"));
		account.deposit(new BigDecimal("150.00"));
		account.withdraw(new BigDecimal("50.00"));
		
		TestingUtils.assertEqualsBigDecimal(account.getBalance(), account.getBalanceAt(LocalDate.now()));
	}	

	@Test
	public void getBalanceShallReturnCorrectBalance() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		account.deposit(new BigDecimal("15.00"));
		account.deposit(new BigDecimal("150.00"));
		account.withdraw(new BigDecimal("50.00"));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("115.00"), account.getBalance());
	}
	
	@Test
	public void blankAccountShallHaveNoBalance() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.00"), account.getBalance());
	}
	
	@Test
	public void accountsShallEqualIfTheSameObject() throws Exception {
		
		Account account = accountClass.newInstance();
		
		Assert.assertEquals(account, account);
	}

	@Test
	public void accountsShallNotEqualIfDifferentObjects() throws Exception {
		
		Account account1 = accountClass.newInstance();
		Account account2 = accountClass.newInstance();
		
		Assert.assertNotEquals(account1, account2);
	}
	
	@Test
	public void getBalanceAtShallReturnCorrectBalance() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("1000.00")),
				"transactionDate",
				LocalDate.of(2016, 1, 1));
		
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("10.00")),
				"transactionDate",
				LocalDate.of(2017, 5, 14));
		
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("90.00")),
				"transactionDate",
				LocalDate.of(2017, 9, 12));
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("77.00")),
				"transactionDate",
				LocalDate.of(2017, 12, 11));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("0.00"), account.getBalanceAt(LocalDate.of(2015, 12, 31)));

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("1000.00"), account.getBalanceAt(LocalDate.of(2016, 1, 1)));
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("1000.00"), account.getBalanceAt(LocalDate.of(2017, 5, 13)));

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("990.00"), account.getBalanceAt(LocalDate.of(2017, 5, 14)));

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("990.00"), account.getBalanceAt(LocalDate.of(2017, 9, 1)));

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("900.00"), account.getBalanceAt(LocalDate.of(2017, 9, 14)));

		TestingUtils.assertEqualsBigDecimal(new BigDecimal("977.00"), account.getBalanceAt(LocalDate.of(2017, 12, 12)));
	}

	@Test
	public void ensureAccruedInterestShallAccrueInterestEveryDayAndOnlyOnce() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("1000.00")),
				"transactionDate",
				LocalDate.of(2016, 1, 1));
		
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("10.00")),
				"transactionDate",
				LocalDate.of(2017, 5, 14));
		
		TestingUtils.changePrivateField(
				account.withdraw(new BigDecimal("90.00")),
				"transactionDate",
				LocalDate.of(2017, 9, 12));
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("77.00")),
				"transactionDate",
				LocalDate.of(2017, 12, 11));
		
		account.ensureInterestAccrued();

		Collection<Transaction> transactions = account.getTransactions();
		
		for(LocalDate day = LocalDate.of(2016, 1, 1); day.compareTo(LocalDate.now()) < 0; day = day.plusDays(1) ) {
			
			LocalDate dayToCheck = LocalDate.from(day);
			
			List<Transaction> interestTransactions =
				transactions
					.stream()
					.filter(trx -> trx.getTransactionDate().equals(dayToCheck))
					.filter(trx -> trx.isAccruedInterest() == true)
					.collect(Collectors.toList());
			
			Assert.assertEquals(1, interestTransactions.size());
		}
	}
	
	@Test
	public void ensureAccruedInterestShallContinueAfterLastAccruedInterestDate() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		TestingUtils.changePrivateField(
				account.deposit(new BigDecimal("1000.00")),
				"transactionDate",
				LocalDate.of(2016, 1, 1));
		
		Transaction lastAccruedInterestTransaction = account.deposit(new BigDecimal("10.05"));
		TestingUtils.changePrivateField(lastAccruedInterestTransaction, "transactionDate", LocalDate.of(2017, 12, 1));
		TestingUtils.changePrivateField(lastAccruedInterestTransaction, "accruedInterest", Boolean.TRUE);
		
		Collection<Transaction> oldTransactions = account.getTransactions();
		
		account.ensureInterestAccrued();
		
		Collection<Transaction> newTransactions = new ArrayList<Transaction>(account.getTransactions());
		newTransactions.removeAll(oldTransactions);
		
		LocalDate minDateNewAccruedInterest =
				newTransactions
					.stream()
					.map(transaction -> transaction.getTransactionDate())
					.min(LocalDate::compareTo)
					.orElse(LocalDate.MIN);
		
		Assert.assertEquals(LocalDate.of(2017, 12, 2), minDateNewAccruedInterest);
	}
	
	@Test
	public void RollbackTransactionShallRemoveTransactionToRollback() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		
		Transaction transaction = account.deposit(new BigDecimal("1000.00"));
		
		account.rollBackTransactionIfExists(transaction);
		
		Assert.assertFalse(account.getTransactions().contains(transaction));
	}

	@Test
	public void RollbackTransactionShallDoNothingIfTheTransactionDoesNotExist() throws Exception {
		
		AbstractAccount account = (AbstractAccount)accountClass.newInstance();
		account.deposit(new BigDecimal("1000.00"));
		account.deposit(new BigDecimal("2000.00"));
		
		int formerSize = account.getTransactions().size();
		
		Transaction transaction = new Transaction(new BigDecimal("3000.00"));
		account.rollBackTransactionIfExists(transaction);
		
		int newSize = account.getTransactions().size();
		
		Assert.assertEquals(formerSize, newSize);
	}
	
    @Test
    public void sameInstanceShallHaveSameHashCode() throws Exception  {
    	Bank bank = new Bank();
    	Customer johnny = bank.newCustomer("Johnny");
    	Account account = johnny.openAccount(SavingsAccount.class);
    	
    	Assert.assertEquals(account.hashCode(), account.hashCode());
    }

}

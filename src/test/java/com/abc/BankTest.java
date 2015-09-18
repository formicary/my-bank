package com.abc;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.abc.Account.AccountType;

public class BankTest extends TestCase {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        Customer bill = new Customer("Bill");
        bill.openAccount(new Account(AccountType.CHECKING));
        bill.openAccount(new Account(AccountType.MAXI_SAVINGS));
        bank.addCustomer(john);
        bank.addCustomer(bill);

        assertEquals("Customer Summary\n" +
                     " - John (1 account)\n" +
                     " - Bill (2 accounts)" , bank.customerSummary());
    }
    
    @Test
    public void testWithdrawNegativeAmount() {
        try {
            new Account(AccountType.CHECKING).withdraw(-1000.0);
			fail("Should throw IllegalArgumentException when amount is negative");
		} catch (IllegalArgumentException e) {
			assert(e.getMessage().contains("amount must be greater than zero"));
		}
	}
    
    @Test
    public void testWithdrawZero() {
        try {
            new Account(AccountType.CHECKING).withdraw(0.0);
			fail("Should throw IllegalArgumentException when amount is equal to zero");
		} catch (IllegalArgumentException e) {
			assert(e.getMessage().contains("amount must be greater than zero"));
		}
	}
    
    @Test
    public void testDepositNegativeAmount() {
        try {
            new Account(AccountType.CHECKING).deposit(-1000.0);
			fail("Should throw IllegalArgumentException when amount is negative");
		} catch (IllegalArgumentException e) {
			assert(e.getMessage().contains("amount must be greater than zero"));
		}
	}
    
    @Test
    public void testDepositZero() {
        try {
            new Account(AccountType.CHECKING).deposit(0.0);
			fail("Should throw IllegalArgumentException when amount is equal to zero");
		} catch (IllegalArgumentException e) {
			assert(e.getMessage().contains("amount must be greater than zero"));
		}
	}

    @Test
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000.0);

        assertEquals(0.002739726027397, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(0.006849315068493, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountNoWithdrawal() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(0.410958904109589, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testMaxiSavingsAccountWithWithdrawal() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("John").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(4000.0);
        maxiSavingsAccount.withdraw(1000.0);

        assertEquals(0.008219178082191, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testMaxiSavingsAccountWithOldWithdrawal() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("John").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(4000.0);
        maxiSavingsAccount.withdraw(1000.0);
        Calendar c = Calendar.getInstance();
        c.set(2016, Calendar.OCTOBER, 29);
        Date d = c.getTime();

        assertEquals(0.410958904109589, bank.totalInterestPaidOnDay(d), DOUBLE_DELTA);
    }
}

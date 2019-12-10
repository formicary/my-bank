package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

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

        assertEquals(0.00027397260273972606, bank.totalInterestPaid(), DOUBLE_DELTA); //calculated hard coded value with a calculator
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(1.0027397260273974, bank.totalInterestPaid(), DOUBLE_DELTA); //calculated hard coded value with a calculator
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(0.4109589041095891, bank.totalInterestPaid(), DOUBLE_DELTA); //calculated hard coded value with a calculator
    }
    
    @Test
    public void checkMaxiSavingInterestValidWithRecentWithdrawal() {
    	Bank bank = new Bank();
    	Account acc = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(acc));
    	
    	
    	acc.deposit(200);
    	acc.withdraw(100);
    	
    	assertEquals(0.00027397260273972606,acc.interestEarned(),DOUBLE_DELTA); //calculated hard coded value with a calculator
    }
	
	@Test
	public void checkSavingUnder1000() {
		Bank bank = new Bank();
    	Account acc = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(acc));
    	
    	
    	acc.deposit(100);
    	
    	assertEquals(0.00027397260273972606,acc.interestEarned(),DOUBLE_DELTA); //calculated hard coded value with a calculator
	}
}

package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Account account = AccountFactory.getInstance().createAccount(Account.CHECKING);
        account.deposit(1000);
        account.withdraw(1000);
        assertEquals(0, account.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiAccountNoWithdrawalDailyAccruelTest() {
    	Account account = AccountFactory.getInstance().createAccount(Account.MAXI_SAVINGS, AccountFactory.DEBUG_ENABLED);
    	for(int i = 0; i < 365 ; i++) {
    		account.deposit(5);
    	}
    
    	assertEquals(1871.51, account.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiAccountDailyAccruelWithWithdrawalsTest() {
    	Account account = AccountFactory.getInstance().createAccount(Account.MAXI_SAVINGS, AccountFactory.DEBUG_ENABLED);
    	for(int i = 0; i < 365 ; i++) {
    		if (i == 39) {
    			account.withdraw(50);
    		}
    		account.deposit(5);
    	}
    	    	
    	assertEquals(1819.01, account.getBalance(), DOUBLE_DELTA);
    }
    
	@Test
	public void checkingAccountTest() {
		Bank bank = new Bank();
		Account checkingAccount = AccountFactory.getInstance().createAccount(Account.CHECKING,
				AccountFactory.DEBUG_ENABLED);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);
		
//		Manually apply interest to the account for the year.
		while(checkingAccount.transactions.size() < 365) {
			checkingAccount.applyInterest();
		}
		
		System.out.println(checkingAccount.sumTransactions());
		assertEquals(0.10, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccountAboveThresholdTest() {
		Bank bank = new Bank();
		Account savingsAccount = AccountFactory.getInstance().createAccount(Account.SAVINGS,
				AccountFactory.DEBUG_ENABLED);
		bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

		savingsAccount.deposit(1500.0);
//		Manually apply interest to the account for the year.
		while(savingsAccount.transactions.size() < 365) {
			savingsAccount.applyInterest();
		}

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	
	@Test
	public void savingsAccountBelowThresholdTest() {
		Bank bank = new Bank();
		Account savingsAccount = AccountFactory.getInstance().createAccount(Account.SAVINGS,
				AccountFactory.DEBUG_ENABLED);
		bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

		savingsAccount.deposit(500.0);
//		Manually apply interest to the account for the year.
		while(savingsAccount.transactions.size() < 365) {
			savingsAccount.applyInterest();
		}

		assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
    
    
}
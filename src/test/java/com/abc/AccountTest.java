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
    
    	assertEquals(1869.41, account.sumTransactions(), DOUBLE_DELTA);
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
    	    	
    	assertEquals(1816.95, account.sumTransactions(), DOUBLE_DELTA);
    }
}
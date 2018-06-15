package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
    public void testWithdrawZero() {
		
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Amount must be greater than zero");
		
        Account testAccount = new Account(Account.accountType.CHECKING);

        testAccount.withdraw(0);
        }
	
	@Test
    public void testWithdrawLessZero() {
		
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Amount must be greater than zero");
		
        Account testAccount = new Account(Account.accountType.CHECKING);

        testAccount.withdraw(-50);
        }
	
	@Test
    public void testDepositZero() {
		
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Amount must be greater than zero");
		
        Account testAccount = new Account(Account.accountType.CHECKING);

        testAccount.deposit(0);
        }
	
	@Test
    public void testDepositLessZero() {
		
		thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Amount must be greater than zero");
		
        Account testAccount = new Account(Account.accountType.CHECKING);

        testAccount.deposit(-50);
        }
	
}

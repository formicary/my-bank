package com.abc;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;


public class AccountTest {
	
	private final Bank bank = new Bank();
	private final BankManager steve = new BankManager("Steve", bank);
	
    @Test
    public void checkingAccountInterestRate() {
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill", bank, checkingAccount);
        bill.deposit(checkingAccount.getAccountId(),new BigDecimal("100.00"));
        
        assertEquals("Total interest paid by the bank: $0.10", steve.totalInterestPaidReport());
    }

    @Test
    public void savingsAccountInterestRate() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill", bank, savingsAccount);
        bill.deposit(savingsAccount.getAccountId(),new BigDecimal("1500.00"));

        assertEquals("Total interest paid by the bank: $2.00", steve.totalInterestPaidReport());
    }


    @Test
    public void maxiSavingsAccountInterestRate() {
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill", bank, maxiAccount);
        bill.deposit(maxiAccount.getAccountId(),new BigDecimal("3000.00"));

        assertEquals("Total interest paid by the bank: $170.00", steve.totalInterestPaidReport());
    }

}

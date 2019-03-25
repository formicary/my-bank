package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

public class CustomerTest {
	
	private Customer cust;
	
	@Before
	public void setUp() throws Exception {
		cust = new Customer(TestConstants.CUSTOMER1);
	}
	

    @Test
    public void testOpenAccount() {
    	assertEquals(TestConstants.TRUE, cust.getAccountList().isEmpty());
    	
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	assertEquals(TestConstants.CHECKING_ACCOUNT, cust.getAccountList().get(TestConstants.ZERO).getAccountType());
    	   	
    	cust.openAccount(TestConstants.SAVINGS_ACCOUNT);
    	assertEquals(TestConstants.SAVINGS_ACCOUNT, cust.getAccountList().get(TestConstants.ONE).getAccountType());
    	   	
    	cust.openAccount(TestConstants.MAXISAVINGS_ACCOUNT);
    	assertEquals(TestConstants.MAXISAVINGS_ACCOUNT, cust.getAccountList().get(TestConstants.TWO).getAccountType());
    	
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT_VARYING_CASES);
    	assertEquals(TestConstants.CHECKING_ACCOUNT, cust.getAccountList().get(TestConstants.THREE).getAccountType());
    	assertEquals(TestConstants.CHECKING_ACCOUNT_SUCCESSFULLY_CREATED, cust.openAccount(TestConstants.CHECKING_ACCOUNT_VARYING_CASES));
    	
    	assertEquals(TestConstants.CHECKING_ACCOUNT_SUCCESSFULLY_CREATED, cust.openAccount(TestConstants.CHECKING_ACCOUNT));
    	assertEquals(TestConstants.SAVINGS_ACCOUNT_SUCCESSFULLY_CREATED, cust.openAccount(TestConstants.SAVINGS_ACCOUNT));
    	assertEquals(TestConstants.MAXISAVINGS_ACCOUNT_SUCCESSFULLY_CREATED, cust.openAccount(TestConstants.MAXISAVINGS_ACCOUNT));
    	
    	assertEquals(TestConstants.DEFAULT_CREATE_ACCOUNT_MESSAGE, cust.openAccount(TestConstants.MISSPELLED_ACCOUNT));
    }

    @Test
    public void testGenerateAccountID() {
    	
    	assertEquals(TestConstants.ONE,cust.generateAccountID());
    	
    	assertEquals(TestConstants.TWO,cust.generateAccountID());
    	
    	Customer cust2 = new Customer(TestConstants.CUSTOMER2);
    	assertEquals(TestConstants.THREE, cust2.generateAccountID());
    }

    @Test
    public void testAddAccountNumberToList() {
    	assertEquals(TestConstants.ZERO, Customer.getAccountIDList().size());
    	
    	cust.addAccountNumberToList(TestConstants.ONE);
    	assertEquals(TestConstants.ONE, Customer.getAccountIDList().size());     
    }

    @Test
    public void testAddToAccountList() {
    	assertEquals(TestConstants.ZERO, cust.getAccountList().size());
    	
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	assertEquals(TestConstants.ONE, cust.getAccountList().size());
       
    }
    
    @Test
    public void testGetNumberOfAccounts() {
    	assertEquals(TestConstants.ZERO, cust.getNumberOfAccounts());
    	
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	assertEquals(TestConstants.ONE, cust.getNumberOfAccounts());
    }
    
    @Test
    public void testDeposit() {
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	assertEquals(TestConstants.SUCCESSFUL_DEPOSIT, cust.deposit(TestConstants.FIRST_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT));
    }
    
    @Test
    public void testWithdrawal() {
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	assertEquals(TestConstants.INSUFFICIENT_FUNDS, cust.withdrawal(TestConstants.FIRST_ACCOUNT_ID, TestConstants.WITHDRAWAL_AMOUNT));
    	
    	cust.deposit(1, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.SUCCESSFUL_WITHDRAWAL, cust.withdrawal(TestConstants.FIRST_ACCOUNT_ID, TestConstants.WITHDRAWAL_AMOUNT));
    }
    
    @Test
    public void testTotalInterestEarned() {
    	assertEquals(TestConstants.ZERO_BD, cust.totalInterestEarned());
    	
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	assertEquals(TestConstants.ZERO_BD, cust.totalInterestEarned());
    	
    	cust.deposit(TestConstants.FIRST_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.CHECKING_ACCOUNT_TOTAL_INTEREST_EARNED, cust.totalInterestEarned());
    	
    	cust.openAccount(TestConstants.MAXISAVINGS_ACCOUNT);
    	cust.deposit(TestConstants.SECOND_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.CHECKING_ACCOUNT_AND_MAXISAVINGS_TOTAL_INTEREST_EARNED, cust.totalInterestEarned());
    }
    
    @Test
    public void testGetStatement() {
    	assertEquals(TestConstants.EMPTY_STATEMENT, cust.getStatement());
    	
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	assertEquals(TestConstants.ONE_ACCOUNT_NO_TRANSACTIONS, cust.getStatement());
    	
    	cust.openAccount(TestConstants.SAVINGS_ACCOUNT);
    	assertEquals(TestConstants.TWO_ACCOUNT_NO_TRANSACTIONS, cust.getStatement());
    	
    	cust.deposit(TestConstants.FIRST_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.TWO_ACCOUNT_ONE_DEPOSIT_FIRST_HALF + cust.getAccountList().get(0).getDepositList().get(0).getDepositDate() + TestConstants.TWO_ACCOUNT_ONE_DEPOSIT_SECOND_HALF, cust.getStatement());
    	
    	cust.withdrawal(TestConstants.FIRST_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.TWO_ACCOUNT_ONE_DEPOSIT_AND_WITHDRAWAL_FIRST_THIRD + cust.getAccountList().get(0).getDepositList().get(0).getDepositDate() + TestConstants.TWO_ACCOUNT_ONE_DEPOSIT_AND_WITHDRAWAL_SECOND_THIRD + cust.getAccountList().get(0).getWithdrawalList().get(0).getWithdrawalDate() + TestConstants.TWO_ACCOUNT_ONE_DEPOSIT_AND_WITHDRAWAL_LAST_THIRD, cust.getStatement());
    }
    
    @Test
    public void testGenerateDepositStatement() {
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	cust.deposit(TestConstants.FIRST_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.DEPOSIT_STATEMENT_FIRST_HALF + cust.getAccountList().get(0).getDepositList().get(0).getDepositDate() + TestConstants.DEPOSIT_STATEMENT_SECOND_HALF, cust.generateDepositStatement(cust.accountFinder(TestConstants.FIRST_ACCOUNT_ID)));
    }
    
    @Test
    public void testGenerateWithdrawalStatement() {
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	cust.deposit(TestConstants.FIRST_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	cust.withdrawal(TestConstants.FIRST_ACCOUNT_ID, TestConstants.WITHDRAWAL_AMOUNT);
    	assertEquals(TestConstants.WITHDRAWAL_STATEMENT_FIRST_HALF + cust.getAccountList().get(0).getWithdrawalList().get(0).getWithdrawalDate() + TestConstants.WITHDRAWAL_STATEMENT_SECOND_HALF, cust.generateWithdrawalStatement(cust.accountFinder(TestConstants.FIRST_ACCOUNT_ID)));
    }
    
    @Test
    public void testTransferBetweenAccounts() {
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	cust.deposit(TestConstants.FIRST_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.BALANCE_AFTER_DEPOSIT, cust.accountFinder(TestConstants.FIRST_ACCOUNT_ID).getBalance());
    	
    	cust.openAccount(TestConstants.CHECKING_ACCOUNT);
    	cust.deposit(TestConstants.SECOND_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
    	assertEquals(TestConstants.BALANCE_AFTER_DEPOSIT, cust.accountFinder(TestConstants.SECOND_ACCOUNT_ID).getBalance());
    	
    	assertEquals(TestConstants.SUCCESSFUL_TRANSFER, cust.transferBetweenAccounts(TestConstants.FIRST_ACCOUNT_ID, TestConstants.SECOND_ACCOUNT_ID, TestConstants.TRANSFER_AMOUNT));
    	
    	assertEquals(TestConstants.INSUFFICIENT_TRANSFER_FUNDS, cust.transferBetweenAccounts(TestConstants.FIRST_ACCOUNT_ID, TestConstants.SECOND_ACCOUNT_ID, TestConstants.TRANSFER_AMOUNT));
    }
    
    @Test
    public void testAccountFinder() {
    	assertEquals(null, cust.accountFinder(1));
    	
    	Checking_Account chAcc = new Checking_Account(TestConstants.CHECKING_ACCOUNT, TestConstants.FIRST_ACCOUNT_ID);
    	cust.getAccountList().add(chAcc);
    	assertEquals(chAcc, cust.accountFinder(TestConstants.FIRST_ACCOUNT_ID));
    	
    	Checking_Account chAcc2 = new Checking_Account(TestConstants.CHECKING_ACCOUNT, TestConstants.SECOND_ACCOUNT_ID);
    	cust.getAccountList().add(chAcc2);
    	assertEquals(chAcc2, cust.accountFinder(TestConstants.SECOND_ACCOUNT_ID));
    }
    
    @After
    public void tearDown() throws Exception {
		cust = null;
		Customer.getAccountIDList().clear();;
	}
}

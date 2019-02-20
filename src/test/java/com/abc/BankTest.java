package com.abc;

import org.junit.Before;
import org.junit.Test;

import consts.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BankTest {
	
	private final String fullNamePeter = "Peter Yellow";
	private final double amount = 1500;
	private Bank myBank = new Bank("bank");
	Customer customerPeter;
	
	@Before
	public void setUpBank() {
		Account checkingAccount = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.UK);
    	Account savingsAccount = new Account(Constants.ACCOUNT_SAVINGS_ID, Locale.UK);
    	Account maxiSavingsAccount = new Account(Constants.ACCOUNT_MAXI_SAVINGS_ID, Locale.ITALY);
    	List<Account> accounts = new ArrayList<Account> ();
    	checkingAccount.deposit(amount); savingsAccount.deposit(amount); maxiSavingsAccount.deposit(amount);
    	accounts.add(checkingAccount); accounts.add(savingsAccount); accounts.add(maxiSavingsAccount);
    	Customer john = new Customer("John Black", accounts);
    	Account peterAccount = new Account(Constants.ACCOUNT_SAVINGS_ID, Locale.UK);
    	customerPeter = new Customer(fullNamePeter, peterAccount);
    	myBank.addCustomer(john);
    	myBank.addCustomer(customerPeter);
    	// 10 days of interests
    	for (int i=0; i<10; i++) {
    		checkingAccount.accrueInterests();
    		savingsAccount.accrueInterests();
    		maxiSavingsAccount.accrueInterests();
    	}
	}
	
	
	@Test
    public void testSummaryBank() {
		String summary = "Customer Summary\n" + 
				" - John Black (3 accounts)\n" + 
				" - Peter Yellow (1 account)";
        assertEquals(summary, myBank.customerSummary());
    }
    
	@Test
    public void testGetCustomerByFullName_testGetAccountByCustomerName_testStatementTotalInterestPaidBank() {
		double peterAmount = 2000;
		Customer peter = myBank.getCustomerByFullName(fullNamePeter);
		assertEquals(customerPeter, peter);
		String customerAccountsInfo = peter.getInformationOnCustomerAccounts(); 
		System.out.println(customerAccountsInfo);
		int id = Integer.valueOf(customerAccountsInfo.split("\n")[2].split(": ")[1]);
        assertTrue(customerAccountsInfo.contains("ID: "+id));
        Account peterAccount = peter.getAccountById(id);
        peterAccount.deposit(peterAmount);
        peterAccount.accrueInterests();
        String totalInterestPaid = myBank.totalInterestPaid();
        System.out.print(totalInterestPaid);
        
        double interestEuro = ((amount * Constants.FIVE_PERCENT) / 365) * 10;
        
        double interestChecking = ((amount * Constants.ZERO_ONE_PERCENT) / 365) * 10;
        double interestSavings = ((1 + (amount-1000) * Constants.ZERO_TWO_PERCENT) / 365) * 10;
        double peterInterest = peterAccount.getInterestEarned();
        double interestPounds = interestChecking + interestSavings + peterInterest;
		
		assertTrue(totalInterestPaid.contains(interestEuro+""));
		assertTrue(totalInterestPaid.contains(interestPounds+""));
	}
    
}

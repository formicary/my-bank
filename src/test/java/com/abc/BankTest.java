package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    //private static final double DOUBLE_DELTA = 1e-15;

    //---------TESTS FOR CUSTOMER SUMMARY METHOD-------------
    @Test
    public void singleAccountCustomerSummery() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	john.openCheckingAccount();
    	bank.addCustomer(john);
    	
    	assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void multipleAccountCustomerSummery() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	john.openCheckingAccount();
    	john.openMaxiSavingAccount();
    	john.openSavingAccount();
    	bank.addCustomer(john);
    	
    	assertEquals("Customer Summary\n - John (3 accounts)", bank.customerSummary());
    }
    
    @Test
    public void multipleCustomersSummery() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	Customer bill = new Customer("Bill");
    	bank.addCustomer(john);
    	bank.addCustomer(bill);
    	
    	System.out.println(bank.customerSummary());
    	
    	assertEquals("Customer Summary\n - John (0 accounts)\n - Bill (0 accounts)", bank.customerSummary());
    }
    
    @Test
    public void multipleAccountsMultipleCustomersSummery() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	Customer bill = new Customer("Bill");
    	bank.addCustomer(john);
    	bank.addCustomer(bill);
    	john.openCheckingAccount();
    	john.openCheckingAccount();
    	bill.openMaxiSavingAccount();
    	    	
    	assertEquals("Customer Summary\n - John (2 accounts)\n - Bill (1 account)", bank.customerSummary());
    }
    
   //---------TESTS FOR INTEREST SUMMARY METHOD-------------
    @Test
    public void interestSummaryNoInterest() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	Customer bill = new Customer("Bill");
    	bank.addCustomer(john);
    	bank.addCustomer(bill);
    	john.openCheckingAccount();
    	john.openCheckingAccount();
    	bill.openMaxiSavingAccount();
    	
    	System.out.println(bank.interestSummary());
    	
    	assertEquals("Interest Summary\n" + 
    	" - John $0.0\n" + 
    	"  Checking Account 0.0\n" + 
    	"  Checking Account 0.0\n" + 
    	"\n" + 
    	" - Bill $0.0\n" + 
    	"  Maxi-Saving Account 0.0\n" + 
    	"\n" + 
    	"Total Interest $0.0", bank.interestSummary());
    	
    	
    }

}

package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class BankTest {
	
    private static final double DOUBLE_DELTA = 1e-15;

    @Test 
    //Customer summary test 1: one customer, one account.
    public void customerSummary1() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Checking());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test 
    //Customer summary test 2: two customers, multiple accounts per customer.
    public void customerSummary2() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer mark = new Customer("Mark");
        john.openAccount(new Checking());
        john.openAccount(new Savings());
        mark.openAccount(new Checking());
        mark.openAccount(new Savings());
        mark.openAccount(new Maxisavings());
        bank.addCustomer(john);
        bank.addCustomer(mark);

        assertEquals("Customer Summary\n " + 
        		    "- John (2 accounts)\n " +
        		    "- Mark (3 accounts)", bank.customerSummary());
    }
    
    @Test 
    //Test total interests paid for CHECKING account.
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Checking();
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test 
    //SAVING accounts total interests test 1: total amount < 1000.
    public void savingsAccount1() {
        Bank bank = new Bank();
        Account savingsAccount = new Savings();
        Customer bill = new Customer("Bill");
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);
        
        savingsAccount.deposit(500.0);

        assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test 
    //SAVING accounts total interests test 2: total amount = 1000.
    public void savingsAccount2() {
        Bank bank = new Bank();
        Account savingsAccount = new Savings();
        Customer bill = new Customer("Bill");
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);
        
        savingsAccount.deposit(1000.0);

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test 
    //SAVING accounts total interests test 3: total amount > 1000.
    public void savingsAccount3() {
        Bank bank = new Bank();
        Account savingsAccount = new Savings();
        Customer bill = new Customer("Bill");
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);
        
        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test 
    //MAXI_SAVINGS account total interests test 1: no withdrawal in past 10 days.
    public void maxiSavingsAccount1() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Maxisavings();
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test 
    //MAXI_SAVINGS account total interests test 2: withdrawal in past 10 days.
    public void maxiSavingsAccount2() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Maxisavings();
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(500.0);

        assertEquals(2.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    //MAXI_SAVINGS account total interests test 3: withdrawal before past 10 days.
    public void maxiSavingsAccount3() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Maxisavings();
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(500.0);
        List<Transaction> record = maxiSavingsAccount.getTransactions();
        Transaction withdrawal = record.get(record.size()-1);
        withdrawal.setTime();
        
        assertEquals(125.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    //Test total interests for mixture of different types of account.
    public void totalInterestsTest1() {
    	Bank bank = new Bank();
    	Account checkingAccount = new Checking();
    	Account savingsAccount = new Savings();
        Account maxiSavingsAccount = new Maxisavings();
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bill.openAccount(savingsAccount);
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);
        
        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(1500.0);
        maxiSavingsAccount.deposit(3000.0);
        
        assertEquals(153.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    //Test total interests for multiple clients with multiple accounts.
    public void totalInterestsTest2() {
    	Bank bank = new Bank();
    	Account billCA = new Checking();
    	Account billSA = new Savings();
        Account billMSA = new Maxisavings();
        Account johnCA = new Checking();
        Account johnSA = new Savings();
        Customer bill = new Customer("Bill");
        Customer john = new Customer("John");
        bill.openAccount(billCA);
        bill.openAccount(billSA);
        bill.openAccount(billMSA);
        john.openAccount(johnCA);
        john.openAccount(johnSA);
        bank.addCustomer(bill);
        bank.addCustomer(john);
        
        billCA.deposit(1000.0);
        billSA.deposit(1500.0);
        billMSA.deposit(3000.0);
        johnCA.deposit(2000.0);
        johnSA.deposit(2000.0);
        
        assertEquals(158.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    

}

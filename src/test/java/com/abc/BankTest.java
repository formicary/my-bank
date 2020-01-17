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

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    

    @Test
    public void savings_account1() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void saving_account2() {
        Bank bank = new Bank();
        
        Account savingAccountAlice = new Account(Account.SAVINGS);
        Account savingAccountBob = new Account(Account.SAVINGS);
        
        Customer alice = new Customer("Alice").openAccount(savingAccountAlice);
        Customer bob = new Customer("Bob").openAccount(savingAccountBob);
       
        bank.addCustomer(alice);
        bank.addCustomer(bob);
        
        savingAccountAlice.deposit(400.0);
        savingAccountBob.deposit(500.0);        

        assertEquals(0.9, bank.totalInterestPaid(), DOUBLE_DELTA);
    }


    @Test
    public void maxi_Savings_Account1() {
        Bank bank = new Bank();
        Account maxi_savings = new Account(Account.MAXI_SAVINGS);
        Customer Bill= new Customer("Bill");
        bank.addCustomer(Bill);
        Bill.openAccount(maxi_savings);
        maxi_savings.withdraw(100.0);
        maxi_savings.deposit(3000.0);
        
        assertEquals(145.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    
    @Test
    public void maxi_Savings_Account2() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.withdraw(3000.0);
        maxiSavingsAccount.deposit(4000.0);

        assertEquals(50.0, bank.totalInterestPaid(), DOUBLE_DELTA);

    }
    
  
    @Test
    public void maxi_Savings_Account3() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        
        maxiSavingsAccount.deposit(4000.0);
        maxiSavingsAccount.withdraw(3000.0);
      

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);

    }
    
    @Test 
    public void checkFirstCustomer()	 {
    	Bank bank = new Bank();
    	bank.addCustomer(new Customer("Alice"));
    	bank.addCustomer(new Customer("Bob"));
    	bank.addCustomer(new Customer("Charlie"));
    	
    	assertEquals("Alice", bank.getFirstCustomer());
    	
    	
    }
    
    public void checkCustomerSummary()	{
    	Bank bank = new Bank();
    	
    
    	Account checkingAccountAlice = new Account(Account.CHECKING);
    	Account savingsAccountAlice = new Account(Account.SAVINGS);
    	
    	
    	Account savingsAccountBob   = new Account(Account.SAVINGS);
    
    	 
        Customer alice = new Customer("Alice");
        alice.openAccount(checkingAccountAlice);
        alice.openAccount(savingsAccountAlice);
        
        Customer bob = new Customer("Bob");
        bob.openAccount(savingsAccountBob);
        
        bank.addCustomer(alice);
        bank.addCustomer(bob);
        
        assertEquals("\n - " + "Alice" + " (" + "2 " + "accounts" + "\n - " + "Bob" + " (" + "1 " + "account", bank.customerSummary());
       
    }
    
    
    
    
    

}
    
    
   
    
	



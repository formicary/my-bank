package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
    	//Arrange
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer george = new Customer("George");
        
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        george.openAccount(new Account(Account.MAXI_SAVINGS));
        
        bank.addCustomer(john);
        bank.addCustomer(george);
        
        //Assert
        assertEquals("Customer Summary\n - John (2 accounts)\n - George (1 account)", bank.getCustomerSummary());
    }

    @Test
    public void getTotalInterestCheckingAccount() {
    	//Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        
        //Act
        checkingAccount.deposit(5000.0);
        
        //Assert
        assertEquals(5.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }
   

    @Test
    public void getTotalInterestSavingsAccount() {
        //Arrange
    	Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        
        //Act
        savingsAccount.deposit(5000.0);
        
        //Assert
        assertEquals(9.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test //More than 10 days since last withdrawal
    public void getTotalInterestMaxiSavingsAccountMoreThan10Days() {
        //Arrange
    	Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        
        //Act
        maxiSavingsAccount.deposit(5000.0);
        
        //Assert
        assertEquals(250.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test //Less than 10 days since last withdrawal
    public void getTotalInterestMaxiSavingsAccountLessThan10Days() {
        //Arrange
    	Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        
        //Act
        maxiSavingsAccount.deposit(6000.0);
        maxiSavingsAccount.withdraw(1000.0);
        
        //Assert
        assertEquals(5.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

}

package com.abc;

//Imports
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BankTest {
	//Constant for assertEquals method on double data types
    private static final double DOUBLE_DELTA = 1e-15;

    //Test for a customer summary where the bank has one customer with one account
    @Test
    public void customerSummaryOneCOneA() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    //Test for a customer summary where the bank has one customer with more than one account
    @Test
    public void customerSummaryOneCTwoA() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	john.openAccount(new Account(Account.CHECKING));
    	john.openAccount(new Account(Account.SAVINGS));
    	bank.addCustomer(john);
    	
    	assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }
    
    //Test for a customer summary where the bank has more than one customer with 1 account
    @Test
    public void customerSummaryTwoCOneA() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	Customer bill = new Customer("Bill");
    	john.openAccount(new Account(Account.CHECKING));
    	bill.openAccount(new Account(Account.SAVINGS));
    	bank.addCustomer(john);
    	bank.addCustomer(bill);
    	
    	assertEquals("Customer Summary\n - John (1 account)\n - Bill (1 account)", bank.customerSummary());
    }

    //Test to assert the interest accrued for a checking account is correct
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(100.0);

        assertEquals(0.1 / DateProvider.daysInYear(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    //Test to assert the interest accrued for a savings account with less than 1000.0 is correct
    @Test
    public void savingsAccountLessThanOneThousand() {
    	Bank bank = new Bank();
    	Account savingsAccount = new Account(Account.SAVINGS);
    	bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
    	
    	savingsAccount.deposit(500.0);
    	
    	assertEquals(0.5 / DateProvider.daysInYear(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //Test to assert the interest accrued for a savings account with more than 1000.0 is correct
    @Test
    public void savingsAccountMoreThanOneThousand() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0 / DateProvider.daysInYear(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    //Test to assert the interest accrued for a maxi savings account with no withdrawals in the past 10 days is correct
    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0 / DateProvider.daysInYear(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //Test to assert the interest accrued for a maxi savings account with at least one withdrawal in the past 10 days is correct
    @Test
    public void maxiSavingsAccountWithWithdrawal() {
    	Bank bank = new Bank();
    	Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
    	bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
    	
    	maxiSavingsAccount.deposit(3000.0);
    	maxiSavingsAccount.withdraw(1000.0);
    	
    	assertEquals(2.0 / DateProvider.daysInYear(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

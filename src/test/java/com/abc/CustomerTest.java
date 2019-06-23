package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void statementGeneration(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

   
    @Test
    public void openOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void openTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount())
                .openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    @Test
    public void transfer()
    {
    	Account ac1 = new SavingsAccount();
    	ac1.deposit(1000.0);
    	
    	Account ac2 = new CheckingAccount();
    	ac2.deposit(5.0);
    	
        Customer oscar = new Customer("Oscar")
                .openAccount(ac1)
                .openAccount(ac2);
        
        oscar.Transfer(0, 1, 500.0);
        
        assertEquals(oscar.getAccount(0).sumTransactions(), 500.0, TestUtils.DELTA_MONEY);
        assertEquals(oscar.getAccount(1).sumTransactions(), 505.0, TestUtils.DELTA_MONEY);
    }
    @Test(expected = IllegalArgumentException.class)
    public void transferToNonExistingAccount()
    {
    	Account ac1 = new SavingsAccount();
    	ac1.deposit(1000.0);
    	
    	Account ac2 = new CheckingAccount();
    	ac2.deposit(5.0);
    	
        Customer oscar = new Customer("Oscar")
                .openAccount(ac1)
                .openAccount(ac2);
        
        oscar.Transfer(0, 5, 500.0);
    }

}

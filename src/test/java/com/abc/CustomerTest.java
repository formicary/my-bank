package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){
    		Bank bank = new Bank();
    		Customer henry = new Customer("Henry");
    		bank.addCustomer(henry);
        checkingAccount checkingAccount = new checkingAccount(henry);
        savingsAccount savingsAccount = new savingsAccount(henry);

        henry.openAccount(checkingAccount, bank).openAccount(savingsAccount, bank);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  interest $0.10\n" +
                "Total $100.10\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  interest $4.00\n" +
                "Total $3,804.00\n" +
                "\n" +
                "Total In All Accounts $3,904.10", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
    		Bank bank = new Bank();
    		Customer oscar = new Customer("Oscar");
    		bank.addCustomer(oscar);
    		oscar.openAccount(new savingsAccount( oscar), bank);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
    		
    		Bank bank = new Bank();
		Customer oscar = new Customer("Oscar");
		bank.addCustomer(oscar);
		oscar.openAccount(new savingsAccount(oscar), bank).openAccount(new checkingAccount(oscar), bank);
        
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
    	Bank bank = new Bank();
		Customer oscar = new Customer("Oscar");
		bank.addCustomer(oscar);
		oscar.openAccount(new savingsAccount( oscar), bank).openAccount(new checkingAccount(oscar), bank).openAccount(new maxisavingsAccount(oscar), bank);
        
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

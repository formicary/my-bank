package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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

    // Test to validate opening of One account for a customer
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    // Test to validate opening of Two accounts for a customer
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    // Test to validate opening of Two accounts for a customer
    @Ignore
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    //Test transfer of money from one account to another by a Customer
    @Test
    public void transferMoney(){
    	Bank bank = new Bank();
        Customer casey = new Customer("Casey");
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        casey.openAccount(checkingAccount);
        casey.openAccount(savingAccount);
        checkingAccount.deposit(1000);
        savingAccount.deposit(2200);
        bank.addCustomer(casey);
        
        Account fromAccount = null;
        Account toAccount = null;
        int transferAmount = 500;
        
        if(casey.isAccountValid(casey.getAccounts().get(0).getAccountNumber())!= null)
        	fromAccount = casey.getAccounts().get(0);
        
        if(casey.isAccountValid(casey.getAccounts().get(1).getAccountNumber()) != null)
        	toAccount = casey.getAccounts().get(1);
        
        fromAccount.withdraw(transferAmount);
        toAccount.deposit(transferAmount);
        
        String accountSummary = "Statement for Casey\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $500.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $2,200.00\n" +
                "  deposit $500.00\n" +
                "Total $2,700.00\n" +
                "\n" +
                "Total In All Accounts $3,200.00";
        
        assertEquals(accountSummary, casey.getStatement());
    }
    
    // Test designed to test the simple interest earned 
    @Test
    public void simpleInterestEarned(){
    	Bank bank = new Bank();
        Customer ramona = new Customer("Ramona");
        Account checkingAccount = new Account(Account.CHECKING);
        ramona.openAccount(checkingAccount);
        checkingAccount.deposit(1500);
        bank.addCustomer(ramona);
        
        Account savingsAccount = new Account(Account.SAVINGS);
        ramona.openAccount(savingsAccount);
        savingsAccount.deposit(3200);
        
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        ramona.openAccount(maxiSavingsAccount);
        maxiSavingsAccount.deposit(4000);
        
        assertEquals("1.5", ""+checkingAccount.interestEarned());
        assertEquals("5.4", ""+savingsAccount.interestEarned());
        assertEquals("270.0", ""+maxiSavingsAccount.interestEarned());
        
    }
    
    // Test designed to test the compound interest earned  
    @Test
    public void compoundInterestEarned(){
    	Bank bank = new Bank();
        Customer joe = new Customer("Joe");
        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
        joe.openAccount(maxiSavingAccount);
        maxiSavingAccount.deposit(5200);
        bank.addCustomer(joe);
        
        Account savingsAccount = new Account(Account.SAVINGS);
        joe.openAccount(savingsAccount);
        savingsAccount.deposit(3200);
        
        Account checkingAccount = new Account(Account.CHECKING);
        joe.openAccount(checkingAccount);
        checkingAccount.deposit(1500);
        
        assertEquals("1.5007481932059363", ""+checkingAccount.compoundInterestEarned());
        assertEquals("5.404889651356825", ""+savingsAccount.compoundInterestEarned());
        assertEquals("407.96677867231483", ""+maxiSavingAccount.compoundInterestEarned());
        
    }
}



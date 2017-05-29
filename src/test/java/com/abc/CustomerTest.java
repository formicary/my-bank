package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import com.abc.account.AccountI;
import com.abc.account.Checking;
import com.abc.account.Saving;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

    	StringBuilder statment= new StringBuilder();
    	
        AccountI checkingAccount = new Checking();
        AccountI savingsAccount = new Saving();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        statment.append("\nStatement for Henry\n") 
        .append("\n" )
        .append("Checking Account\n") 
        .append("  deposit $100.00\n" )
        .append("Total $100.00\n" )
        .append("\n") 
        .append( "Savings Account\n" )
        .append("  deposit $4,000.00\n" )
        .append("  withdrawal $200.00\n" )
        .append("Total $3,800.00\n" )
        .append("\n" )
        .append("Total In All Accounts $3,900.00");
        
        assertEquals(statment.toString(),henry.getStatement().toString());

          
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Saving());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Saving());
        oscar.openAccount(new Checking());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    
}

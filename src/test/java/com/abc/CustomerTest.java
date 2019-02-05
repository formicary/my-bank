package com.abc;

import com.abc.Exceptions.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    
            private static final double DOUBLE_DELTA = 1e-15;


    @Test //Test customer statement generation
    public void testApp() throws NotEnoughFundsAvailableException{

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    @Test
    public void testThreeAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
 
    @Test
    public void transferBetweenAccounts() throws NotEnoughFundsAvailableException{
        Customer oscar = new Customer("Oscar");
        Account checking = new Account(AccountType.CHECKING);
        Account savings = new Account(AccountType.SAVINGS);
        oscar.openAccount(checking);
        oscar.openAccount(savings);

        checking.deposit(200.0);
        
        oscar.transferFundsToAccount(checking, savings, 45.0);
        
        assertEquals(155.0,checking.sumTransactions(),DOUBLE_DELTA);
        
        assertEquals(45.0,savings.sumTransactions(),DOUBLE_DELTA);

        
    }
    
    


}

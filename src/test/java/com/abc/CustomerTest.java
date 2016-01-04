package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CustomerTest {

    private static final double DELTA = 1e-15;
    
    @Test
    public void createsStatementCorrectly(){

        Customer henry = new Customer("Henry");
        Account checkingAccount = new Account(henry,Account.CHECKING);
        Account savingsAccount = new Account(henry,Account.SAVINGS);
        henry.openAccount(checkingAccount).openAccount(savingsAccount);
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
    public void openingAccountIncrementsNumberOfAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(oscar,Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
        oscar.openAccount(new Account(oscar,Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
        oscar.openAccount(new Account(oscar,Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void getCustomerName() {
      Customer oscar = new Customer("Oscar");
      assertEquals(oscar.getName(),"Oscar");
    }
    
    @Test
    public void calculateInterestPaidCorrectly() {
      Customer oscar = new Customer("Oscar");
      Account checking = new Account(oscar,Account.CHECKING); 
      Account savings = new Account(oscar,Account.SAVINGS);
      Account maxiSavings = new Account(oscar,Account.MAXI_SAVINGS);
      assertEquals(oscar.totalInterestEarned(),0,DELTA);
      oscar.openAccount(checking);
      oscar.openAccount(savings);
      oscar.openAccount(maxiSavings); 
      assertEquals(oscar.totalInterestEarned(),0,DELTA);
      checking.deposit(500);
      assertEquals(oscar.totalInterestEarned(),500*0.001,DELTA);
      savings.deposit(1500);
      assertEquals(oscar.totalInterestEarned(),
                    500*0.001 + 1000*0.001 + 500*0.002,
                    DELTA);
      maxiSavings.deposit(500);
      assertEquals(oscar.totalInterestEarned(),
          500*0.001 + 1000*0.001 + 500*0.002 + 500 * 0.05,
          DELTA);
    }
    
    @Test
    public void testEqualAcounts() {
        Customer oscar = new Customer("Oscar");
        Customer oscar1 = new Customer("Oscar");
        assertNotEquals(oscar, oscar1);
        assertEquals(oscar, oscar);
    }
}

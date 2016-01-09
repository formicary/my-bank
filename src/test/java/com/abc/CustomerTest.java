package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Customer henry = new Customer("Henry");
        henry.openCheckingAccount();
        henry.openSavingsAccount();
        
        henry.depositFunds(0, 100.0);
        henry.depositFunds(1, 4000.0);
        henry.withdrawFunds(1, 200.0);

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
    public void testTransferFund(){

        Customer henry = new Customer("Henry");
        henry.openCheckingAccount();
        henry.openSavingsAccount();
        
        henry.depositFunds(0, 100.0);
        henry.depositFunds(1, 4000.0);
        double amountToTransfer = 400.0;
        henry.transferFundFundFrom(1, 0, amountToTransfer);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $400.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $400.00\n" +
                "Total $3,600.00\n" +
                "\n" +
                "Total In All Accounts $4,100.00", henry.getStatement());
    }
    
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openSavingsAccount();
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openSavingsAccount();
        oscar.openCheckingAccount();
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openSavingsAccount();
        oscar.openCheckingAccount();
        oscar.openCheckingAccount();
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

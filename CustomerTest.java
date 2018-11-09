package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){
        Account checkingACC = new Account(Account.CHECKING);
        Account savingsACC = new Account(Account.SAVINGS);
        Account MAxiSACC = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry")
                .openAccount(checkingACC)
                .openAccount(savingsACC)
                .openAccount(MAxiSACC);

        checkingACC.deposit(100.0);
        savingsACC.deposit(4000.0);
        savingsACC.withdraw(200.0);
        MAxiSACC.deposit(2000.0);
        savingsACC.ACCTransfer(1000.0, MAxiSACC);//tests to see if the funds are transfered from the savings account to the Maxi_savings acount.
        
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                " Deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                " Deposit $4,000.00\n" +
                " withdrawal $200.00\n" +
                " transfer to Maxi Savings Account $1,000.00\n" +
                "Total $2,800.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                " Deposit $2,000.00\n" +
                " transfer from Savings Account $1,000.00\n" +
                "Total $3,000.00\n" +
                "\n" +
                "Total In All Accounts $5,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

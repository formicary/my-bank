package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

            try {
                checkingAccount.deposit(100.0);
                savingsAccount.deposit(4000.0);
                savingsAccount.withdraw(200.0);
                henry.transfer(500, henry.getAccount(1), henry.getAccount(0));  // test for transfer
                henry.transfer(500, checkingAccount, savingsAccount);
            }
            catch (InvalidAmountException iae) {
                System.out.println(iae.getMessage());
            }


        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $500.00\n" +
                "  withdrawal $500.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  withdrawal $500.00\n" +
                "  deposit $500.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }



    @Test
    public void testMaxi() {

        Account maxiAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry").openAccount(maxiAccount);

        try {
            maxiAccount.deposit(100.0);
            maxiAccount.deposit(150.0);
            maxiAccount.deposit(50.0);
            maxiAccount.withdraw(50.0);

        } catch (InvalidAmountException iae) {
            System.out.println(iae.getMessage());
        }

        maxiAccount.interestEarned();


    }

}

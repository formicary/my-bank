package com.abc;

import org.junit.Test;

import com.abc.Account.Type;

import static org.junit.Assert.assertEquals;

import java.util.Random;

/**
 * Tester for the methods of the Customer class.
 * @author Filippos Zofakis
 */
public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-10;

    @Test // Test customer statement generation.
    public void testStatementGeneration() {
        Account checkingAccount = new Account(Type.CHECKING);
        Account savingsAccount = new Account(Type.SAVINGS);

        Customer henry = new Customer("Henry", "Tudor");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry Tudor:\n" +
                "\n" +
                "Checking account:\n" +
                "- deposit $100.00\n" +
                "Total interest accrued: $0.00\n" +
                "Account total: $100.00\n" +
                "\n" +
                "Savings account:\n" +
                "- deposit $4,000.00\n" +
                "- withdrawal $200.00\n" +
                "Total interest accrued: $0.00\n" +
                "Account total: $3,800.00\n" +
                "\n" +
                "Total across all accounts: $3,900.00", henry.getFullStatement());
        
        System.out.println("Statement generation works correctly.");
    }

    @Test // Test customer creation.
    public void testCustomerCreation() {
        Random rng = new Random();

        for (int numCustomers = 0; numCustomers < rng.nextInt(100); numCustomers++) {
            Customer oscar = new Customer("Oscar", "Wilde");

            int totalNumAccounts = rng.nextInt(100);
            for (int numAccounts = 0; numAccounts < totalNumAccounts; numAccounts++) {
                oscar.openAccount(new Account(Type.SAVINGS));
            }
            
            assertEquals(totalNumAccounts, oscar.getNumberOfAccounts());
        }
        
        System.out.println("Account creation works correctly.");
    }
    
    @Test // Test transfer functionality.
    public void testTransfer() {
        Customer oscar = new Customer("Oscar", "Wilde");
        
        oscar.openAccount(new Account(Type.CHECKING));
        Account checkingAccount = oscar.getAllAccounts().get(0);
        checkingAccount.deposit(100.10);
        
        Account maxiAccount = oscar.openAccount(new Account(Type.MAXI_SAVINGS)).get(1);
        oscar.transferBetweenAccounts(100.00, checkingAccount, maxiAccount);
        
        assertEquals(checkingAccount.getBalance(), 0.10, DOUBLE_DELTA);
        assertEquals(maxiAccount.getBalance(), 100, DOUBLE_DELTA);
        System.out.println("Transfer between accounts works correctly.");
    }
}

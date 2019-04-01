package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Customer henry = new Customer("Henry")
                                .openAccount(AccountType.CHECKING)
                                .openAccount(AccountType.SAVINGS);


        henry.getAccounts().get(0).deposit(100.0);
        henry.getAccounts().get(1).deposit(4000.0);
        henry.getAccounts().get(1).withdraw(200.0);

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
        Customer oscar = new Customer("Oscar")
                .openAccount(AccountType.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(AccountType.SAVINGS)
                .openAccount(AccountType.CHECKING);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(AccountType.SAVINGS)
                .openAccount(AccountType.CHECKING)
                .openAccount(AccountType.MAXI_SAVINGS);
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void normalTransfer() {
        Customer cust = new Customer("Test Elek")
            .openAccount(AccountType.SAVINGS)
            .openAccount(AccountType.CHECKING);
        cust.getAccounts().get(0).deposit(5000);
        cust.getAccounts().get(1).deposit(1000);
        
        cust.transfer(0, 1, 500);

        assertTrue(cust.getAccounts().get(0).sumTransactions() == 4500);
        assertTrue(cust.getAccounts().get(1).sumTransactions() == 1500);        
    }

    @Test
    public void transferFromInvalidSourceAccount() {
        Customer cust = new Customer("Test Elek")
            .openAccount(AccountType.SAVINGS)
            .openAccount(AccountType.CHECKING);
        cust.getAccounts().get(0).deposit(5000);
        cust.getAccounts().get(1).deposit(1000);
        
        String exceptionMessage = "";
        try {
            cust.transfer(2, 1, 500);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        
        assertTrue(exceptionMessage.equals("Invalid source account index!"));
    }

    @Test
    public void transferToInvalidTargetAccount() {
        Customer cust = new Customer("Test Elek")
            .openAccount(AccountType.SAVINGS)
            .openAccount(AccountType.CHECKING);
        cust.getAccounts().get(0).deposit(5000);
        cust.getAccounts().get(1).deposit(1000);
        
        String exceptionMessage = "";
        try {
            cust.transfer(0, 2, 500);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        
        assertTrue(exceptionMessage.equals("Invalid target account index!"));
    }

    @Test
    public void transferOfNegativeSum() {
        Customer cust = new Customer("Test Elek")
            .openAccount(AccountType.SAVINGS)
            .openAccount(AccountType.CHECKING);
        cust.getAccounts().get(0).deposit(5000);
        cust.getAccounts().get(1).deposit(1000);
        
        String exceptionMessage = "";
        try {
            cust.transfer(0, 1, -500);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }

        assertTrue(exceptionMessage.equals("Only positive values can be transferred!"));
    }

}

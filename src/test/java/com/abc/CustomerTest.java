package com.abc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static Customer customer;

    @Before
    public void setup(){
        customer = new Customer("Customer A");
    }

    @Test
    public void newCustomerHasZeroAccounts(){
        assertEquals("New customer does not have 0 accounts", 0, customer.getNumberOfAccounts());
    }

    @Test
    public void customerCanAddCheckingAccount(){
        customer.openAccount(new Account(0));
        assertEquals("Customer checking account is not added to account list", 1, customer.getNumberOfAccounts());
    }

    @Test
    public void customerCanAddSavingsAccount(){
        customer.openAccount(new Account(1));
        assertEquals("Customer savings account is not added to account list", 1, customer.getNumberOfAccounts());

    }

    @Test
    public void customerCanAddMaxiSavingsAccount(){
        customer.openAccount(new Account(2));
        assertEquals("Customer maxi savings account is not added to account list", 1, customer.getNumberOfAccounts());

    }

    @Test
    public void customerCanAddMultipleCheckingAccount(){
        customer.openAccount(new Account(0));
        customer.openAccount(new Account(0));
        assertEquals("Mutliple customer checking accounts are not added to account list", 2, customer.getNumberOfAccounts());
    }

    @Test
    public void customerCanAddMultipleSavingsAccount(){
        customer.openAccount(new Account(1));
        customer.openAccount(new Account(1));
        assertEquals("Multiple customer savings accounts are not added to account list", 2, customer.getNumberOfAccounts());

    }

    @Test
    public void customerCanAddMultipleMaxiSavingsAccount(){
        customer.openAccount(new Account(2));
        customer.openAccount(new Account(2));
        assertEquals("Multiple customer maxi savings accounts are not added to account list", 2, customer.getNumberOfAccounts());

    }

    @Test
    public void customerCanAddMultipleAccountsOfDifferentTypes(){
        customer.openAccount(new Account(0));
        customer.openAccount(new Account(1));
        customer.openAccount(new Account(2));
        assertEquals("Multiple accounts of different types are not added to account list", 3, customer.getNumberOfAccounts());

    }

//    @Test //Test customer statement generation
//    public void testApp(){
//
//        Account checkingAccount = new Account(Account.CHECKING);
//        Account savingsAccount = new Account(Account.SAVINGS);
//
//        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
//
//        checkingAccount.deposit(100.0);
//        savingsAccount.deposit(4000.0);
//        savingsAccount.withdraw(200.0);
//
//        assertEquals("Statement for Henry\n" +
//                "\n" +
//                "Checking Account\n" +
//                "  deposit $100.00\n" +
//                "Total $100.00\n" +
//                "\n" +
//                "Savings Account\n" +
//                "  deposit $4,000.00\n" +
//                "  withdrawal $200.00\n" +
//                "Total $3,800.00\n" +
//                "\n" +
//                "Total In All Accounts $3,900.00", henry.getStatement());
//    }
//
//    @Test
//    public void testOneAccount(){
//        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
//        assertEquals(1, oscar.getNumberOfAccounts());
//    }
//
//    @Test
//    public void testTwoAccount(){
//        Customer oscar = new Customer("Oscar")
//                .openAccount(new Account(Account.SAVINGS));
//        oscar.openAccount(new Account(Account.CHECKING));
//        assertEquals(2, oscar.getNumberOfAccounts());
//    }
//
//    @Ignore
//    public void testThreeAcounts() {
//        Customer oscar = new Customer("Oscar")
//                .openAccount(new Account(Account.SAVINGS));
//        oscar.openAccount(new Account(Account.CHECKING));
//        assertEquals(3, oscar.getNumberOfAccounts());
//    }
}

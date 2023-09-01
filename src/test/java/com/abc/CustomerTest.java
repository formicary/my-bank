package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CustomerTest {
    //Test customer statement generation
    @Test
    public void initialCustomerStatement() {
        Bank bank = new Bank();
        Customer jack = new Customer("Jack");
        bank.addCustomer(jack);
        jack.openAccount(new Account(Account.CHECKING));

        assertEquals("Statement for Jack\nChecking Account\nTotal : $0.00\n\nTotal Of Jack's Accounts : $0.00", jack.getStatement()[0]);
    }

    @Test
    public void statementForAccount() {
        Customer james = new Customer("James");
        Account max_savings = new Account(Account.MAXI_SAVINGS);
        james.openAccount(max_savings);
        max_savings.deposit(150);

        assertEquals("Maxi Savings Account\n  deposit : $150.00\nTotal : $150.00", james.statementForAccount(max_savings));
    }

    @Test
    public void testGetStatement(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "Checking Account\n" +
                "  deposit : $100.00\n" +
                "Total : $100.00\n" +
                "Savings Account\n" +
                "  deposit : $4,000.00\n" +
                "  withdrawal : $200.00\n" +
                "Total : $3,800.00\n" +
                "\n" +
                "Total Of Henry's Accounts : $3,900.00", henry.getStatement()[0]);
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));

        Customer jack = new Customer("Jack").openAccount(new Account(Account.MAXI_SAVINGS));

        assertEquals(2, oscar.getNumberOfAccounts());
        assertEquals(1, jack.getNumberOfAccounts());

    }

    @Test
    public void testFiveAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));

        Customer bill = new Customer("Bill")
                .openAccount(new Account(Account.CHECKING));
        bill.openAccount(new Account(Account.SAVINGS));
        
        assertEquals(3, oscar.getNumberOfAccounts());
        assertEquals(2, bill.getNumberOfAccounts());
    }

    //Transfer between accounts test
    @Test
    public void transferFunds() throws Exception {
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar");
        bank.addCustomer(oscar);

        Account oscarCheckingAccount = new Account(Account.CHECKING);
        Account oscarSavingsAccount = new Account(Account.SAVINGS);
        Account oscarMaxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        oscar.openAccount(oscarCheckingAccount);
        oscar.openAccount(oscarSavingsAccount);
        oscar.openAccount(oscarMaxiSavingsAccount);

        oscarCheckingAccount.deposit(740.86);
        oscarSavingsAccount.deposit(300);
        oscarMaxiSavingsAccount.deposit(250);
        double[] amountInAccounts = oscar.transferFunds(80, oscarSavingsAccount, oscarCheckingAccount);

        assertEquals(220, amountInAccounts[0], 1e-15);
        assertEquals(820.86, amountInAccounts[1] , 1e-15);
    }
}
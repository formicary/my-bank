/*Edited by: Foyaz Hasnath*/
package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation (including interest on balance, as of rolled business date [deposit/withdrawal dates not used currently])
    public void testCustomerStatement(){
        Bank bank = new Bank();
        //Create 2 accounts for customer "Henry"
        Customer henry = new Customer("Henry");
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);
        bank.addCustomer(henry);

        //deposit and withdraw from accounts
        checkingAccount.deposit(100000);
        savingsAccount.deposit(400000);
        savingsAccount.withdraw(20000);

        //roll date
        bank.rollBusinessDate(100);//simulate account having being opened for x days

        //check summary
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100,000.00\n" +
                "Total (less interest): $100,000.00\n" +
                "Interest: "+ "$27.40\n" +
                "Total: $100,027.40\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $400,000.00\n" +
                "  withdrawal $20,000.00\n" +
                "Total (less interest): $380,000.00\n" +
                "Interest: "+ "$208.00\n" +
                "Total: $380,208.00\n" +
                "\n" +
                "Total In All Accounts (including interest): $480,235.40", henry.getStatement(bank));
    }

    @Test
    public void testOneAccount(){
        //create one account for customer "oscar"
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));

        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        //create 2 accounts for customer "oscar"
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));

        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        //create 3 accounts for customer "oscar"
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));

        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

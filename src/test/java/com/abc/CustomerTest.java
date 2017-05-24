package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0, true);
        savingsAccount.deposit(4000.0, true);
        savingsAccount.withdraw(200.0, true);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100,00\n" +
                "Total $100,00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4.000,00\n" +
                "  withdrawal $200,00\n" +
                "Total $3.800,00\n" +
                "\n" +
                "Total In All Accounts $3.900,00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test //Test customer statement generation
    public void testApp2(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0, true);
        savingsAccount.deposit(4000.0, true);
        savingsAccount.withdraw(200.0, true);
        henry.transferMoneyBetweenAccounts(1,0,500);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100,00\n" +
                "  transfer $500,00\n" +
                "Total $600,00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4.000,00\n" +
                "  withdrawal $200,00\n"+
                "  transfer -$500,00\n" +
                "Total $3.300,00\n" +
                "\n" +
                "Total In All Accounts $3.900,00", henry.getStatement());
    }

    @Test
    public void testTransferMoney(){
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer oscar = new Customer("Oscar").openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);
        checkingAccount.deposit(100.0, true);
        savingsAccount.deposit(4000.0, true);
        oscar.transferMoneyBetweenAccounts(0,1,100);
        assertEquals(0,oscar.getAccounts().get(0).moneyIn,0.01);
        assertEquals(4100,oscar.getAccounts().get(1).moneyIn,0.01);

        System.out.print(oscar.getStatement());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

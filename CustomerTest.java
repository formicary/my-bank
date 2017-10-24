package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(new BigDecimal(100.0),true);
        savingsAccount.deposit(new BigDecimal(4000.0),true);
        savingsAccount.withdraw(new BigDecimal(200.0),true);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit    $100.00\n" +
                "  total interest $0.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit    $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  total interest $0.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement(true,true));
    }

    @Test
    public void testOneAccount(){
        Account checkingAccount = new Account(Account.CHECKING);
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(checkingAccount);
        assertEquals(1, oscar.getNumberOfAccounts());

        checkingAccount.deposit(new BigDecimal(1000),true);

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(System.currentTimeMillis());
        date.add(Calendar.YEAR,1);
        oscar.setDate(date);

        System.out.println(oscar.getStatement(false,true));
        assertEquals("Statement for Oscar\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit    $1,000.00\n" +
                "  total interest $1.00\n" +
                "Total $1,001.00\n" +
                "\n" +
                "Total In All Accounts $1,001.00", oscar.getStatement(true,true));
    }

    @Test
    public void testTwoAccount(){
        Customer bob = new Customer("Bob")
                .openAccount(new Account(Account.SAVINGS));
        bob.openAccount(new Account(Account.CHECKING));
        assertEquals(2, bob.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer charlie = new Customer("Charlie");
        charlie.openAccount(new Account(Account.SAVINGS));
        charlie.openAccount(new Account(Account.CHECKING));
        charlie.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, charlie.getNumberOfAccounts());
    }
}

package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;


import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

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

    //my addtion
    @Test
    public void testTotalSaving() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        for (int i = 0; i < oscar.getAccounts().size(); i++) {
            if (oscar.getAccounts().get(i).getAccountType() == 0) {
                oscar.getAccounts().get(i).deposit(10000.0);
            }
        }

        assertEquals(10000.0, oscar.totalSaving(), DOUBLE_DELTA);

    }

    //my addtion
    @Test
    public void testTransferBetweenAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));

        for (int i = 0; i < oscar.getAccounts().size(); i++) {
            if (oscar.getAccounts().get(i).getAccountType() == 0) {
                oscar.getAccounts().get(i).deposit(10000);
            }
        }

        oscar.transferBetweenAccounts(oscar.getAccounts().get(1), oscar.getAccounts().get(0), 10.0);

        assertEquals(10.0, oscar.getAccounts().get(0).sumTransactions(), DOUBLE_DELTA);

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
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testDaysBetween2Dates(){

        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.DATE, -360);

        assertEquals(360, Account.daysBetween(cal.getTime(), Calendar.getInstance().getTime()));
    }
 }

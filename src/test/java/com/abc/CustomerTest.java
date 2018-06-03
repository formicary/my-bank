package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;


import static org.junit.Assert.assertEquals;

/**
 * This class is for testing the methods in the Customer class.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class CustomerTest {

    @Test
    /**
     * This test is created for customer statement generation.
     */
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(new BigDecimal(100.0));
        savingsAccount.deposit(new BigDecimal(4000.0));
        savingsAccount.withdraw(new BigDecimal(200.0));

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
    /**
     * This test was created to test the transferBetweenAccounts function.
     * First, one saving and one checking account is created for the customer.
     * Then, the checking account is deposited 10000 dollars.
     * Finally, the customer moves 10 dollar from the checking account to the saving account.
     */
    public void testTransferBetweenAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));

        for (int i = 0; i < oscar.getAccounts().size(); i++) {
            if (oscar.getAccounts().get(i).getAccountType() == 0) {
                oscar.getAccounts().get(i).deposit(new BigDecimal(10000));
            }
        }

        oscar.transferBetweenAccounts(oscar.getAccounts().get(1), oscar.getAccounts().get(0), 10.0);

        assertEquals(new BigDecimal(10.0), oscar.getAccounts().get(0).sumTransactions());
        assertEquals(new BigDecimal(9990.0), oscar.getAccounts().get(1).sumTransactions());

    }

    @Test
    /**
     * This test was created to test the numberOfAccounts function.
     * In this case, only one account is created for the customer.
     */
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    /**
     * This test was created to test the numberOfAccounts function.
     * In this case, three accounts are created for the customer.
     */
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    /**
     * This test is created to test the daysBetweenDates function.
     * The first parameter of the function input is 360 days ago from now;
     * the second parameter of the function input now.
     */
    public void testDaysBetween2Dates1(){

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.DATE, -360);

        assertEquals(360, Account.daysBetween(cal.getTime(), Calendar.getInstance().getTime()));
    }

    @Test
    /**
     * This test is created to test the daysBetweenDates function.
     * The first parameter of the function input is 360 days ago from now;
     * the second parameter of the function input is 350 days ago from now.
     */
    public void testDaysBetween2Dates2(){

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.DATE, -360);

        GregorianCalendar cal1 = new GregorianCalendar();
        cal1.setTime(Calendar.getInstance().getTime());
        cal1.add(Calendar.DATE, -350);

        assertEquals(10, Account.daysBetween(cal.getTime(), cal1.getTime()));
    }
 }

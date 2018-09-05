/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author prarthana
 */
public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-11;

    @Test
    public void getTotalCheckingAccount() {

        Customer john = new Customer("John");
        Account checkingAccount = new CheckingAccount(john, Account.CHECKING);
        Transaction t = new CreditTransaction(200);
        t.setTransactionDate(getTestDate(-2));
        checkingAccount.addTransaction(t);
        checkingAccount.withdraw(20);

        assertEquals(Math.pow(1 + 0.001 / 365, 2) * 200 - 20, checkingAccount.getTotal(), DOUBLE_DELTA);
    }

    //deposit over $1000 
    @Test
    public void getTotalSavingsAccount() {

        Customer john = new Customer("John");
        Account savingsAccount = new SavingsAccount(john, Account.SAVINGS);
        Transaction t = new CreditTransaction(200);
        t.setTransactionDate(getTestDate(-2));
        savingsAccount.addTransaction(t);
        Transaction t2 = new CreditTransaction(900);
        t2.setTransactionDate(getTestDate(-1));
        savingsAccount.addTransaction(t2);

        double a = 200 * 0.001 / 365 + 200;
        double b = a + 900;
        double c = (1000 * 0.001 / 365) + ((b - 1000) * 0.002 / 365) + b;

        assertEquals(c, savingsAccount.getTotal(), DOUBLE_DELTA);

    }

    @Test
    public void getTotalMaxiSavingsAccount() {
        Customer john = new Customer("John");
        Account maxiAccount = new MaxiSavingsAccount(john, Account.MAXI_SAVINGS);
        Transaction t = new CreditTransaction(100);
        t.setTransactionDate(getTestDate(-3));
        maxiAccount.addTransaction(t);
        Transaction t2 = new DebitTransaction(50);
        t2.setTransactionDate(getTestDate(-1));
        maxiAccount.addTransaction(t2);
        
        double a = Math.pow(1+ 0.05/365, 2)*100;
        double b = a-50;
        double c = b * 0.001/365 + b;
       
        assertEquals(c, maxiAccount.getTotal(), DOUBLE_DELTA);

    }

    private Date getTestDate(int numberOfDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, numberOfDays);
        return c.getTime();
    }

}

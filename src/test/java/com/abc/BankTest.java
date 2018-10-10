package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/** in this class we test the programs ability to construct a new Bank class, 
 * create customers for it and make sure the summaries are printed out correctly.
 * We then check that the correct type of account is created and that for each account
 * type the interest rate applied is correct.
 * Finally a special test case for the maxi savings type as we want to examine whether 
 * a reduced interest rate is applied when a recent withdrawal has been made.
 * In all the interest rate cases bellow the rate has been calculated daily.
 * @author mantom2132
 */
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test//test case to check whether the correct statements are printed out.
    public void customerSummary() {
        Bank bank = new Bank();
        ArrayList<Transaction> testTransactions =  new ArrayList<Transaction>();
        Transaction t,t1,t2,t3;
        t = new Transaction(12, Calendar.getInstance().getTime());
        t1 = new Transaction(-9, Calendar.getInstance().getTime());
        t2 = new Transaction(-3, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING,testTransactions));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test//test case to check whether the correct interest is applied to the checking account
    public void checkingAccount() {
        ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
        Transaction t,t1,t2,t3;
        Date d1 = new Date(06,10,10);
        Date d2 = new Date(06,10,20);
        t = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d1);
        t1 = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d2);
        t2 = new Transaction(0, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING,testTransactions);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        Date d3 = new Date(05,10,12);
        Date d4 = new Date(05,10,13);
        Date d5 = new Date(05,11,25);
        Calendar.getInstance().setTime(d3);
        checkingAccount.deposit(100.0, Calendar.getInstance().getTime());
        assertEquals(0.1, bank.totalInterestPaid(365), 0.01);
    }

    @Test//test case to check whether the correct interest is applied to the savings account.
    public void savings_account() {
        Bank bank = new Bank();        
        ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
        Transaction t,t1,t2,t3;
        Date d1 = new Date(06,10,10);
        Date d2 = new Date(06,10,20);
        t = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d1);
        t1 = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d2);
        t2 = new Transaction(0, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Account checkingAccount = new Account(Account.SAVINGS,testTransactions );
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        Date d3 = new Date(06,12,12);
        Calendar.getInstance().setTime(d3);
        checkingAccount.deposit(1500.0, Calendar.getInstance().getTime());
        assertEquals(2.0, bank.totalInterestPaid(365), 0.01);
    }

    @Test//test case to check whether the correct interest is applied to maxi savings
    public void maxi_savings_account() {
        Bank bank = new Bank();
        ArrayList<Transaction> testTransactions =  new ArrayList<Transaction>();
        Transaction t,t1,t2,t3;
        Date d1 = new Date(06,10,10);
        Date d2 = new Date(06,10,20);
        t = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d1);
        t1 = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d2);
        t2 = new Transaction(0, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Account checkingAccount = new Account(Account.MAXI_SAVINGS, testTransactions);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        Date d3 = new Date(06,10,30);
        Calendar.getInstance().setTime(d3);
        checkingAccount.deposit(3000.0,Calendar.getInstance().getTime());

        assertEquals(153.0, bank.totalInterestPaid(365), 1);
    }
    @Test//special test case for a maxi savings where a recent withdrawal has been made.
    public void maxi_savings_with_recent_withdrawal(){
        Bank bank = new Bank();
        ArrayList<Transaction> testTransactions =  new ArrayList<Transaction>();
        Transaction t,t1,t2,t3;
        Date d1 = new Date(06,10,10);
        Date d2 = new Date(06,10,20);
        t = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d1);
        t1 = new Transaction(0, Calendar.getInstance().getTime());
        Calendar.getInstance().setTime(d2);
        t2 = new Transaction(0, Calendar.getInstance().getTime());
        testTransactions.add(t);
        testTransactions.add(t1);
        testTransactions.add(t2);
        Account checkingAccount = new Account(Account.MAXI_SAVINGS, testTransactions);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        Date d3 = new Date(06,10,30);
        Calendar.getInstance().setTime(d3);
        checkingAccount.deposit(3000.0,Calendar.getInstance().getTime());
        checkingAccount.withdraw(1, Calendar.getInstance().getTime());
        assertEquals(3.0, bank.totalInterestPaid(365),0.01);
    }

}

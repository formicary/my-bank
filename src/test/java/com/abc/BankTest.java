package com.abc;

import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-11;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount(john, Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void twoCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount(john, Account.CHECKING));
        bank.addCustomer(john);

        Customer bill = new Customer("Bill");
        bill.openAccount(new CheckingAccount(bill, Account.CHECKING));
        bill.openAccount(new SavingsAccount(bill, Account.SAVINGS));
        bank.addCustomer(bill);

        assertEquals("Customer Summary\n - John (1 account)\n - Bill (2 accounts)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account checkingAccount = new CheckingAccount(bill, Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(100.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    //Interest paid on $1000 deposit 5 days ago
    @Test
    public void checkingAccount2() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account checkingAccount = new CheckingAccount(bill, Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        Transaction t = new CreditTransaction(1000.0);
        t.setTransactionDate(getTestDate(-5));
        checkingAccount.addTransaction(t);

        assertEquals(Math.pow(1 + 0.001 / 365.0, 5) * 1000 - 1000, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account savingsAccount = new SavingsAccount(bill, Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        savingsAccount.deposit(1500.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    //Interest on savings account under $1000 
    @Test
    public void savings_account2() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account savingsAccount = new SavingsAccount(bill, Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        Transaction t = new CreditTransaction(500.0);
        t.setTransactionDate(getTestDate(-15));
        savingsAccount.addTransaction(t);

        assertEquals(Math.pow(1 + 0.001 / 365, 15) * 500.0 - 500, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    //Interest on savings account over $1000
    public void savings_account3() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account savingsAccount = new SavingsAccount(bill, Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        Transaction t = new CreditTransaction(8000.0);
        t.setTransactionDate(getTestDate(-1));
        savingsAccount.addTransaction(t);

        assertEquals(1000 * 0.001 / 365 + 7000 * 0.002 / 365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account maxiAccount = new MaxiSavingsAccount(bill, Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));
        maxiAccount.deposit(3000.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

   @Test
    public void maxi_savings_account2() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account maxiAccount = new MaxiSavingsAccount(bill, Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));
        Transaction t = new CreditTransaction(2000.0);
        t.setTransactionDate(getTestDate(-3));
        maxiAccount.addTransaction(t);
        Transaction t2 = new DebitTransaction(100);
         t2.setTransactionDate(getTestDate(-1));
        maxiAccount.addTransaction(t2);

        double a = Math.pow(1 + 0.05 / 365, 2) * 2000; //Days 1 & 2 earn 5% interest
        double b = (a - 100) * Math.pow(1+ 0.001 / 365, 1) -1900; // Day 3 has withdrawal so earns 0.1% interest

        assertEquals(b, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //Use current day as test date
    private Date getTestDate(int numberOfDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, numberOfDays);
        return c.getTime();
    }
}

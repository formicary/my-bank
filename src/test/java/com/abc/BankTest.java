package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-10;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void noInterest(){
        Bank bank = new Bank();
        Customer will = new Customer ("Will");
        Account checkaccount = new Account(Account.CHECKING);
        will.openAccount(checkaccount);
        bank.addCustomer(will);

        checkaccount.deposit(new BigDecimal(1000),true);

        Calendar date = Calendar.getInstance();
        checkaccount.withdraw(new BigDecimal(0.01),true);
        checkaccount.deposit(new BigDecimal(0.01),true);
//        checkaccount.withdraw(new BigDecimal(0.01),true);
//        checkaccount.deposit(new BigDecimal(0.01),true);
//        checkaccount.withdraw(new BigDecimal(0.01),true);
//        checkaccount.deposit(new BigDecimal(0.01),true);
        System.out.println("Balance = " + checkaccount.sumTransactions().toString());
        System.out.println("Interest = " + checkaccount.interestEarned(true).toString());
        date.add(Calendar.DAY_OF_MONTH,1);
        bank.setDate(date);

        System.out.println(will.getStatement(true, false));

        assertEquals(0,bank.totalInterestPaid(true).doubleValue(),DOUBLE_DELTA);
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(new BigDecimal(100.0),true);

        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR,1);
        bank.setDate(date);

        assertEquals(0.1, bank.totalInterestPaid(false).doubleValue(), DOUBLE_DELTA);
    }

    @Test
    //
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(new BigDecimal(1500.0),true);

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH,365);
        bank.setDate(date);

        // Expected value from Excel calculation. Slightly off due to rounding errors.
        assertEquals(2.00049813213991, bank.totalInterestPaid(false).doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(new BigDecimal(3000.0),true);

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH,365);
        bank.setDate(date);

        assertEquals(150.0, bank.totalInterestPaid(false).doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_low() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(bill.openAccount(maxiAccount));

        maxiAccount.withdraw(BigDecimal.ZERO,true);
        maxiAccount.deposit(new BigDecimal(1000.0),true);

        Calendar date = Calendar.getInstance();
        for (int day = 0; day < 72; day++){
            maxiAccount.withdraw(new BigDecimal(0.01),false);
            maxiAccount.deposit(new BigDecimal(0.01),false);
//            System.out.println("Balance = " + maxiAccount.sumTransactions().toString());
//            System.out.println("Interest = " + maxiAccount.interestEarned(false).toString());
            date.add(Calendar.DAY_OF_MONTH,5);
            bank.setDate(date);
        }

        date.add(Calendar.DAY_OF_MONTH,5);
        bank.setDate(date);

        System.out.println(bill.getStatement(false,false));

        assertEquals(1.0, bank.totalInterestPaid(false).doubleValue(), DOUBLE_DELTA);

        date.add(Calendar.YEAR,1);
        bank.setDate(date);

        System.out.println(bill.getStatement(false,false));
        assertEquals(51.05, bank.totalInterestPaid(false).doubleValue(), DOUBLE_DELTA);
    }

     @Test
    public void maxi_savings_low_high(){
        Bank bank = new Bank();
        Customer barry = new Customer("Barry");
        Account maxiaccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(barry.openAccount(maxiaccount));

        maxiaccount.deposit(new BigDecimal(1000),true);

        Calendar date = Calendar.getInstance();

        date.add(Calendar.MINUTE,5);
        bank.setDate(date);

         maxiaccount.withdraw(new BigDecimal(0.01),false);
         maxiaccount.deposit(new BigDecimal(0.01),false);

         date.add(Calendar.MONTH,1);
         bank.setDate(date);

         maxiaccount.withdraw(new BigDecimal(0.01),false);
         maxiaccount.deposit(new BigDecimal(0.01),false);

         date.add(Calendar.MONTH,1);
         bank.setDate(date);

         System.out.println(barry.getStatement(false,false));
     }

}

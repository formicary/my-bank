package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        
        
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiTest10DayInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3010.0); // Depositing 3010
        checkingAccount.withdraw(10); // Widthdrawing 10
        Calendar currentTime = Calendar.getInstance(); // Getting Current Time
    	currentTime.add(Calendar.DAY_OF_MONTH, -11); // setting currentTime to -11 days
        checkingAccount.transactions.get(1).transactionDate = currentTime.getTime(); // Changing Transaction date of widthdraw to 11 days ago
        
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void lastWidthdrawDate() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        maxiSavings.deposit(3000.0);
        maxiSavings.withdraw(275.00);
        
        System.out.println(bill.lastWithdrawal(maxiSavings));
        
        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void payInterest() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        maxiSavings.deposit(3010.0);
        maxiSavings.withdraw(10);
       
        System.out.println("balance £"+bill.getBalance(maxiSavings));
        bank.payInterest();
        System.out.println("balance £"+bill.getBalance(maxiSavings));
        
        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void payInterest2() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        maxiSavings.deposit(3010.0);
        maxiSavings.withdraw(10);
       
        System.out.println("balance £"+bill.getBalance(maxiSavings));
        bank.dailyInterest();
        System.out.println("balance £"+bill.getBalance(maxiSavings));
        
        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void test() throws InterruptedException {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        maxiSavings.deposit(2000.0);
        System.out.println("Real amount of customers: "+bank.customers.size());
        
        System.out.println("Current Hour: "+bank.zonedNow.getHour()+"Current Minute: "+bank.zonedNow.getMinute()+"Current Second: "+bank.zonedNow.getSecond());
        
        //Sets interest payout time to 5 seconds from test start
        bank.zonedNext1AM = bank.zonedNow.withHour(bank.zonedNow.getHour()).withMinute(bank.zonedNow.getMinute()).withSecond(bank.zonedNow.getSecond()+5);
        
        //Sets interest delay from once every 24 hours to every 3 seconds
        bank.secondsBeforeNextInterest = 3;
        bank.dailyInterest();
        
        Thread.sleep(10000);
        System.out.println(bill.getBalance(maxiSavings));
        
        assertEquals(2205, bill.getBalance(maxiSavings), DOUBLE_DELTA);
    }

}

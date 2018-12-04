package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    // Tests customer summary.
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    // Tests interest on checking account
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        bank.payInterest();

        assertEquals(0.1/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    // Tests interest on savings account.
    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);
        bank.payInterest();

        assertEquals(2.0/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    // Tests interest on maxi savings account
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        bank.payInterest();
        
        
        assertEquals(150.0/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    // Tests interest on maxi-account with withdrawal over 10 days ago, one of the additional features.
    @Test
    public void maxiTest10DayInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3010.0); // Depositing 3010
        checkingAccount.withdraw(10); // Withdrawing 10
        Calendar currentTime = Calendar.getInstance(); // Getting Current Time
    	currentTime.add(Calendar.DAY_OF_MONTH, -11); // setting currentTime to -11 days
        checkingAccount.transactions.get(1).transactionDate = currentTime.getTime(); // Changing Transaction date of withdrawal to 11 days ago
        bank.payInterest();
        
        assertEquals(150.0/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    // Tests interest on one customer with 2 accounts.
    @Test
    public void InterestChecker() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Account savings = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavings).openAccount(savings);
        bank.addCustomer(bill);

        maxiSavings.deposit(3000);
        savings.deposit(400);
        
        bank.payInterest();
        
        
        
        assertEquals(150.40/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }    
    
    // Tests interest on one customer with multiple accounts.
    @Test
    public void InterestChecker2() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Account savings = new Account(Account.SAVINGS);
        Account checking = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(maxiSavings).openAccount(savings).openAccount(checking);
        bank.addCustomer(bill);

        maxiSavings.deposit(3000);
        savings.deposit(1200);
        checking.deposit(500);
        
        bank.payInterest();
        
        
        
        assertEquals(151.90/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }    
    
    // Tests interest payment on multiple customers and accounts.
    @Test
    public void InterestChecker3() {
        Bank bank = new Bank();
        Account maxiSavingsBill = new Account(Account.MAXI_SAVINGS);
        Account savingsBill = new Account(Account.SAVINGS);
        Account checkingBill = new Account(Account.CHECKING);
        Account maxiSavingsAlan = new Account(Account.MAXI_SAVINGS);
        Account savingsAlan = new Account(Account.SAVINGS);
        Account checkingAlan = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(maxiSavingsBill).openAccount(savingsBill).openAccount(checkingBill);
        Customer alan = new Customer("Alan").openAccount(maxiSavingsAlan).openAccount(savingsAlan).openAccount(checkingAlan);
        bank.addCustomer(bill);
        bank.addCustomer(alan);
        

        maxiSavingsBill.deposit(3000);
        savingsBill.deposit(1200);
        checkingBill.deposit(500);
        maxiSavingsAlan.deposit(750);
        savingsAlan.deposit(450);
        checkingAlan.deposit(220);
        
        
        bank.payInterest();
        
        
        
        assertEquals(190.07/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }    
    
    // This tests one of the additional features, interest rate is of maxi-savings is reduced to 0.1%
    // when there is a transaction within last 10 days
    @Test
    public void payInterest() {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        maxiSavings.deposit(3010.0);
        maxiSavings.withdraw(10);
       
        bank.payInterest();
        
        assertEquals(3.0/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }    
    
    
    // Tests Daily interest payment system, one of the additional features, for testing, instead of interest being paid daily,
    // it is paid once every 3 seconds. and the first payment is in five seconds.
    @Test
    public void testDailyPayments() throws InterruptedException {
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        maxiSavings.deposit(2000.0);
                
        //Sets interest payout time to 5 seconds from test start instead of 1 AM for testing purposes
        bank.zonedNext1AM = bank.zonedNow.withHour(bank.zonedNow.getHour()).withMinute(bank.zonedNow.getMinute()).withSecond(bank.zonedNow.getSecond()+5);
        
        //Sets interest delay from once every 24 hours to every 3 seconds for testing purposes
        bank.secondsBeforeNextInterest = 3;
        bank.dailyInterest();
        
        Thread.sleep(10000);
                
        assertEquals(2000+maxiSavings.interestEarnedOnAccount, maxiSavings.sumTransactions(), 1e-10);
    }

}

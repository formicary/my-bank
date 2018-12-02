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

}

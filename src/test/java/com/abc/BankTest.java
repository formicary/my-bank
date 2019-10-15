package com.abc;

import org.junit.Test;



import static org.junit.Assert.assertEquals;

import java.util.Date;

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
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_deposit_in_last_10_days() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(3000.0);
        
        maxiAccount.withdraw(1000.0);
        
        assertEquals(20.0, bank.totalInterestPaid(), DOUBLE_DELTA);	//3000-1000 = 2000. 0.01 * 1000 = 20
    }

    @Test
    public void maxi_savings_account_no_deposit_in_last_10_days() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(3000.0);
        
        maxiAccount.withdraw(1000.0);
        
        Date d = new Date();
        
        Date now = DateProvider.getInstance().now();						//get date now
        
        d = Transaction.subtractDays(now,11);							//change date to more than 10 days ago
        
        maxiAccount.transactions.get(0).setTransactionDate(d);;		//set transaction to new date

        assertEquals(100, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

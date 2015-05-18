package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    
    /**
     * A bank manager can get a report showing the list of customers and how many accounts they have
     */
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

         assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());

    }

     /**
     * Same as customerSummary test but with 2 accounts
     */
    @Test
    public void customerSummary2() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer ben = new Customer("Ben");
        john.openAccount(new Account(Account.CHECKING));
        ben.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);
        bank.addCustomer(ben);

         assertEquals("Customer Summary\n - John (1 account)\n - Ben (1 account)", bank.customerSummary());
    }
     
      /**
     * Same as customerSummary test but with 3 accounts
     */
    @Test
    public void customerSummary3() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer ben = new Customer("Ben");
        Customer alex = new Customer("Alex");
        john.openAccount(new Account(Account.CHECKING));
        ben.openAccount(new Account(Account.SAVINGS));
        alex.openAccount(new Account(Account.SAVINGS));
        alex.openAccount(new Account(Account.CHECKING));

        bank.addCustomer(john);
        bank.addCustomer(ben);
        bank.addCustomer(alex);

        assertEquals("Customer Summary\n - John (1 account)\n - Ben (1 account)\n - Alex (2 accounts)", bank.customerSummary());
    }
    
    /**
     * Get interest for Checking accounts
     */
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Get interest for Saving accounts
     */
    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    /**
     * Get interest for maxi-saving accounts - Transaction occurred in past 10 days.
     * Means 0.1% interest
     */
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(3, bank.totalInterestPaid(), DOUBLE_DELTA);
        }
    
       /**
     * Get interest for maxi-saving accounts - Transaction has NOT occurred in past 10 days.
     * Means 0.1% interest
     */
    @Test
    public void maxi_savings_account2() throws ParseException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        
        //Manually change transaction date to more than 10 days ago
        Calendar cal = java.util.Calendar.getInstance();
        String dateInString = new java.text.SimpleDateFormat("EEEE, 01/05/2015/00:00:00").format(cal.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss");
        Date tenDaysAgo = formatter.parse(dateInString);
        
        Transaction one = checkingAccount.getTransactions().get(0);
        one.setTransactionDate(tenDaysAgo);

        assertEquals(150, bank.totalInterestPaid(), DOUBLE_DELTA);
        }
    
    
    /**
     * Get interest for multiple accounts
     */
    @Test
    public void getTotalInterestPaidSeveralAccounts() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(100.0);
        
        Account checkingAccount2 = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount2));
        checkingAccount2.deposit(1500.0);

        Account checkingAccount3 = new Account(Account.MAXI_SAVINGS); //0.1% interest
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount3));
        checkingAccount3.deposit(3000.0);

        
        assertEquals(5.1, bank.totalInterestPaid(), DOUBLE_DELTA);
        }
    
    
  
        /**
        * Test can transfer money between accounts
        */
       @Test
       public void testTransferBetweenAccounts() {
            Bank bank = new Bank();
            Account checkingAccount1 = new Account(Account.CHECKING);
            checkingAccount1.deposit(100.0);
            
            Account checkingAccount2 = new Account(Account.CHECKING);
            checkingAccount2.deposit(200.0);
            
            Customer bill = new Customer("Bill").openAccount(checkingAccount1);
            bill.openAccount(checkingAccount1);
            bill.openAccount(checkingAccount2);

            bank.addCustomer(bill);
            
            bill.transferToOtherAccount(checkingAccount1, checkingAccount2, 50);
            
            assertEquals(50,checkingAccount1.sumTransactions(), DOUBLE_DELTA);
            assertEquals(250,checkingAccount2.sumTransactions(), DOUBLE_DELTA);
       }
       
    /**
     * Test boolean return value, where transactions were NOT created in the past 10 days
     */
    @Test
    public void transactionsCreatedPast10Days() throws ParseException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        
        //Manually change transaction date to more than 10 days ago
        Calendar cal = java.util.Calendar.getInstance();
        String dateInString = new java.text.SimpleDateFormat("EEEE, 01/05/2015/00:00:00").format(cal.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss");
        Date tenDaysAgo = formatter.parse(dateInString);
        
        Transaction one = checkingAccount.getTransactions().get(0);
        one.setTransactionDate(tenDaysAgo);

        assertEquals(false, checkingAccount.transactionsCreatedPast10Days());
    }
    
   /**
     * Test boolean return value, where transactions WERE created in the past 10 days
     */
    @Test
    public void transactionsCreatedPast10Days2() throws ParseException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        checkingAccount.deposit(200.0); //This transaction is done today - Should return true.
        
        //Manually change transaction date to more than 10 days ago
        Calendar cal = java.util.Calendar.getInstance();
        String dateInString = new java.text.SimpleDateFormat("EEEE, 01/05/2015/00:00:00").format(cal.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss");
        Date tenDaysAgo = formatter.parse(dateInString);
        
        Transaction one = checkingAccount.getTransactions().get(0);
        one.setTransactionDate(tenDaysAgo);

        assertEquals(true, checkingAccount.transactionsCreatedPast10Days());
    }
    
    /**
     * Test you can add interest to an account daily
     */
      @Test
      public void addInterestToAccount(){
        
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000.0);
        
        //Represents day 1 interest accrued
        checkingAccount.addInterestToAccount();
        assertEquals(1001,checkingAccount.sumTransactions(), DOUBLE_DELTA);

        //Represents day 2 interest accrued
        checkingAccount.addInterestToAccount();
        assertEquals(1002.001,checkingAccount.sumTransactions(), DOUBLE_DELTA);
        
        
    }
    

}

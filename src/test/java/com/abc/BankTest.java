package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void addCustomer() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (0 accounts)", bank.customerSummary());
    }
    
    @Test
    public void addCustomerWithBlankName() {
        Bank bank = new Bank();
        Customer john = new Customer("");
        //john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("", john.getName());
        assertEquals(0, john.getNumberOfAccounts());
        assertEquals(0, bank.getNumberOfCustomers());        
    }
    
    @Test
    public void addCustomerWithNullName() {
        Bank bank = new Bank();
        Customer john = new Customer(null);
        //john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals(null, john.getName());
        assertEquals(0, john.getNumberOfAccounts());
        assertEquals(0, bank.getNumberOfCustomers());
        
    }
    
    @Test
        public void testCustomerSummaryNoCustomers(){
            Bank bank = new Bank();
    
            assertEquals("There are currently no customers", bank.customerSummary());
        }
    
    
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_multiple_transactions() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        checkingAccount.deposit(1000.0);

        assertEquals(4.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
   // Test case for transaction date ten days before
    @Test
    public void maxi_savings_account_backvalue_transactiondate() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        
/*        List<Transaction> transactions = bank.getFirstCustomer().getAccounts().get(0).getTransactions();
        
        Assert.assertNotNull(transactions);
        
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.get(0);
        
        transaction.setTransactionDate(DateProvider.getInstance().dateTenDaysBefore());*/

        checkingAccount.depositWithBackValueDate(3000.0, DateProvider.getInstance().dateTenDaysBefore());

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void getBankWithoutAnyCustomerTest() {
    	 Bank bank = new Bank();
    	 Assert.assertNull(bank.getFirstCustomerName());
    }
    
    @Test
    public void getBankWithCustomerTest() {
    	Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        
        assertEquals("Bill", bank.getFirstCustomerName());
        
    }
   

}

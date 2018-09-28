package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    @Test
    public void testSingleCustomerReport() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Report\n - John (1 account)", bank.printCustomerAccountsReport());
    }
    
    @Test
    public void testMultipleCustomerReport() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer bill = new Customer("Bill");
        Customer tom = new Customer("Tom");
        bank.addCustomer(john.openAccount(new Account(AccountType.CHECKING)));
        bank.addCustomer(bill.openAccount(new Account(AccountType.SAVINGS)));
        bank.addCustomer(tom.openAccount(new Account(AccountType.MAXI_SAVINGS)));

        assertEquals("Customer Report\n - John (1 account)\n - Bill (1 account)\n - Tom (1 account)", bank.printCustomerAccountsReport());
    }
    
    @Test
    public void testMultipleAccountCustomerReport() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);
        john.openAccount(new Account(AccountType.SAVINGS));

        assertEquals("Customer Report\n - John (2 accounts)", bank.printCustomerAccountsReport());
    }

    @Test
    public void testCheckingAccountTotalInterestPaidReport() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        
        // test for account total = 0
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $0.00", bank.printTotalInterestPaidReport());

        checkingAccount.deposit(100.0);
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $0.10", bank.printTotalInterestPaidReport());
    }

    @Test
    public void testSavingsAccountTotalInterestPaidReport() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        
        // test for account total = 0
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $0.00", bank.printTotalInterestPaidReport());
        
        // test for account total less than 1000
        savingsAccount.deposit(900.0);
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $0.90", bank.printTotalInterestPaidReport());
        
        // test for account total greater than 1000
        savingsAccount.deposit(600.0);
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $2.00", bank.printTotalInterestPaidReport());
    }

    @Test
    public void testMaxiSavingsAccountTotalInterestPaidReport() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        
        // test for account total = 0
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $0.00", bank.printTotalInterestPaidReport());

        // test for account total less than 1000
        maxiSavingsAccount.deposit(900.0);
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $18.00", bank.printTotalInterestPaidReport());
        
        // test for account total 1000-2000
        maxiSavingsAccount.deposit(600.0);
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $45.00", bank.printTotalInterestPaidReport());
        
        // test for account total greater than 2000
        maxiSavingsAccount.deposit(1500.0);
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $170.00", bank.printTotalInterestPaidReport());
    }
    
    // Checks that the total interest paid by bank is calculated across each different account of every customer
    @Test
    public void testMixedAccountsTotalInterestPaidReport() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);

        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        Customer john = new Customer("John").openAccount(savingsAccount);
        Customer tom = new Customer("Tom").openAccount(maxiSavingsAccount);

        bank.addCustomer(bill);
        bank.addCustomer(john);
        bank.addCustomer(tom);

        
        // test for account total = 0
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $0.00", bank.printTotalInterestPaidReport());

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(1500.0);
        maxiSavingsAccount.deposit(2500.0);
        assertEquals("Total Interest Paid Report\n Total Interest Paid: $122.10", bank.printTotalInterestPaidReport());
    }
    
    

}

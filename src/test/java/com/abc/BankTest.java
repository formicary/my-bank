package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test Constructor
    public void bank_TestConstructor_NewBankCreated() {
    	Bank bank = new Bank();
    	assertTrue(bank instanceof Bank);
    }
    
    @Test //Test customerSummary method with no customers
    public void customerSummary_NoCustomers_ReturnEmptySummary() {
        Bank bank = new Bank();
        assertEquals("Customer Summary", bank.customerSummary());
    }
    
    @Test //Test customerSummary method with one customer
    public void customerSummary_OneCustomer_ReturnCustomerAndAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.Type.CHECKING));
        bank.addCustomer(john);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test //Test customerSummary method with two customers
    public void customerSummary_TwoCustomers_ReturnCustomersAndAccounts() {
    	Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.Type.CHECKING));
        Customer mary = new Customer("Mary");
        mary.openAccount(new Account(Account.Type.SAVINGS));
        mary.openAccount(new Account(Account.Type.MAXI_SAVINGS));
        bank.addCustomer(john);
        bank.addCustomer(mary);
        assertEquals("Customer Summary\n - John (1 account)\n - Mary (2 accounts)", bank.customerSummary());
    }
    
    @Test //Test getFirstCustomer method with no customers
    public void getFirstCustomer_NoCustomer_ErrorReturned() {
    	Bank bank = new Bank();
    	assertEquals("No Customers", bank.getFirstCustomer());
    }

    @Test //Test totalInterestPaid method with one checking account
    public void totalInterestPaid_CheckingAccount_ReturnCheckingInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.Type.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(checkingAccount.totalInterest(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test //Test totalInterestPaid method with one savings account
    public void totalInterestPaid_SavingsAccount_ReturnSavingsInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.Type.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(100.0);

        assertEquals(savingsAccount.totalInterest(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test //Test totalInterestPaid method with one maxi-savings account
    public void totalInterestPaid_MaxiSavingsAccount_ReturnMaxiSavingsInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.Type.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(100.0);

        assertEquals(maxiSavingsAccount.totalInterest(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test //Test totalInterestPaid method with multiple customers
    public void totalInterestPaid_MultipleCustomers_ReturnTotalofInterests() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.Type.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount);
        Account checkingAccount = new Account(Account.Type.CHECKING);
        Customer ben = new Customer("Ben").openAccount(checkingAccount);
        bank.addCustomer(bill);
        bank.addCustomer(ben);

        maxiSavingsAccount.deposit(100.0);
        checkingAccount.deposit(500.0);

        double expectedInterest = maxiSavingsAccount.totalInterest() + checkingAccount.totalInterest();
        assertEquals(expectedInterest, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test //Test totalInterestPaid method with multiple accounts
    public void totalInterestPaid_MultipleAccounts_ReturnTotalofInterests() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.Type.MAXI_SAVINGS);
        Account checkingAccount = new Account(Account.Type.CHECKING);
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount).openAccount(checkingAccount);;
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(1000.0);
        checkingAccount.deposit(500.0);

        double expectedInterest = maxiSavingsAccount.totalInterest() + checkingAccount.totalInterest();
        assertEquals(expectedInterest, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

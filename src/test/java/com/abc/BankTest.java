package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Factory cf = new CustomerFactory();
	private Factory af = new AccountFactory();

    @Test
    public void customerSummary() {
    	Bank bank = new BankBranch("BranchA");
        Customer john = (Customer) cf.create("John", DateProvider.getInstance().now());
        Account ca = (Account) af.create(john, AccountEnum.CHECKINGACCOUNT);
        john.addAccount(ca);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void testTotalPaidInterest() {
    	Bank bank = new BankBranch("BranchA");
        Customer bill = (Customer) cf.create("Bill", DateProvider.getInstance().now());
        Account checkingAccount = (Account) af.create(bill, AccountEnum.CHECKINGACCOUNT);
        bill.addAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(100.0);
        
        Customer jack = (Customer) cf.create("Jack", DateProvider.getInstance().now());
        Account savingsAccount = (Account) af.create(jack, AccountEnum.SAVINGSACCOUNT);
        jack.addAccount(savingsAccount);
        bank.addCustomer(jack);
        savingsAccount.deposit(1500.0);
        
    	Customer conor = (Customer) cf.create("Conor", DateProvider.getInstance().now());
        Account maxiSavingAccount = (Account) af.create(conor, AccountEnum.MAXISAVINGACCOUNT);
        conor.addAccount(maxiSavingAccount);
        bank.addCustomer(conor);
        maxiSavingAccount.deposit(3000.0);

        assertEquals(122.1, bank.totalInterestPaid(), DOUBLE_DELTA);	
    }
    
    @Test
    public void checkingAccount() {
    	Bank bank = new BankBranch("BranchA");
        Customer bill = (Customer) cf.create("Bill", DateProvider.getInstance().now());
        Account checkingAccount = (Account) af.create(bill, AccountEnum.CHECKINGACCOUNT);
        bill.addAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
    	Bank bank = new BankBranch("BranchA");
        Customer bill = (Customer) cf.create("Bill", DateProvider.getInstance().now());
        Account savingsAccount = (Account) af.create(bill, AccountEnum.SAVINGSACCOUNT);
        bill.addAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
    	Bank bank = new BankBranch("BranchA");
    	Customer bill = (Customer) cf.create("Bill", DateProvider.getInstance().now());
        Account maxiSavingAccount = (Account) af.create(bill, AccountEnum.MAXISAVINGACCOUNT);
        bill.addAccount(maxiSavingAccount);
        bank.addCustomer(bill);

        maxiSavingAccount.deposit(3000.0);

        assertEquals(120.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

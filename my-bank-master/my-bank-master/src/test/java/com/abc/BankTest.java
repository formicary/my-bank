package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    //Tests alter time as interest occurs after each day passes, so is not present first day

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
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        checkingAccount.currentDate.setDate(checkingAccount.currentDate.getDate() + 1);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        //Changed from checkingAccount, to savingAccount to avoid confusion
        Account savingAccount = new Account(Account.SAVINGS);
        //Changed name from bill to Kevin, so as to make more different from checking test
        Customer kevin = new Customer("Kevin");
        kevin.openAccount(savingAccount);
        bank.addCustomer(kevin);

        savingAccount.deposit(1500.0);
        savingAccount.currentDate.setDate(savingAccount.currentDate.getDate() + 1);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        //Changed from maxiAccount, to savingAccount to avoid confusion
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        //Changed name from bill to Jack, so as to make more different from checking test
        Customer jack = new Customer("Jack");
        jack.openAccount(maxiAccount);
        bank.addCustomer(jack);

        maxiAccount.deposit(3000.0);
        maxiAccount.currentDate.setDate(maxiAccount.currentDate.getDate() + 1);

        assertEquals(3, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    //Test Maxi account changes interest after ten days
    @Test
    public void maxi_savings_date() {
        Bank bank = new Bank();
        //Changed from maxiAccount, to savingAccount to avoid confusion
        Account maxiAccountD = new Account(Account.MAXI_SAVINGS);
        //Changed name from bill to Jack, so as to make more different from checking test
        Customer jack = new Customer("Jack");
        jack.openAccount(maxiAccountD);
        bank.addCustomer(jack);

        maxiAccountD.deposit(3000.0);
        
        //Set date to 10 days ago so can test change of interest
        maxiAccountD.dateWithdraw.setDate(maxiAccountD.dateWithdraw.getDate() - 10);
        maxiAccountD.currentDate.setDate(maxiAccountD.currentDate.getDate() + 1);

        assertEquals(150, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkingTen() {
        Bank bank = new Bank();
        Account checkingAccountT = new Account(Account.CHECKING);
        Customer brock = new Customer("Brock");
        brock.openAccount(checkingAccountT);
        bank.addCustomer(brock);
        
        checkingAccountT.deposit(100.0);
        checkingAccountT.currentDate.setDate(checkingAccountT.currentDate.getDate() + 10);
        assertEquals(1.004512021025221, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
}

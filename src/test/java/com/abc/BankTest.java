package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.AccountFactory;
import com.abc.accounts.AccountType;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Bank bank = new Bank();
    private Customer bill = new Customer("Bill");

    @Test
    public void customerSummary() {
        bill.openAccount(AccountFactory.createAccount(AccountType.CHECKING));
        bank.addCustomer(bill);

        assertEquals("Customer Summary\n - Bill (1 account)", bank.customerSummary());
    }

    //TODO: test totalInterest earned by bank

    @Test
    public void checkingAccount() {
        Account checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Account savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Account maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
 
    @Test
    public void getFirstCustomer(){
        bank.addCustomer(bill);
        bank.addCustomer(new Customer("Jake"));
        assertEquals("Bill", bank.getFirstCustomerName());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getFirstCustomer_NoCustomers(){
        assertEquals(" ", bank.getFirstCustomerName());
    }

}

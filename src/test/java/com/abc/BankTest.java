package com.abc;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.Account;
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

    @Test
    public void allAccountsSummary(){
        Account billSavings = AccountFactory.createAccount(AccountType.SAVINGS);
        bill.openAccount(billSavings);
        billSavings.deposit(1500);

        Customer oscar = new Customer("Oscar");
        Account oscarChecking = AccountFactory.createAccount(AccountType.CHECKING);
        oscar.openAccount(oscarChecking);
        oscarChecking.deposit(600);

        bank.addCustomer(bill);
        bank.addCustomer(oscar);

        String expectedSummary =
         "All Accounts Summary"
         +bill.accountSummary() + oscar.accountSummary()
         +"\n Total interest paid by bank: " + Utilities.toDollars(bank.totalInterestPaid());

         assertEquals(expectedSummary, bank.allAccountsSummary());
    }

    @Test
    public void totalInterestPaid_oneAccount() {
        Account checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000);

        assertEquals(checkingAccount.interestEarned(), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid_multipleAccounts() {
        Account checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000);

        Customer oscar = new Customer("Oscar");
        bank.addCustomer(oscar);
        Account savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        oscar.openAccount(savingsAccount);

        savingsAccount.deposit(3600);

        double expectedInterestPaid = oscar.totalInterestEarned() + bill.totalInterestEarned();

        assertEquals(expectedInterestPaid, bank.totalInterestPaid(), DOUBLE_DELTA);
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

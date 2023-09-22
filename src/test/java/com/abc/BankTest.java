package com.abc;

import org.junit.Test;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.MaxiSavingsAccount;
import com.abc.Account.SavingsAccount;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;

public class BankTest {
    private BigDecimal amountToDeposit;
    private BigDecimal amountToDeposit2;


    private Bank bank;
    private Customer customer;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    @Before
    public void setup() {
        bank = new Bank();
        customer = new Customer("Jade");
        checkingAccount = new CheckingAccount();
        savingsAccount = new SavingsAccount();
        maxiSavingsAccount = new MaxiSavingsAccount();
        amountToDeposit = BigDecimal.valueOf(1500.00);
        amountToDeposit2 = BigDecimal.valueOf(1000.00);
    }

    @After
    public void tearDown() {
        bank = null;
        customer = null;
        checkingAccount = null;
        savingsAccount = null;
        maxiSavingsAccount = null;
        amountToDeposit = null;
        amountToDeposit2 = null;
    }

    @Test
    public void testCustomerSummary() {
        bank.addCustomer(customer);
        customer.openAccount(checkingAccount);
        
        assertEquals("Customer Summary\n - Jade (1 account)", bank.customerSummary());
    }

    @Test
    public void testCheckingInterestPaid() {
        bank.addCustomer(customer);
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = new BigDecimal("1.50");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

    @Test
    public void testSavingsLowInterestPaid() {
        bank.addCustomer(customer);
        customer.openAccount(savingsAccount);
        savingsAccount.depositFunds(amountToDeposit2);
        BigDecimal expectedInterestPaid = new BigDecimal("1.00");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

    @Test
    public void testSavingsHighInterestPaid() {
        bank.addCustomer(customer);
        customer.openAccount(savingsAccount);
        savingsAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = new BigDecimal("2.00");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

    @Test
    public void testMaxiSavingsLowInterestPaid() {
        bank.addCustomer(customer);
        customer.openAccount(maxiSavingsAccount);
        maxiSavingsAccount.depositFunds(amountToDeposit2);
        BigDecimal expectedInterestPaid = new BigDecimal("20.00");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

    @Test
    public void testMaxiSavingsMidInterestPaid() {
        bank.addCustomer(customer);
        customer.openAccount(maxiSavingsAccount);
        maxiSavingsAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = new BigDecimal("45.00");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

    @Test
    public void testMaxiSavingsHighInterestPaid() {
        bank.addCustomer(customer);
        customer.openAccount(maxiSavingsAccount);
        BigDecimal amountToDepositMaxiSavings = BigDecimal.valueOf(3000.00);
        maxiSavingsAccount.depositFunds(amountToDepositMaxiSavings);
        BigDecimal expectedInterestPaid = new BigDecimal("170.00");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

}
    
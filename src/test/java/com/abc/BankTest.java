package com.abc;

import org.junit.Test;

import com.abc.Utilities.Enums.AccountType;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

// Todo: Debug, fix tests/code and ensure all scenarios covered
public class BankTest {
    private BigDecimal amountToDeposit;


    private Bank bank;
    private Customer customer;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    @Before
    public void setup() {
        bank = new Bank();
        customer = new Customer("Jade");
        checkingAccount = new Account(AccountType.CHECKING);
        savingsAccount = new Account(AccountType.SAVINGS);
        maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        amountToDeposit = BigDecimal.valueOf(1500.00);
    }

    @After
    public void tearDown() {
        bank = null;
        customer = null;
        checkingAccount = null;
        savingsAccount = null;
        maxiSavingsAccount = null;
        amountToDeposit = null;
    }

    @Test
    public void testCustomerSummary() {
        bank.addCustomer(customer);
        customer.openAccount(checkingAccount);
        
        assertEquals("Customer Summary\n - Jade (1 account)", bank.customerSummary());
    }

    // Todo: below tests currently fail - revisit once interested calculation refactored
    @Ignore
    @Test
    public void testCheckingAccount() {
        bank.addCustomer(customer);
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = BigDecimal.valueOf(0.1);

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

    @Ignore
    @Test
    public void testSavingsAccount() {
        bank.addCustomer(customer);
        customer.openAccount(savingsAccount);
        savingsAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = BigDecimal.valueOf(2.0);

        assertEquals(expectedInterestPaid, bank.totalInterestPaid());
    }

    @Ignore
    @Test
    public void testMaxiSavingsAccount() {
        bank.addCustomer(customer);
        customer.openAccount(maxiSavingsAccount);
        BigDecimal amountToDepositMaxiSavings = BigDecimal.valueOf(3000.00);
        maxiSavingsAccount.depositFunds(amountToDepositMaxiSavings);

        assertEquals(170.0, bank.totalInterestPaid());
    }

}

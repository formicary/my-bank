package com.abc;

import org.junit.Test;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;

public class BankTest {
    private BigDecimal amountToDeposit;
    private BigDecimal amountToDeposit2;
    private int numberOfDays;


    private Bank bank;
    private Customer customer;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    @Before
    public void setup() {
        bank = new Bank();

        customer = new Customer("Jade");
        bank.addCustomer(customer);

        checkingAccount = new CheckingAccount();
        savingsAccount = new SavingsAccount();
        maxiSavingsAccount = new MaxiSavingsAccount();
        amountToDeposit = BigDecimal.valueOf(1500.00);
        amountToDeposit2 = BigDecimal.valueOf(1000.00);
        numberOfDays = 30;
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
        numberOfDays = 0;
    }

    @Test
    public void testCustomerSummary() {
        customer.openAccount(checkingAccount);
        assertEquals("Customer Summary\n - Jade (1 account)", bank.generateCustomerSummary());
    }

    @Test
    public void testCheckingInterestPaid() {
        customer.openAccount(checkingAccount);

        checkingAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = new BigDecimal("0.12");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid(numberOfDays));
    }

    @Test
    public void testSavingsLowInterestPaid() {
        customer.openAccount(savingsAccount);

        savingsAccount.depositFunds(amountToDeposit2);
        BigDecimal expectedInterestPaid = new BigDecimal("0.08");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid(numberOfDays));
    }

    @Test
    public void testSavingsHighInterestPaid() {
        customer.openAccount(savingsAccount);

        savingsAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = new BigDecimal("0.25");
        int numberOfDays = 30;

        assertEquals(expectedInterestPaid, bank.totalInterestPaid(numberOfDays));
    }

    @Test
    public void testMaxiSavingsNoWithdrawalInterestPaid() {
        customer.openAccount(maxiSavingsAccount);

        maxiSavingsAccount.depositFunds(amountToDeposit);
        BigDecimal expectedInterestPaid = new BigDecimal("6.18");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid(numberOfDays));
    }

    @Test
    public void testMaxiSavingsWithdrawalWithin10DaysInterestPaid() {
        customer.openAccount(maxiSavingsAccount);
        
        maxiSavingsAccount.depositFunds(amountToDeposit);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(10.00);
        maxiSavingsAccount.withdrawFunds(amountToWithdraw);
        BigDecimal expectedInterestPaid = new BigDecimal("0.12");

        assertEquals(expectedInterestPaid, bank.totalInterestPaid(numberOfDays));
    }
}
    
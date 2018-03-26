package com.abc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateProvider.class)
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Mock public DateProvider dateProvider;

    @Before
    public void setup(){
        mockStatic(DateProvider.class);
        when(DateProvider.getInstance()).thenReturn(dateProvider);
    }

    @Test
    public void customerSummaryOneCustomer() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount("one", new CheckingAccount());
        bank.addCustomer(john);

        String finalSummary = "Customer Summary\n - John (1 account)";

        assertEquals(finalSummary, bank.customerSummary());
    }

    @Test
    public void customerSummaryTwoCustomers() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount("one", new CheckingAccount());
        bank.addCustomer(john);

        Customer bill = new Customer("Bill");
        bill.openAccount("one", new CheckingAccount());
        bill.openAccount("two", new SavingsAccount());
        bank.addCustomer(bill);

        String finalSummary = "Customer Summary\n - John (1 account)\n - Bill (2 accounts)";

        assertEquals(finalSummary, bank.customerSummary());
    }

    @Test
    public void calculateTotalInterest(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        CheckingAccount checkingAccountJohn = new CheckingAccount();
        john.openAccount("one", checkingAccountJohn);
        bank.addCustomer(john);

        Customer bill = new Customer("Bill");
        SavingsAccount savingsAccountBill = new SavingsAccount();
        bill.openAccount("one", savingsAccountBill);
        bank.addCustomer(bill);

        Customer henry = new Customer("Henry");
        MaxiSavingsAccount maxiSavingsAccountHenry = new MaxiSavingsAccount();
        henry.openAccount("one", maxiSavingsAccountHenry);
        bank.addCustomer(henry);

        when(dateProvider.now()).thenReturn(LocalDateTime.now().minusDays(20));

        checkingAccountJohn.deposit(3000.0, true);
        savingsAccountBill.deposit(1500.0, true);
        maxiSavingsAccountHenry.deposit(3000.0, true);

        when(dateProvider.now()).thenReturn(LocalDateTime.now());


        double checkingExpectedInterest = (3000.0 * Math.pow((1 + CheckingAccount.interestRate/365), 20) - 3000.0);
        double savingsExpectedInterest = (1000 * Math.pow((1 + SavingsAccount.interestRateLow / 365), 20)) - 1000 +
                (1500 - 1000) * SavingsAccount.interestRateHigh;
        double maxiSavingsExpectedInterest = (3000.0 * Math.pow(1 + MaxiSavingsAccount.interestRateLongTerm / 365, 20) - 3000.0);

        double totalExpected = checkingExpectedInterest + savingsExpectedInterest + maxiSavingsExpectedInterest;

        assertEquals(totalExpected, bank.totalInterestPaid(), DOUBLE_DELTA);
    }






}

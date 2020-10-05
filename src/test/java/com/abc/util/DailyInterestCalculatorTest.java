package com.abc.util;

import com.abc.entity.*;
import com.abc.entity.impl.AccountImpl;
import com.abc.entity.impl.BankImpl;
import com.abc.entity.impl.CustomerImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DailyInterestCalculatorTest {

    private static Customer customer;
    private static Account currentAccount;
    private static Account savingsAccount;
    private static Account maxiSavingsAccount;
    private static Account maxiAdditionalSavingsAccount;

    private LocalDateTime daysAgo(int days){
        return LocalDateTime.now().minusDays(days);
    }

    @Before
    public  void setup(){
        customer = new CustomerImpl("customer");
        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        savingsAccount = new AccountImpl(customer, AccountType.SAVINGS, "124");
        maxiSavingsAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "125");
        maxiAdditionalSavingsAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS_ADD, "126");
    }

    @Test
    public void dailyInterestIsZeroForZeroTransactionsCurrentAccount(){

        assertEquals("Daily interest calculator does not return zero for zero transactions in current account",
                new BigDecimal("0.00"),
                DailyInterestCalculator.interestEarned(currentAccount) );

    }

    @Test
    public void dailyInterestIsZeroForZeroTransactionsSavingsAccount(){

        assertEquals("Daily interest calculator does not return zero for zero transactions in savings account",
                new BigDecimal("0.00"),
                DailyInterestCalculator.interestEarned(savingsAccount) );

    }

    @Test
    public void dailyInterestIsZeroForZeroTransactionsMaxiSavingsAccount(){

        assertEquals("Daily interest calculator does not return zero for zero transactions in maxi savings account",
                new BigDecimal("0.00"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount) );

    }

    @Test
    public void dailyInterestIsZeroForZeroTransactionsMaxiSavingsAdditionalAccount(){

        assertEquals("Daily interest calculator does not return zero for zero transactions in maxi savings additional account",
                new BigDecimal("0.00"),
                DailyInterestCalculator.interestEarned(maxiAdditionalSavingsAccount) );

    }

    @Test
    public void currentAccountFourDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        currentAccount.addTransaction(t1);

        assertEquals("Daily interest of current account is not accumulated for 4 days correctly on small deposit",
                new BigDecimal("0.01"),
                DailyInterestCalculator.interestEarned(currentAccount));
    }

    @Test
    public void currentAccountHundredDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        currentAccount.addTransaction(t1);

        assertEquals("Daily interest of current account is not accumulated for 100 days correctly on small deposit",
                new BigDecimal("0.14"),
                DailyInterestCalculator.interestEarned(currentAccount));
    }


    @Test
    public void currentAccountFourDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        currentAccount.addTransaction(t1);

        assertEquals("Daily interest of current account is not accumulated for 4 days correctly on large deposit",
                new BigDecimal("10.96"),
                DailyInterestCalculator.interestEarned(currentAccount));
    }

    @Test
    public void currentAccountHundredDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        currentAccount.addTransaction(t1);

        assertEquals("Daily interest of current account is not accumulated for 100 days correctly on large deposit",
                new BigDecimal("273.97"),
                DailyInterestCalculator.interestEarned(currentAccount));
    }

    @Test
    public void savingsAccountFourDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        savingsAccount.addTransaction(t1);

        assertEquals("Daily interest of savings account is not accumulated for 4 days correctly on small deposit",
                new BigDecimal("0.01"),
                DailyInterestCalculator.interestEarned(savingsAccount));
    }

    @Test
    public void savingsAccountHundredDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        savingsAccount.addTransaction(t1);

        assertEquals("Daily interest of savings account is not accumulated for 100 days correctly on small deposit",
                new BigDecimal("0.14"),
                DailyInterestCalculator.interestEarned(savingsAccount));
    }

    @Test
    public void savingsAccountFourDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        savingsAccount.addTransaction(t1);

        assertEquals("Daily interest of savings account is not accumulated for 4 days correctly on large deposit",
                new BigDecimal("21.91"),
                DailyInterestCalculator.interestEarned(savingsAccount));
    }

    @Test
    public void savingsAccountHundredDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        savingsAccount.addTransaction(t1);

        assertEquals("Daily interest of savings account is not accumulated for 100 days correctly on large deposit",
                new BigDecimal("547.67"),
                DailyInterestCalculator.interestEarned(savingsAccount));
    }

    @Test
    public void maxiSavingsAccountFourDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        maxiSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings account is not accumulated for 4 days correctly on small deposit",
                new BigDecimal("0.11"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount));
    }

    @Test
    public void maxiSavingsAccountHundredDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        maxiSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings account is not accumulated for 100 days correctly on small deposit",
                new BigDecimal("2.74"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount));
    }

    @Test
    public void maxiSavingsAccountFourDaysAgoMidDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        maxiSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings account is not accumulated for 4 days correctly on mid size deposit",
                new BigDecimal("0.49"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount));
    }

    @Test
    public void maxiSavingsAccountHundredDaysAgoMidDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        maxiSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings account is not accumulated for 100 days correctly on mid size deposit",
                new BigDecimal("12.33"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount));
    }

    @Test
    public void maxiSavingsAccountFourDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        maxiSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings account is not accumulated for 4 days correctly on large deposit",
                new BigDecimal("1094.47"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount));
    }

    @Test
    public void maxiSavingsAccountHundredDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        maxiSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings account is not accumulated for 100 days correctly on large deposit",
                new BigDecimal("27361.64"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount));
    }

    @Test
    public void maxiSavingsAdditionalAccountFourDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        maxiAdditionalSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings additional feature account is not accumulated for 4 days correctly on small deposit",
                new BigDecimal("0.27"),
                DailyInterestCalculator.interestEarned(maxiAdditionalSavingsAccount));
    }

    @Test
    public void maxiSavingsAdditionalAccountHundredDaysAgoSmallDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        maxiAdditionalSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings additional feature account is not accumulated for 100 days correctly on small deposit",
                new BigDecimal("6.85"),
                DailyInterestCalculator.interestEarned(maxiAdditionalSavingsAccount));
    }

    @Test
    public void maxiSavingsAdditionalAccountFourDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(4));
        maxiAdditionalSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings additional feature account is not accumulated for 4 days correctly on large deposit",
                new BigDecimal("547.95"),
                DailyInterestCalculator.interestEarned(maxiAdditionalSavingsAccount));
    }

    @Test
    public void maxiSavingsAdditionalAccountHundredDaysAgoLargeDeposit(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        maxiAdditionalSavingsAccount.addTransaction(t1);

        assertEquals("Daily interest of maxi savings additional feature account is not accumulated for 100 days correctly on large deposit",
                new BigDecimal("13698.63"),
                DailyInterestCalculator.interestEarned(maxiAdditionalSavingsAccount));
    }

    @Test
    public void currentAccountSmallDepositAndWithdrawal(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        Transaction t2 = mock(Transaction.class);
        when(t2.getAmount()).thenReturn(new BigDecimal("250.00"));
        when(t2.getTransactionDate()).thenReturn(daysAgo(50));
        currentAccount.addTransaction(t1);
        currentAccount.addTransaction(t2);

        assertEquals("Daily interest of current account is not accumulated for deposit and withdrawals",
                new BigDecimal("0.18"),
                DailyInterestCalculator.interestEarned(currentAccount));
    }

    @Test
    public void savingsAccountLargeDepositAndWithdrawal(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        Transaction t2 = mock(Transaction.class);
        when(t2.getAmount()).thenReturn(new BigDecimal("-500000.00"));
        when(t2.getTransactionDate()).thenReturn(daysAgo(50));
        savingsAccount.addTransaction(t1);
        savingsAccount.addTransaction(t2);

        assertEquals("Daily interest of savings account is not accumulated for deposit and withdrawals",
                new BigDecimal("410.69"),
                DailyInterestCalculator.interestEarned(savingsAccount));
    }

    @Test
    public void maxiSavingsAccountLargeDepositAndWithdrawal(){
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));
        Transaction t2 = mock(Transaction.class);
        when(t2.getAmount()).thenReturn(new BigDecimal("-500000.00"));
        when(t2.getTransactionDate()).thenReturn(daysAgo(50));
        maxiSavingsAccount.addTransaction(t1);
        maxiSavingsAccount.addTransaction(t2);

        assertEquals("Daily interest of maxi savings account is not accumulated for deposit and withdrawals",
                new BigDecimal("20512.33"),
                DailyInterestCalculator.interestEarned(maxiSavingsAccount));
    }

    @Test
    public void interestPaidByBankForMultipleAccounts(){

        Bank bank = new BankImpl();
        bank.addCustomer(customer);
        Transaction t1 = mock(Transaction.class);
        when(t1.getAmount()).thenReturn(new BigDecimal("1000000.00"));
        when(t1.getTransactionDate()).thenReturn(daysAgo(100));

        currentAccount.addTransaction(t1);
        savingsAccount.addTransaction(t1);
        maxiSavingsAccount.addTransaction(t1);

        assertEquals("Bank is not able to calculate daily interest paid for multiple accounts",
                new BigDecimal("28183.28"),
                DailyInterestCalculator.totalInterestPaid(bank));

    }

}

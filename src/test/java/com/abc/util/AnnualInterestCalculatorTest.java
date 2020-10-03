package com.abc.util;

import com.abc.entity.*;
import com.abc.entity.impl.*;
import com.abc.util.AnnualInterestCalculator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnualInterestCalculatorTest {

    private static AccountImpl lowCurrent;
    private static AccountImpl midCurrent;
    private static AccountImpl highCurrent;

    private static AccountImpl lowSaving;
    private static AccountImpl midSaving;
    private static AccountImpl highSaving;

    private static AccountImpl lowMaxSaving;
    private static AccountImpl midMaxSaving;
    private static AccountImpl midHighMaxSaving;
    private static AccountImpl highMaxSaving;
    private static AccountImpl veryHighMaxSaving;

    private static BigDecimal low;
    private static BigDecimal mid;
    private static BigDecimal midHigh;
    private static BigDecimal high;
    private static BigDecimal veryHigh;
    private static Customer customer;
    @BeforeClass
    public static void setup(){
        customer = new CustomerImpl("customer");
        lowCurrent = new AccountImpl(customer, AccountType.CURRENT, "123");
        midCurrent = new AccountImpl(customer, AccountType.CURRENT, "124");
        highCurrent = new AccountImpl(customer, AccountType.CURRENT, "125");

        lowSaving = new AccountImpl(customer, AccountType.SAVINGS, "126");
        midSaving = new AccountImpl(customer, AccountType.SAVINGS, "127");
        highSaving = new AccountImpl(customer, AccountType.SAVINGS, "128");

        lowMaxSaving = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "129");
        midMaxSaving = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "130");
        midHighMaxSaving = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "131");
        highMaxSaving = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "132");
        veryHighMaxSaving = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "133");

        low = new BigDecimal("500");
        mid = new BigDecimal("1000");
        midHigh = new BigDecimal("1500");
        high = new BigDecimal("2000");
        veryHigh = new BigDecimal("3000");

    }


    @Test
    public void zeroBalanceLeavesZeroInterest(){
        assertEquals("Interest is not zero for empty account.",
                new BigDecimal("0.00"),
                AnnualInterestCalculator.interestEarned(lowCurrent));
    }
    @Test
    public void currentAccountInterestLowAmount(){
        customer.deposit( low, lowCurrent);
        assertEquals("Interest is not calculated correctly for current account of low balance.",
                new BigDecimal("0.50"),
                AnnualInterestCalculator.interestEarned(lowCurrent));
    }
    @Test
    public void currentAccountInterestForMidAmount(){
        customer.deposit(mid, midCurrent);
        assertEquals("Interest is not calculated correctly for current account of mid balance.",
                new BigDecimal("1.00"),
                AnnualInterestCalculator.interestEarned(midCurrent));
    }

    @Test
    public void currentAccountInterestForHighAmount(){
        customer.deposit(high, highCurrent);
        assertEquals("Interest is not calculated correctly for current account of high balance.",
                new BigDecimal("2.00"),
                AnnualInterestCalculator.interestEarned(highCurrent));
    }

    @Test
    public void savingAccountInterestLowAmount(){
        customer.deposit(low, lowSaving);
        assertEquals("Interest is not calculated correctly for saving account of low balance.",
                new BigDecimal("0.50"),
                AnnualInterestCalculator.interestEarned(lowSaving));
    }
    @Test
    public void savingAccountInterestForMidAmount(){
        customer.deposit(mid, midSaving);
        assertEquals("Interest is not calculated correctly for saving account of mid balance.",
                new BigDecimal("1.00"),
                AnnualInterestCalculator.interestEarned(midSaving));
    }

    @Test
    public void savingAccountInterestForHighAmount(){
        customer.deposit(high, highSaving);
        assertEquals("Interest is not calculated correctly for saving account of high balance.",
                new BigDecimal("3.00"),
                AnnualInterestCalculator.interestEarned(highSaving));
    }


    @Test
    public void maxiSavingAccountInterestLowAmount(){
        customer.deposit(low, lowMaxSaving);
        assertEquals("Interest is not calculated correctly for max saving account of low balance.",
                new BigDecimal("10.00"),
                AnnualInterestCalculator.interestEarned(lowMaxSaving));
    }
    @Test
    public void maxiSavingAccountInterestForMidAmount(){
        customer.deposit(mid, midMaxSaving);
        assertEquals("Interest is not calculated correctly for max saving account of mid balance.",
                new BigDecimal("20.00"),
                AnnualInterestCalculator.interestEarned(midMaxSaving));
    }

    @Test
    public void maxiSavingAccountInterestForMidHighAmount(){
        customer.deposit(midHigh, midHighMaxSaving);
        assertEquals("Interest is not calculated correctly for max saving account of mid-high balance.",
                new BigDecimal("45.00"),
                AnnualInterestCalculator.interestEarned(midHighMaxSaving));
    }

    @Test
    public void maxiSavingAccountInterestForHighAmount(){
        customer.deposit(high, highMaxSaving);
        assertEquals("Interest is not calculated correctly for max saving account of high balance.",
                new BigDecimal("70.00"),
                AnnualInterestCalculator.interestEarned(highMaxSaving));
    }

    @Test
    public void maxiSavingAccountInterestForVeryHighAmount(){
        customer.deposit(veryHigh, veryHighMaxSaving);
        assertEquals("Interest is not calculated correctly for max saving account of very high balance.",
                new BigDecimal("170.00"),
                AnnualInterestCalculator.interestEarned(veryHighMaxSaving));
    }


    @Test
    public void zeroInterestPaidForZeroCustomers(){
        Bank newBank = new BankImpl();
        assertEquals("Bank with zero customers does not return zero interest paid",new BigDecimal("0.00"), AnnualInterestCalculator.totalInterestPaid(newBank));
    }

    @Test
    public void interestPaidForOneCurrentCustomer(){
        Bank newBank = new BankImpl();
        Customer customer = new CustomerImpl("customer");
        Account account1 = new AccountImpl(customer, AccountType.CURRENT, "123");
        Account account2 = new AccountImpl(customer, AccountType.SAVINGS, "124");
        Account account3 = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "125");

        customer.deposit(low, account1);
        customer.deposit(mid, account2);
        customer.deposit( high, account3);
        newBank.addCustomer(customer);
        assertEquals("Bank with multiple accounts for one customer does not return expected interest paid",
                new BigDecimal("71.50"),
                AnnualInterestCalculator.totalInterestPaid(newBank));
    }

    @Test
    public void maxiSavingsInterestAdditionalGivesLowRate(){
        Bank newBank = new BankImpl();
        Customer customer = new CustomerImpl("customer");
        Account additionalAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS_ADD, "123");
        newBank.addCustomer(customer);
        customer.deposit( low, additionalAccount);
        assertEquals("Additional feature maxi savings does not record higher interest on zero withdrawals",
                new BigDecimal("25.00"),
                AnnualInterestCalculator.interestEarned(additionalAccount));

    }


    @Test
    public void maxiSavingsInterestAdditionalGivesHighRate(){
        Bank newBank = new BankImpl();
        Customer customer = new CustomerImpl("customer");
        Account additionalAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS_ADD, "123");
        newBank.addCustomer(customer);
        customer.deposit(low, additionalAccount);
        customer.withdraw( new BigDecimal("50"),additionalAccount);
        assertEquals("Additional feature maxi savings does not record lower interest on more than 0 withdrawals",
                new BigDecimal("0.45"),
                AnnualInterestCalculator.interestEarned(additionalAccount));
    }

    @Test
    public void maxiSavingsInterestAdditionalGivesHighRateWithWithdrawal(){
        Bank newBank = new BankImpl();
        Customer customer = new CustomerImpl("customer");
        Account additionalAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS_ADD, "123");
        newBank.addCustomer(customer);
        customer.deposit(low, additionalAccount);
        Transaction mockTx = mock(TransactionImpl.class);
        when(mockTx.getTransactionDate()).thenReturn(LocalDateTime.now().minusDays(15));
        when(mockTx.getAmount()).thenReturn(new BigDecimal("-10.00"));
        additionalAccount.addTransaction(mockTx);
        assertEquals("Additional feature maxi savings does not record lower interest on more than 0 withdrawals",
                new BigDecimal("24.50"),
                AnnualInterestCalculator.interestEarned(additionalAccount));
    }




}

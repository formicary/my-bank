package com.abc;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.entity.impl.AccountType;
import com.abc.entity.impl.CustomerImpl;
import com.abc.service.TransactionManager;
import com.abc.util.InterestCalculator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class InterestCalculatorTest {

    private static Account lowCurrent;
    private static Account midCurrent;
    private static Account highCurrent;

    private static Account lowSaving;
    private static Account midSaving;
    private static Account highSaving;

    private static Account lowMaxSaving;
    private static Account midMaxSaving;
    private static Account midHighMaxSaving;
    private static Account highMaxSaving;
    private static Account veryHighMaxSaving;

    private static TransactionManager transactionManager;

    private static BigDecimal low;
    private static BigDecimal mid;
    private static BigDecimal midHigh;
    private static BigDecimal high;
    private static BigDecimal veryHigh;

    @BeforeClass
    public static void setup(){

        lowCurrent = new Account(AccountType.CURRENT);
        midCurrent = new Account(AccountType.CURRENT);
        highCurrent = new Account(AccountType.CURRENT);

        lowSaving = new Account(AccountType.SAVINGS);
        midSaving = new Account(AccountType.SAVINGS);
        highSaving = new Account(AccountType.SAVINGS);

        lowMaxSaving = new Account(AccountType.MAXI_SAVINGS);
        midMaxSaving = new Account(AccountType.MAXI_SAVINGS);
        midHighMaxSaving = new Account(AccountType.MAXI_SAVINGS);
        highMaxSaving = new Account(AccountType.MAXI_SAVINGS);
        veryHighMaxSaving = new Account(AccountType.MAXI_SAVINGS);

        Customer customer = new CustomerImpl("Customer");
        customer.addAccount(lowCurrent);
        customer.addAccount(midCurrent);
        customer.addAccount(highCurrent);
        customer.addAccount(lowSaving);
        customer.addAccount(midSaving);
        customer.addAccount(highSaving);
        customer.addAccount(lowMaxSaving);
        customer.addAccount(midMaxSaving);
        customer.addAccount(midHighMaxSaving);
        customer.addAccount(highMaxSaving);
        customer.addAccount(veryHighMaxSaving);

        transactionManager = new TransactionManager(customer);
        low = new BigDecimal("500");
        mid = new BigDecimal("1000");
        midHigh = new BigDecimal("1500");
        high = new BigDecimal("2000");
        veryHigh = new BigDecimal("3000");

    }

    @Test
    public void currentAccountInterestLowAmount(){
        transactionManager.deposit(lowCurrent,low);
        assertEquals("Interest is not calculated correctly for current account of low balance.",
                new BigDecimal("0.50"),
                InterestCalculator.interestEarned(lowCurrent));
    }
    @Test
    public void currentAccountInterestForMidAmount(){
        transactionManager.deposit(midCurrent,mid);
        assertEquals("Interest is not calculated correctly for current account of mid balance.",
                new BigDecimal("1.00"),
                InterestCalculator.interestEarned(midCurrent));
    }

    @Test
    public void currentAccountInterestForHighAmount(){
        transactionManager.deposit(highCurrent,high);
        assertEquals("Interest is not calculated correctly for current account of high balance.",
                new BigDecimal("2.00"),
                InterestCalculator.interestEarned(highCurrent));
    }

    @Test
    public void savingAccountInterestLowAmount(){
        transactionManager.deposit(lowSaving,low);
        assertEquals("Interest is not calculated correctly for saving account of low balance.",
                new BigDecimal("0.50"),
                InterestCalculator.interestEarned(lowSaving));
    }
    @Test
    public void savingAccountInterestForMidAmount(){
        transactionManager.deposit(midSaving,mid);
        assertEquals("Interest is not calculated correctly for saving account of mid balance.",
                new BigDecimal("1.00"),
                InterestCalculator.interestEarned(midSaving));
    }

    @Test
    public void savingAccountInterestForHighAmount(){
        transactionManager.deposit(highSaving,high);
        assertEquals("Interest is not calculated correctly for saving account of high balance.",
                new BigDecimal("3.00"),
                InterestCalculator.interestEarned(highSaving));
    }


    @Test
    public void maxiSavingAccountInterestLowAmount(){
        transactionManager.deposit(lowMaxSaving,low);
        assertEquals("Interest is not calculated correctly for max saving account of low balance.",
                new BigDecimal("10.00"),
                InterestCalculator.interestEarned(lowMaxSaving));
    }
    @Test
    public void maxiSavingAccountInterestForMidAmount(){
        transactionManager.deposit(midMaxSaving,mid);
        assertEquals("Interest is not calculated correctly for max saving account of mid balance.",
                new BigDecimal("20.00"),
                InterestCalculator.interestEarned(midMaxSaving));
    }

    @Test
    public void maxiSavingAccountInterestForMidHighAmount(){
        transactionManager.deposit(midHighMaxSaving,midHigh);
        assertEquals("Interest is not calculated correctly for max saving account of mid-high balance.",
                new BigDecimal("45.00"),
                InterestCalculator.interestEarned(midHighMaxSaving));
    }

    @Test
    public void maxiSavingAccountInterestForHighAmount(){
        transactionManager.deposit(highMaxSaving,high);
        assertEquals("Interest is not calculated correctly for max saving account of high balance.",
                new BigDecimal("70.00"),
                InterestCalculator.interestEarned(highMaxSaving));
    }

    @Test
    public void maxiSavingAccountInterestForVeryHighAmount(){
        transactionManager.deposit(veryHighMaxSaving,veryHigh);
        assertEquals("Interest is not calculated correctly for max saving account of very high balance.",
                new BigDecimal("170.00"),
                InterestCalculator.interestEarned(veryHighMaxSaving));
    }

}

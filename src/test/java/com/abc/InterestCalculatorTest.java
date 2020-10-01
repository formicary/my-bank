package com.abc;

import com.abc.entity.impl.AccountImpl;
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

    private static TransactionManager transactionManager;

    private static BigDecimal low;
    private static BigDecimal mid;
    private static BigDecimal midHigh;
    private static BigDecimal high;
    private static BigDecimal veryHigh;

    @BeforeClass
    public static void setup(){

        lowCurrent = new AccountImpl(AccountType.CURRENT);
        midCurrent = new AccountImpl(AccountType.CURRENT);
        highCurrent = new AccountImpl(AccountType.CURRENT);

        lowSaving = new AccountImpl(AccountType.SAVINGS);
        midSaving = new AccountImpl(AccountType.SAVINGS);
        highSaving = new AccountImpl(AccountType.SAVINGS);

        lowMaxSaving = new AccountImpl(AccountType.MAXI_SAVINGS);
        midMaxSaving = new AccountImpl(AccountType.MAXI_SAVINGS);
        midHighMaxSaving = new AccountImpl(AccountType.MAXI_SAVINGS);
        highMaxSaving = new AccountImpl(AccountType.MAXI_SAVINGS);
        veryHighMaxSaving = new AccountImpl(AccountType.MAXI_SAVINGS);

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
    public void zeroBalanceLeavesZeroInterest(){
        assertEquals("Interest is not zero for empty account.",
                new BigDecimal("0.00"),
                InterestCalculator.interestEarned(lowCurrent));
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

package com.abc;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.entity.Transaction;
import com.abc.entity.impl.AccountType;
import com.abc.entity.impl.CustomerImpl;
import com.abc.service.TransactionManager;
import com.abc.util.InterestCalculator;
import org.junit.Before;
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

        transactionManager.deposit(midCurrent,mid);
        transactionManager.deposit(highCurrent,high);
        transactionManager.deposit(lowSaving,low);
        transactionManager.deposit(midSaving,mid);
        transactionManager.deposit(highSaving,high);
        transactionManager.deposit(lowMaxSaving,low);
        transactionManager.deposit(midMaxSaving,mid);
        transactionManager.deposit(midHighMaxSaving,midHigh);
        transactionManager.deposit(highMaxSaving,high);
        transactionManager.deposit(veryHighMaxSaving,veryHigh);

    }

    @Test
    public void currentAccountInterestLessThanThousand(){
        transactionManager.deposit(lowCurrent,low);
        assertEquals("Interest is not calculated correctly for current account of low balance.",
                new BigDecimal("0.5"),
                InterestCalculator.interestEarned(lowCurrent));
    }
    /*
    less than 1000
    test 1000
    test 2000

     */

}

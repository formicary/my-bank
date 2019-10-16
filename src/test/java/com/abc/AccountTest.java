package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class AccountTest {
       //private static final BigDecimal DOUBLE_DELTA = new BigDecimal(1e-15);
    private static final double DOUBLE_DELTA = 1e-15;


    @Test
    public void checkingAccountOneDayInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        BigDecimal tenThousand = new BigDecimal(10000);
        checkingAccount.deposit(tenThousand);
//        assertEquals(naughtPointOne, bank.totalInterestPaid(), DOUBLE_DELTA);
        Assert.assertEquals(0.027397335410264137, checkingAccount.interestEarnedDaily().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void checkingAccountOneYearInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        BigDecimal tenThousand = new BigDecimal(10000);
        checkingAccount.deposit(tenThousand);
//        assertEquals(naughtPointOne, bank.totalInterestPaid(), DOUBLE_DELTA);
        Assert.assertEquals(10.032440050643759, checkingAccount.getAccruedInterestForTimePeriod(365).doubleValue(), DOUBLE_DELTA);
    }


    @Test
    public void savingsAccountOneDayInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount();
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);

        BigDecimal oneThousandFiveHundred = new BigDecimal(1500);
        savingsAccount.deposit(oneThousandFiveHundred);


//        assertEquals(naughtPointOne, bank.totalInterestPaid(), DOUBLE_DELTA);
        Assert.assertEquals(0.00547945205479452, savingsAccount.interestEarnedDaily().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountOneYearInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount();
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);

        BigDecimal oneThousandFiveHundred = new BigDecimal(1500);
        savingsAccount.deposit(oneThousandFiveHundred);


//        assertEquals(naughtPointOne, bank.totalInterestPaid(), DOUBLE_DELTA);
        Assert.assertEquals(2.0040053276727763, savingsAccount.getAccruedInterestForTimePeriod(365).doubleValue(), DOUBLE_DELTA);

    }

    @Test
    public void maxiSavingsAccountDailyInterest() {
        Bank bank = new Bank();
        Account maxiSavings = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        BigDecimal oneThousandFiveHundred = new BigDecimal(10000);
        maxiSavings.deposit(oneThousandFiveHundred);


//        assertEquals(naughtPointOne, bank.totalInterestPaid(), DOUBLE_DELTA);
        Assert.assertEquals(0.0273972602739726, maxiSavings.interestEarnedDaily().doubleValue(), DOUBLE_DELTA);

    }

    @Test
    public void maxiSavingsAccountYearlyInterest() {
        Bank bank = new Bank();
        Account maxiSavings = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill").openAccount(maxiSavings);
        bank.addCustomer(bill);

        BigDecimal oneThousandFiveHundred = new BigDecimal(10000);
        maxiSavings.deposit(oneThousandFiveHundred);


//        assertEquals(naughtPointOne, bank.totalInterestPaid(), DOUBLE_DELTA);
        Assert.assertEquals(525.928444712144, maxiSavings.getAccruedInterestForTimePeriod(365).doubleValue(), DOUBLE_DELTA);

    }


}

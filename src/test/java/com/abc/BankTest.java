package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final BigDecimal DOUBLE_DELTA = new BigDecimal(1e-15);

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        BigDecimal oneHundred = new BigDecimal(100);
        checkingAccount.deposit(oneHundred);

        BigDecimal naughtPointOne = new BigDecimal(0.1);
        Assert.assertEquals(naughtPointOne.longValue(), bank.totalInterestPaid().longValue(), DOUBLE_DELTA.longValue());
        //Assert.assertEquals(naughtPointOne.longValue(), checkingAccount.interestEarned().longValue());
    }


    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));


        checkingAccount.deposit(Conversion.doubleToBigDecimalConverter(1500.0));

        assertEquals(2.0, bank.totalInterestPaid().longValue(), DOUBLE_DELTA.longValue());
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingAccount));

        maxiSavingAccount.deposit(Conversion.doubleToBigDecimalConverter(3000.0));

        assertEquals(170.0, bank.totalInterestPaid().longValue() , DOUBLE_DELTA.longValue());
    }
}

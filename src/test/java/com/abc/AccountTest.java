package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountTest {
//    private static final double DOUBLE_DELTA = 1e-15;
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
//        assertEquals(naughtPointOne, bank.totalInterestPaid(), DOUBLE_DELTA);
        Assert.assertEquals(naughtPointOne.longValue(), checkingAccount.interestEarned().longValue());
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));


        checkingAccount.deposit(Conversion.doubleToBigDecimalConverter(1500.0));

        assertEquals(2.0, checkingAccount.interestEarned().longValue(), DOUBLE_DELTA.longValue());
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingAccount));

        maxiSavingAccount.deposit(Conversion.doubleToBigDecimalConverter(3000.0));

        assertEquals(170.0, maxiSavingAccount.interestEarned().longValue() , DOUBLE_DELTA.longValue());
    }

    @Test
    public void maxi_savings_account2() {
        Bank bank = new Bank();
        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingAccount));

        maxiSavingAccount.deposit(Conversion.doubleToBigDecimalConverter(4000.0));

        assertEquals(270.0, maxiSavingAccount.interestEarned().longValue() , DOUBLE_DELTA.longValue());
    }
// Might need to store accounts with their individual transactions inside a hashmap
//    @Test
////    public void transferToAccount() {
////        Bank bank = new Bank();
////        Account savingsAccount = new Account(Account.SAVINGS);
////        Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
////        Customer bill = new Customer("Bill");
////        bank.addCustomer(bill);
////        bill.openAccount(maxiSavingAccount);
////        bill.openAccount(savingsAccount);
////
////        maxiSavingAccount.deposit(Conversion.doubleToBigDecimalConverter(4000.0));
////        maxiSavingAccount.transferToAccount(maxiSavingAccount, savingsAccount, Conversion.doubleToBigDecimalConverter(1000));
////
////        System.out.println(bill.getStatement(maxiSavingAccount));
//////        System.out.println(bill.getStatement(savingsAccount));
////
////        assertEquals(270.0, maxiSavingAccount.interestEarned().longValue() , DOUBLE_DELTA.longValue());
//    }

    @Test
    public void getAccountSummary() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        BigDecimal oneHundred = new BigDecimal(100);
        checkingAccount.deposit(oneHundred);

        BigDecimal naughtPointOne = new BigDecimal(0.1);
        System.out.println(checkingAccount.getAccountSummary());
        Assert.assertEquals(naughtPointOne.longValue(), checkingAccount.interestEarned().longValue());
    }

}

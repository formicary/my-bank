package com.abc;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void checkingAccount() {
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);

        checkingAccount.deposit(10000.0);
        checkingAccount.addInterest();

        assertEquals(0.03, checkingAccount.interestEarned(), DOUBLE_DELTA);
        assertEquals(10000.03, checkingAccount.sumAllTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = (new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(10000.0);
        savingsAccount.addInterest();

        assertEquals(0.05, savingsAccount.interestEarned(), DOUBLE_DELTA);
        assertEquals(10000.05, savingsAccount.sumAllTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountNoWithdrawal() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = (new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(10000.0);
        maxiSavingsAccount.addInterest();

        assertEquals(1.37, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
        assertEquals(10001.37, maxiSavingsAccount.sumAllTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountRecentWithdrawal(){
        String accountCreation = "01/03/2019 09:29:58";
        String transActionDate = "25/03/2019 10:31:48";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date d1 = format.parse(accountCreation);
            Date d2 = format.parse(transActionDate);

            Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS, d1);
            Customer bill = (new Customer("Bill").openAccount(maxiSavingsAccount));

            maxiSavingsAccount.deposit(11000.0);
            maxiSavingsAccount.withdraw(1000.0,d2);
            maxiSavingsAccount.addInterest();

            assertEquals(0.03, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
            assertEquals(10000.03, maxiSavingsAccount.sumAllTransactions(), DOUBLE_DELTA);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void maxiSavingsAccountNotRecentWithdrawal(){
        String accountCreation = "01/03/2019 09:29:58";
        String transActionDate = "10/03/2019 10:31:48";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date d1 = format.parse(accountCreation);
            Date d2 = format.parse(transActionDate);

            Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS, d1);
            Customer bill = (new Customer("Bill").openAccount(maxiSavingsAccount));

            maxiSavingsAccount.deposit(11000.0);
            maxiSavingsAccount.withdraw(1000.0,d2);
            maxiSavingsAccount.addInterest();

            assertEquals(1.37, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
            assertEquals(10001.37, maxiSavingsAccount.sumAllTransactions(), DOUBLE_DELTA);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //A large account balance was needed here to properly test that the interest was compounded everyday
    @Test
    public void compoundingInterest(){
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = (new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(1000000000.0);
        maxiSavingsAccount.addInterest();

        assertEquals(136986.3, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
        assertEquals(1000136986.3, maxiSavingsAccount.sumAllTransactions(), DOUBLE_DELTA);

        maxiSavingsAccount.addInterest();


        assertEquals(273991.37, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);

    }
}

package com.abc;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    // no withdrawals have taken place on account, so interest rate should be high
    @Test
    public void interestRateNoWithdrawals(){
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = bill.openMaxiSavingsAccount();

        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    // withdrawals have taken place, but over 10 days ago
    @Test
    public void interestRateWithNoRecentWithdrawal(){

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = bill.openMaxiSavingsAccount();

        bank.addCustomer(bill);

        Transaction depositTransaction = new Transaction(3000.0, Transaction.DEPOSIT);
        Transaction withdrawalTransaction = new Transaction(1500.0, Transaction.WITHDRAWAL);

        // change the transaction date to one over 10 days ago
        try {
            withdrawalTransaction.transactionDate = simpleDateFormat.parse("2019-06-15 12:00:00");
        } catch (ParseException e) {
            Assert.fail("error: parsing failed");
        }

        maxiSavingsAccount.transactions.add(depositTransaction);
        maxiSavingsAccount.transactions.add(withdrawalTransaction);

        maxiSavingsAccount.addFunds(1500.00);

        // update last withdrawal to match the withdrawal
        // have to do it like this so that I can manually change the date on the transaction
        maxiSavingsAccount.lastWithdrawal = withdrawalTransaction.transactionDate;

        // interest should be calculated using higher interest rate (5% of 1500.00)
        assertEquals(75.00, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    // withdrawal has taken place in last 10 days, so lower interest rate (0.1%) should be used
    @Test
    public void interestRateWithdrawalPast10Days(){
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = bill.openMaxiSavingsAccount();

        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(1500.00); // withdrawal took place today

        // uses lower interest rate (0.1% of $1500.00) as there was a recent transaction
        assertEquals(1.5, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }



    // Old Maxi Savings Interest Tests
    //------------------------------------------------------------------------------------------------------------------

    @Ignore
    public void testInterestRateUnder1000(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(400.00);

        assertEquals(8.00, maxiSaver.interestEarned(), DOUBLE_DELTA);
    }

    @Ignore
    public void testInterestRateUnder2000(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(1500.00);

        assertEquals(45.00, maxiSaver.interestEarned(), DOUBLE_DELTA);
    }

    @Ignore
    public void testInterestRateHighest(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(2500.00);

        assertEquals(120.00, maxiSaver.interestEarned(), DOUBLE_DELTA);
    }
}

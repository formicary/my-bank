package com.abc;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositValid(){
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

        maxiSaver.deposit(200.0);

        assertEquals(200.0, maxiSaver.getAccountBalance(), DOUBLE_DELTA);

    }

    @Test
    public void testDepositInvalid(){
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

        try{
            maxiSaver.deposit(-100.0);
            Assert.fail("Invalid deposit was accepted.");
        }catch(IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testWithdrawValid(){
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

        maxiSaver.deposit(200.0);
        maxiSaver.withdraw(100.0);

        assertEquals(100.0, maxiSaver.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawInvalid(){
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

        maxiSaver.deposit(100.0);

        try{
            maxiSaver.withdraw(-100.0);
            Assert.fail("Invalid withdraw was accepted.");
        }catch (IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    // no withdrawals have taken place on account, so interest rate should be high
    @Test
    public void interestRateNoWithdrawals(){
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = bill.openMaxiSavingsAccount();

        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, maxiSavingsAccount.interestEarnedAnnum(), DOUBLE_DELTA);
    }

    @Test
    public void interestRateWithdrawalPast10Days(){
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        Account maxiSavingsAccount = bill.openMaxiSavingsAccount();

        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(1500.00);

        assertEquals(1.5, maxiSavingsAccount.interestEarnedAnnum(), DOUBLE_DELTA);
    }

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

        try {
            depositTransaction.transactionDate = simpleDateFormat.parse("2018-06-15 12:30:01");
            withdrawalTransaction.transactionDate = simpleDateFormat.parse("2019-06-15 12:00:00");
        } catch (ParseException e) {
            Assert.fail("error: parsing failed");
        }

        maxiSavingsAccount.transactions.add(depositTransaction);
        maxiSavingsAccount.transactions.add(withdrawalTransaction);

        maxiSavingsAccount.addFunds(1500.00);

        maxiSavingsAccount.lastWithdrawal = withdrawalTransaction.transactionDate;

        assertEquals(75.00, maxiSavingsAccount.interestEarnedAnnum(), DOUBLE_DELTA);
    }



    // Old Maxi Savings Interest Tests
    //------------------------------------------------------------------------------------------------------------------
    @Ignore
    public void testInterestRateUnder1000(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(400.00);

        assertEquals(8.00, maxiSaver.interestEarnedAnnum(), DOUBLE_DELTA);
    }

    @Ignore
    public void testInterestRateUnder2000(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(1500.00);

        assertEquals(45.00, maxiSaver.interestEarnedAnnum(), DOUBLE_DELTA);
    }

    @Ignore
    public void testInterestRateHighest(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(2500.00);

        assertEquals(120.00, maxiSaver.interestEarnedAnnum(), DOUBLE_DELTA);
    }
}

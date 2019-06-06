package com.abc;

import org.junit.Test;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    Customer henry = new Customer("Henry","James","52 Abbey road","07656261464",30,"ASFA686ADG65");
    Account checkingAccount = new Checking_Account(henry,0, UUID.randomUUID());
    Account savingsAccount = new Savings_Account(henry,100,UUID.randomUUID());
    Account maxisavingsAccount = new Maxi_Savings_Account(henry,100,UUID.randomUUID());

    @Test
    public void createCheckingAccount(){
        Account checkingAccount = new Checking_Account(henry,0, UUID.randomUUID());
        assertEquals(true,checkingAccount instanceof Checking_Account );

    }
    public void createSavingsAccount(){
        Account savingsAccount = new Savings_Account(henry,100,UUID.randomUUID());
        assertEquals(true,savingsAccount instanceof Savings_Account );

    }
    public void createMaxiSavingsAccount(){
        Account maxisavingsAccount = new Maxi_Savings_Account(henry,100,UUID.randomUUID());
        assertEquals(false,maxisavingsAccount instanceof Checking_Account );

    }

    @Test
    public void checkDeposit() {
        checkingAccount.deposit(50);
        assertEquals(50,checkingAccount.getAmount());
    }
    @Test(expected = IllegalArgumentException.class)
    public  void checkDepositWithNegativeAmount(){
        checkingAccount.deposit(-100);
    }

    @Test
    public void checkWithdraw() {
        savingsAccount.withdraw(50);
        assertEquals(50,savingsAccount.getAmount());
    }
    @Test(expected = IllegalArgumentException.class)
    public  void checkWithdrawWithInsufficientBalance(){
        checkingAccount.withdraw(100);
    }
    @Test(expected = IllegalArgumentException.class)
    public  void checkWithdrawWithNegativeAmount(){
        checkingAccount.withdraw(-100);
    }
    @Test
    public void chekcDailyInterestEarned() {
        assertEquals(0.002,maxisavingsAccount.interestEarned(Account.InteType.DAILY),0.001);
    }
    @Test
    public void checkInterestDeposit(){
        checkingAccount.interest(10);
        assertEquals(Transaction.TranType.INTEREST,checkingAccount.getTransactions().get(0).getTransactionType());
        assertEquals(10,checkingAccount.getAmount());
    }
    @Test
    public  void CheckInterestEarnedYearly(){
        assertEquals(0.1, savingsAccount.interestCalculationYearly());
    }

@Test
 public void sumTransactions() {
        double tran =0;
        savingsAccount.deposit(50);
        checkingAccount.deposit(50);
        assertEquals(150,savingsAccount.getAmount());
        assertEquals(50,checkingAccount.getAmount());
        for(Account a:henry.getAccounts()){
            tran += a.sumTransactions();
        }
        assertEquals(100,tran);
    }
 //No withdrawal has been made in this account
    //This test is case sensitive, it tests for a specific date.
    //It is going to fail if you don't change the date.
    @Test
    public void checkFindDateDiffOfLastWithdraw() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse("01/06/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Should not use a setter for this attribute
        //It is only used for test purposes
        savingsAccount.setAccountCreationDate(date);
        assertEquals(5, savingsAccount.findDateDiffOfLastWithdraw());
    }
    //This test is not case sensitive
    //Case of no withdrawals made in this account
    @Test
    public void checkFindDateDiffOfLastWithdraw2() {
        assertEquals(0, savingsAccount.findDateDiffOfLastWithdraw());
    }
    //Withdrawal have been made on the same day so the day difference is zero
    @Test
    public void checkLastWithdrawal(){
        savingsAccount.withdraw(10);
        assertEquals(0,savingsAccount.findDateDiffOfLastWithdraw());
    }
    //Case where no withdrawal have been made in the last 10 days
    @Test
    public void interestEarnedForMaxiSavingsAccount(){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse("01/05/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        maxisavingsAccount.setAccountCreationDate(date);
        assertEquals(0.01, maxisavingsAccount.interestEarned(Account.InteType.DAILY),0.01);
    }
//Withdrawal has been made in the last 10 days
    @Test
    public void interestEarnedForMaxiSavingsAccountWithdrawal(){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse("01/05/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        maxisavingsAccount.setAccountCreationDate(date);
        maxisavingsAccount.withdraw(10);
        assertEquals(0.001, maxisavingsAccount.interestEarned(Account.InteType.DAILY),0.002);
    }


}
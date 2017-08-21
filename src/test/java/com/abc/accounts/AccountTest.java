package com.abc.accounts;

import com.abc.constants.AccountConstantsNew;
import com.abc.rates.Band;
import com.abc.rates.InterestRate;
import com.abc.utilities.Money;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * Created by vahizan on 09/08/2017.
 */
public class AccountTest {
    private Account account;
    @Before
    public void setUp(){
        account = new SavingsAccount();
    }
    //Test transactions
    @Test
    public void getRightAmountWithdrawn(){
        Money money = new Money(100);
        account.withdrawMoney(money);
        assertEquals(-100,account.total().amount(),0.0f);
    }

    @Test
    public void getRightAmountDeposited(){
        Money money = new Money(100);
        account.depositMoney(money);
        assertEquals(100, account.total().amount(), 0.0f);
    }

    //Test Bank Statement Output
    @Test
    public void correctBankStatementPresented(){
        Money money = new Money(100);
        account.depositMoney(money);
        Money moneyTwo = new Money(1000);
        account.depositMoney(moneyTwo);
        Money moneyThree = new Money(400);
        account.withdrawMoney(moneyThree);
        assertEquals(700,account.total().amount(),0.0f);
        assertTrue(account.bankStatement() != null);
        System.out.println(account.bankStatement());
    }

    //Test totalInterest paid
    @Test
    public void checkTotalInterestPaid(){
        //Savings Account Band
        Band band = new Band();
        Money moneyOne = new Money(0);
        Money moneyTwo = new Money(1000);
        Money moneyThree = new Money(2000);
        Money moneyFour = new Money(3000);
        Money moneyFive = new Money(4000);
        Money moneySix = new Money(5000);
        InterestRate rateOne=new InterestRate(0.0f, AccountConstantsNew.SAVINGS);
        InterestRate rateTwo=new InterestRate(0.1f, AccountConstantsNew.SAVINGS);
        InterestRate rateThree=new InterestRate(0.2f,AccountConstantsNew.SAVINGS);
        InterestRate rateFour=new InterestRate(0.3f,AccountConstantsNew.SAVINGS);
        InterestRate rateFive=new InterestRate(0.4f,AccountConstantsNew.SAVINGS);
        InterestRate rateSix=new InterestRate(0.5f,AccountConstantsNew.SAVINGS);
        band.addBand(moneyOne,rateOne);
        band.addBand(moneyTwo,rateTwo);
        band.addBand(moneyThree,rateThree);
        band.addBand(moneyFour,rateFour);
        band.addBand(moneyFive,rateFive);
        band.addBand(moneySix,rateSix);
        //Interest interest = new Interest();
    }

}

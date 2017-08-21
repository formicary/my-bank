package com.abc.rates;

import com.abc.constants.AccountConstantsNew;
import com.abc.utilities.Money;
import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vahizan on 21/08/2017.
 */
public class BandTest {
    private Band band;

    @Before
    public void setUp(){
        band = new Band();
        Money moneyTwo = new Money(1000);
        Money moneyThree = new Money(2000);
        Money moneyFour = new Money(3000);
        Money moneyFive = new Money(4000);
        Money moneySix = new Money(5000);
        InterestRate rateTwo=new InterestRate(0.1f, AccountConstantsNew.SAVINGS);
        InterestRate rateThree=new InterestRate(0.2f,AccountConstantsNew.SAVINGS);
        InterestRate rateFour=new InterestRate(0.3f,AccountConstantsNew.SAVINGS);
        InterestRate rateFive=new InterestRate(0.4f,AccountConstantsNew.SAVINGS);
        InterestRate rateSix=new InterestRate(0.5f,AccountConstantsNew.SAVINGS);
        band.addBand(moneyTwo,rateTwo);
        band.addBand(moneyThree,rateThree);
        band.addBand(moneyFour,rateFour);
        band.addBand(moneyFive,rateFive);
        band.addBand(moneySix,rateSix);
    }

    @Test
     public void obtainLowestBandOfInterestRate(){
        Money money = new Money(500);
        float interestValue = band.interestInBand(money);
        System.out.println(interestValue);
        assertEquals(0.1f, interestValue, 0.001f);
    }
    @Test
    public void obtainFirstBandOfInterestRate(){
        Money money = new Money(1000);
        float interestValue = band.interestInBand(money);
        System.out.println(interestValue);
        assertEquals(0.1f, interestValue, 0.001f);
    }
    @Test
    public void obtainSecondBandOfInterestRate(){
        Money money = new Money(2000);
        float interestValue = band.interestInBand(money);
        System.out.println(interestValue);
        assertEquals(0.2f, interestValue, 0.001f);
    }
    @Test
    public void obtainThirdBandOfInterestRate(){
        Money money = new Money(3000);
        float interestValue = band.interestInBand(money);
        System.out.println(interestValue);
        assertEquals(0.3f, interestValue, 0.001f);
    }
    @Test
    public void obtainFourthBandOfInterestRate(){
        Money money = new Money(4000);
        float interestValue = band.interestInBand(money);
        System.out.println(interestValue);
        assertEquals(0.4f, interestValue, 0.001f);
    }
    @Test
    public void obtainFifthBandOfInterestRate(){
        Money money = new Money(5000);
        float interestValue = band.interestInBand(money);
        System.out.println(interestValue);
        assertEquals(0.5f, interestValue, 0.001f);
    }
    @Test
    public void obtainHighestBandOfInterestRate(){
        Money money = new Money(9000);
        float interestValue = band.interestInBand(money);
        System.out.println(interestValue);
        assertEquals(0.5f, interestValue, 0.001f);
    }






}

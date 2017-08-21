package com.abc.rates;

import com.abc.constants.AccountConstantsNew;
import com.abc.organisation.Bank;
import com.abc.utilities.Money;
import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vahizan on 21/08/2017.
 */
public class InterestTest {
    private Interest interest;

    @Before
    public void setUp(){
        //Create Savings Account Interest
        Band band = new Band();
        InterestRate rate = new InterestRate(0.1f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(0),rate);
        InterestRate rateOverThousand = new InterestRate(0.2f,AccountConstantsNew.SAVINGS);
        band.addBand(new Money(1000),rateOverThousand);
        interest= new Interest(rate,band);

    }

    @Test
    public void calculateLowestBandDailyInterest(){
        Money initialMoney = new Money(100);
        Money money= interest.calculateDailyInterest(initialMoney);
        System.out.println(money.amount());
       assertEquals(0.0005, money.amount(), 0.0001);
    }

    @Test
    public void calculateHighestBandInterest(){
        Money initialMoney = new Money(1000);
        Money money= interest.calculateDailyInterest(initialMoney);
        System.out.println(money.amount());
        assertEquals(0.005,money.amount(),0.001);
    }
}

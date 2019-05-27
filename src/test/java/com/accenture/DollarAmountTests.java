package com.accenture;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class DollarAmountTests {

    @Test
    public void addition() {

        DollarAmount money1 = DollarAmount.fromInt(1);
        DollarAmount money2 = DollarAmount.fromInt(2);
        DollarAmount expectedResult = DollarAmount.fromInt(3);

        DollarAmount actualResult = money1.plus(money2);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void subtraction() {

        DollarAmount money1 = DollarAmount.fromInt(10);
        DollarAmount money2 = DollarAmount.fromInt(4);
        DollarAmount expectedResult = DollarAmount.fromInt(6);

        DollarAmount actualResult = money1.minus(money2);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void subtractionIntoMinus() {

        DollarAmount money1 = DollarAmount.fromInt(4);
        DollarAmount money2 = DollarAmount.fromInt(10);
        DollarAmount expectedResult = DollarAmount.fromInt(-6);

        DollarAmount actualResult = money1.minus(money2);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void additionFloatingPoint() {

        DollarAmount money1 = DollarAmount.fromDouble(1.2);
        DollarAmount money2 = DollarAmount.fromDouble(1.6);
        DollarAmount expectedResult = DollarAmount.fromDouble(2.8);

        DollarAmount actualResult = money1.plus(money2);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void subtractionFloatingPoint() {

        DollarAmount money1 = DollarAmount.fromDouble(10.5);
        DollarAmount money2 = DollarAmount.fromDouble(2.6);
        DollarAmount expectedResult = DollarAmount.fromDouble(7.9);

        DollarAmount actualResult = money1.minus(money2);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void subtractionFloatingPoint_2DecimalPlaces() {

        DollarAmount money1 = DollarAmount.fromDouble(10.50);
        DollarAmount money2 = DollarAmount.fromDouble(2.64);
        DollarAmount expectedResult = DollarAmount.fromDouble(7.86);

        DollarAmount actualResult = money1.minus(money2);

        assertEquals(expectedResult, actualResult);
    }



    @Test
    public void additionOfZero() {

        DollarAmount money1 = DollarAmount.fromDouble(0.00);
        DollarAmount money2 = DollarAmount.fromDouble(0.00);
        DollarAmount expectedResult = DollarAmount.fromDouble(0.00);

        DollarAmount actualResult = money1.plus(money2);

        assertEquals(expectedResult, actualResult);
    }



}

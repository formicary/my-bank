package com.abc.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.PublicKey;

public class Money extends BigDecimal {

    /**
     * for quickly initialising a zero value
     */
    public static Money ZERO = new Money("0");


    /**
     * A high accuracy method of storing monetary value that does not suffer from the same errors as double
     * @param val string representation of the monetary value
     */
    public Money(String val) {
        super(val);
    }

    /**
     * helper function to reduce the amount of casting of BigDecimal to Money
     * @return Money
     */
    @Override
    public Money add(BigDecimal amount){
        return new Money(super.add(amount).toString());
    }

    /**
     * helper function to reduce the amount of casting of BigDecimal to Money
     * @return Money
     */
    @Override
    public Money subtract(BigDecimal amount){
        return new Money(super.subtract(amount).toString());
    }

    /**
     * helper function to reduce the amount of casting of BigDecimal to Money
     * @return Money
     */
    @Override
    public Money multiply(BigDecimal amount){
        return new Money(super.multiply(amount).toString());
    }

    /**
     * helper function to reduce the amount of casting of BigDecimal to Money
     * @return Money
     */
    @Override
    public Money negate(){
        return new Money(super.negate().toString());
    }

    /**
     * helper function to reduce the amount of casting of BigDecimal to Money
     * @return Money
     */
    @Override
    public Money abs(){
        return new Money(super.abs().toString());
    }

    /**
     * prints the value exactly
     * @return
     */
    @Override
    public String toString() {
        return super.toPlainString();
    }
}

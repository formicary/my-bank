package com.abc.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class Money extends BigDecimal {
    /**
     * A high accuracy method of storing monetary value that does not suffer from the same errors as double
     * @param val string representation of the monetary value
     */
    public Money(String val) {
        super(val);
    }
}

package com.abc.util;

import java.math.BigDecimal;

public class USDFactory {

    private USDFactory() {}

    public static Money DollarToMoney(Long dollarAmount) {
        return new Money(BigDecimal.valueOf(dollarAmount * 100));
    }
}

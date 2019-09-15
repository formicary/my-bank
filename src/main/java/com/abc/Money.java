package com.abc;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Money implements Comparable<Money> {
    private BigDecimal amount;

    public Money(String amount){
        this.amount = new BigDecimal(amount);
    }

    private Money(BigDecimal amount){
        this.amount = amount;
    }

    public BigDecimal getBigDecimal(){
        return amount;
    }

    public Money add(Money adder){
        return new Money(amount.add(adder.getBigDecimal()));
    }

    public Money subtract(Money subtractor){
        return new Money(amount.subtract(subtractor.getBigDecimal()));
    }

    public Money multiply(Money mutiplicator){
        return new Money(amount.multiply(mutiplicator.getBigDecimal()));
    }

    public int compareTo(Money comparitor){
        return amount.compareTo(comparitor.getBigDecimal());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.compareTo(money.getBigDecimal()) == 0;
    }

    public String toString(){
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }

}

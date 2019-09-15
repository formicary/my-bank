package com.abc;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Wrapper for BigInteger to improve consistency
 */
public class Money implements Comparable<Money> {
    private BigDecimal amount;

    public Money(String amount){
        this.amount = new BigDecimal(amount);
    }

    //Private constructor for coverting back to Money after BigDecimal calculation
    private Money(BigDecimal amount){
        this.amount = amount;
    }

    // for internal BigDecimal calculations
    private BigDecimal getBigDecimal(){
        return amount;
    }

    /**
     * this + adder
     * @param adder
     * @return
     */
    public Money add(Money adder){
        return new Money(amount.add(adder.getBigDecimal()));
    }

    /**
     * this - subtractor
     * @param subtractor
     * @return
     */
    public Money subtract(Money subtractor){
        return new Money(amount.subtract(subtractor.getBigDecimal()));
    }

    /**
     * this - multiplicator
     * @param mutiplicator
     * @return
     */
    public Money multiply(Money mutiplicator){
        return new Money(amount.multiply(mutiplicator.getBigDecimal()));
    }

    /**
     * Equivalent to BigDecimal -1, 0, 1 if comparitor is less than, equal to, or greater than respectively.
     * @param comparitor
     * @return
     */
    public int compareTo(Money comparitor){
        return amount.compareTo(comparitor.getBigDecimal());
    }

    /**
     * Weaker equivalence than big decimal,
     * We only checks value, NOT, Scale, so 1.0 == 1.00
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.compareTo(money.getBigDecimal()) == 0;
    }

    /**
     * Output Money as string in currency of bank (USD)
     * @return
     */
    public String toString(){
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }

}

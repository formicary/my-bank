package com.accenture;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Currency;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

public class DollarAmount implements Comparable<DollarAmount> {

    // This application is hard-coded for US dollars.
    private static final Currency currency = Currency.getInstance("USD");

    // Also known as "Bankers Rounding"
    private static final RoundingMode rounding = RoundingMode.HALF_EVEN;

    // The number of decimal places used for arithmetic between instances. This was chosen arbitrarily.
    private static final int DECIMAL_PLACES = 5;

    // Calculations were accuracy is paramount, floating point arithmetic MUST NOT be used due to inherent in-precision.
    // Big decimal uses Integer based values for its internal representation, avoiding this issue.
    private BigDecimal amount;

    // Instantiated through its static helper methods
    private DollarAmount(BigDecimal amount) {
        this.amount = amount;
        validateState();
    }

    public static DollarAmount fromDouble(Double dollarAmount) {
        return new DollarAmount(asBigDecimal(dollarAmount));
    }
    public static DollarAmount fromInt(int dollarAmount) {
        return new DollarAmount(new BigDecimal(dollarAmount));
    }

    public BigDecimal getAmount() { return amount; }

    public boolean isPlus(){
        return amount.compareTo(ZERO) > 0;
    }
    public boolean isMinus(){
        return amount.compareTo(ZERO) <  0;
    }
    public boolean isZero(){
        return amount.compareTo(ZERO) ==  0;
    }

    public DollarAmount minus(DollarAmount that){
        return new DollarAmount(amount.subtract(that.amount));
    }

    public DollarAmount plus(DollarAmount that){
        return new DollarAmount(amount.add(that.amount));
    }

    public boolean eq(DollarAmount that) {
        return compareAmount(that) == 0;
    }

    public boolean gt(DollarAmount that) {
        return compareAmount(that) > 0;
    }

    public boolean gteq(DollarAmount that) {
        return compareAmount(that) >= 0;
    }

    public boolean lt(DollarAmount that) {
        return compareAmount(that) < 0;
    }

    public boolean lteq(DollarAmount that) {
        return compareAmount(that) <= 0;
    }

    public static DollarAmount sum(Collection<DollarAmount> amounts){
        DollarAmount sum = new DollarAmount(ZERO);
        for(DollarAmount dollarAmount : amounts){
            sum = sum.plus(dollarAmount);
        }
        return sum;
    }

    public DollarAmount times(int aFactor){
        BigDecimal factor = new BigDecimal(aFactor);
        BigDecimal newAmount = amount.multiply(factor);
        return new DollarAmount(newAmount);
    }

    public DollarAmount times(double factor){
        BigDecimal newAmount = amount.multiply(asBigDecimal(factor));
        newAmount = newAmount.setScale(DECIMAL_PLACES, rounding);
        return  new DollarAmount(newAmount);
    }

    public DollarAmount div(double divisor){
        BigDecimal newAmount = amount.divide(asBigDecimal(divisor), rounding);
        return new DollarAmount(newAmount);
    }

    public DollarAmount negate(){
        return times(-1);
    }


    private void validateState(){
        if( amount == null ) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
    }

    // Currency's normal printing decimal places
    private int getNumDecimalsForCurrency(){
        return currency.getDefaultFractionDigits();
    }


    public int compareTo(DollarAmount that) {
        final int EQUAL = 0;

        if ( this == that ) return EQUAL;

        //the object fields are never null
        int comparison = this.amount.compareTo(that.amount);
        if ( comparison != EQUAL ) return comparison;

        comparison = this.currency.getCurrencyCode().compareTo(
                that.currency.getCurrencyCode()
        );
        if ( comparison != EQUAL ) return comparison;


        comparison = this.rounding.compareTo(that.rounding);
        if ( comparison != EQUAL ) return comparison;

        return EQUAL;
    }


    // While internal representation is held up to DECIMAL_PLACES. Printing uses up to the currency's typical display precision.
    public String toString(){
        return amount.setScale(getNumDecimalsForCurrency(),rounding).toPlainString() + " " + currency.getSymbol();
    }

    public boolean equals(Object aThat){
        if (this == aThat) return true;
        if (! (aThat instanceof DollarAmount) ) return false;
        DollarAmount that = (DollarAmount) aThat;

        return that.amount.equals(this.amount);
    }

    public int hashCode(){
        return Objects.hash(amount);
    }

    /** Ignores scale: 0 same as 0.00 */
    private int compareAmount(DollarAmount aThat){
        return this.amount.compareTo(aThat.amount);
    }


    private static BigDecimal asBigDecimal(double aDouble){
        String asString = Double.toString(aDouble);
        return new BigDecimal(asString);
    }
}

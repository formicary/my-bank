package com.abc.util;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Currency;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

public final class Money implements Comparable<Money>, Serializable {

    /**
     * Thrown when a set of <code>Money</code> objects do not have matching currencies.
     *
     * <P>For example, adding together Euros and Dollars does not make any sense.
     */
    public static final class MismatchedCurrencyException extends RuntimeException {
        MismatchedCurrencyException(String message){
            super(message);
        }
    }

    /**
     * Set default values for currency and rounding style.
     *
     * <em>Your application must call this method upon startup</em>.
     * This method should usually be called only once (upon startup).
     *
     * <P>The recommended rounding style is {@link RoundingMode#HALF_EVEN}, also called
     * <em>banker's rounding</em>; this rounding style introduces the least bias.
     *
     * <P>Setting these defaults allow you to use the more terse constructors of this class,
     * which are much more convenient.
     *
     * <P>(In a servlet environment, each app has its own classloader. Calling this
     * method in one app will never affect the operation of a second app running in the same
     * servlet container. They are independent.)
     */
    public static void init(Currency defaultCurrency, RoundingMode defaultRounding){
        DEFAULT_CURRENCY = defaultCurrency;
        DEFAULT_ROUNDING = defaultRounding;
    }

    /**
     * Full constructor.
     *
     * @param amount is required, can be positive or negative. The number of
     * decimals in the amount cannot <em>exceed</em> the maximum number of
     * decimals for the given {@link Currency}. It's possible to create a
     * <code>Money</code> object in terms of 'thousands of dollars', for instance.
     * Such an amount would have a scale of -3.
     * @param currency is required.
     * @param roundingStyle is required, must match a rounding style used by
     * {@link BigDecimal}.
     */
    public Money(BigDecimal amount, Currency currency, RoundingMode roundingStyle){
        this.amount = amount;
        this.currency = currency;
        this.rounding = roundingStyle;
        validateState();
    }

    /**
     * Constructor taking only the money amount.
     *
     * <P>The currency and rounding style both take default values.
     * @param amount is required, can be positive or negative.
     */
    public Money(BigDecimal amount){
        this(amount, DEFAULT_CURRENCY, DEFAULT_ROUNDING);
    }

    /**
     * Constructor taking the money amount and currency.
     *
     * <P>The rounding style takes a default value.
     * @param amount is required, can be positive or negative.
     * @param currency is required.
     */
    public Money(BigDecimal amount, Currency currency){
        this(amount, currency, DEFAULT_ROUNDING);
    }

    /** Return the amount passed to the constructor. */
    public BigDecimal getAmount() { return amount; }

    /** Return the currency passed to the constructor, or the default currency. */
    public Currency getCurrency() { return currency; }

    /** Return the rounding style passed to the constructor, or the default rounding style. */
    public RoundingMode getRoundingStyle() { return rounding; }

    /**
     * Return <code>true</code> only if <code>that</code> <code>Money</code> has the same currency
     * as this <code>Money</code>.
     */
    public boolean isSameCurrencyAs(Money that){
        boolean result = false;
        if ( that != null ) {
            result = this.currency.equals(that.currency);
        }
        return result;
    }

    /** Return <code>true</code> only if the amount is positive. */
    public boolean isPlus(){
        return amount.compareTo(ZERO) > 0;
    }

    /** Return <code>true</code> only if the amount is negative. */
    public boolean isMinus(){
        return amount.compareTo(ZERO) <  0;
    }

    /** Return <code>true</code> only if the amount is zero. */
    public boolean isZero(){
        return amount.compareTo(ZERO) ==  0;
    }

    /**
     * Add <code>that</code> <code>Money</code> to this <code>Money</code>.
     * Currencies must match.
     */
    public Money plus(Money that){
        checkCurrenciesMatch(that);
        return new Money(amount.add(that.amount), currency, rounding);
    }

    /**
     * Subtract <code>that</code> <code>Money</code> from this <code>Money</code>.
     * Currencies must match.
     */
    public Money minus(Money that){
        checkCurrenciesMatch(that);
        return new Money(amount.subtract(that.amount), currency, rounding);
    }

    /**
     * Sum a collection of <code>Money</code> objects.
     * Currencies must match. You are encouraged to use database summary functions
     * whenever possible, instead of this method.
     *
     * @param moneys collection of <code>Money</code> objects, all of the same currency.
     * If the collection is empty, then a zero value is returned.
     * @param currencyIfEmpty is used only when <code>moneys</code> is empty; that way, this
     * method can return a zero amount in the desired currency.
     */
    public static Money sum(Collection<Money> moneys, Currency currencyIfEmpty){
        Money sum = new Money(ZERO, currencyIfEmpty);
        for(Money money : moneys){
            sum = sum.plus(money);
        }
        return sum;
    }

    /**
     * Equals (insensitive to scale).
     *
     * <P>Return <code>true</code> only if the amounts are equal.
     * Currencies must match.
     * This method is <em>not</em> synonymous with the <code>equals</code> method.
     */
    public boolean eq(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) == 0;
    }

    /**
     * Greater than.
     *
     * <P>Return <code>true</code> only if  'this' amount is greater than
     * 'that' amount. Currencies must match.
     */
    public boolean gt(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) > 0;
    }

    /**
     * Greater than or equal to.
     *
     * <P>Return <code>true</code> only if 'this' amount is
     * greater than or equal to 'that' amount. Currencies must match.
     */
    public boolean gteq(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) >= 0;
    }

    /**
     * Less than.
     *
     * <P>Return <code>true</code> only if 'this' amount is less than
     * 'that' amount. Currencies must match.
     */
    public boolean lt(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) < 0;
    }

    /**
     * Less than or equal to.
     *
     * <P>Return <code>true</code> only if 'this' amount is less than or equal to
     * 'that' amount. Currencies must match.
     */
    public boolean lteq(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) <= 0;
    }

    /**
     * Multiply this <code>Money</code> by an integral factor.
     *
     * The scale of the returned <code>Money</code> is equal to the scale of 'this'
     * <code>Money</code>.
     */
    public Money times(int aFactor){
        BigDecimal factor = new BigDecimal(aFactor);
        BigDecimal newAmount = amount.multiply(factor);
        return new Money(newAmount, currency, rounding);
    }

    /**
     * Multiply this <code>Money</code> by an non-integral factor (having a decimal point).
     *
     * <P>The scale of the returned <code>Money</code> is equal to the scale of
     * 'this' <code>Money</code>.
     */
    public Money times(double factor){
        BigDecimal newAmount = amount.multiply(asBigDecimal(factor));
        newAmount = newAmount.setScale(getNumDecimalsForCurrency(), rounding);
        return  new Money(newAmount, currency, rounding);
    }

    /**
     * Divide this <code>Money</code> by an integral divisor.
     *
     * <P>The scale of the returned <code>Money</code> is equal to the scale of
     * 'this' <code>Money</code>.
     */
    public Money div(int aDivisor){
        BigDecimal divisor = new BigDecimal(aDivisor);
        BigDecimal newAmount = amount.divide(divisor, rounding);
        return new Money(newAmount, currency, rounding);
    }

    /**
     * Divide this <code>Money</code> by an non-integral divisor.
     *
     * <P>The scale of the returned <code>Money</code> is equal to the scale of
     * 'this' <code>Money</code>.
     */
    public Money div(double divisor){
        BigDecimal newAmount = amount.divide(asBigDecimal(divisor), rounding);
        return new Money(newAmount, currency, rounding);
    }

    /** Return the absolute value of the amount. */
    public Money abs(){
        return isPlus() ? this : times(-1);
    }

    /** Return the amount x (-1). */
    public Money negate(){
        return times(-1);
    }

    /**
     * Returns
     * {@link #getAmount()}.getPlainString() + space + {@link #getCurrency()}.getSymbol().
     *
     * <P>The return value uses the runtime's <em>default locale</em>, and will not
     * always be suitable for display to an end user.
     */
    public String toString(){
        return amount.toPlainString() + " " + currency.getSymbol();
    }

    /**
     * Like {@link BigDecimal#equals(java.lang.Object)}, this <code>equals</code> method
     * is also sensitive to scale.
     *
     * For example, <code>10</code> is <em>not</em> equal to <code>10.00</code>
     * The {@link #eq(Money)} method, on the other hand, is <em>not</em>
     * sensitive to scale.
     */
    public boolean equals(Object aThat){
        if (this == aThat) return true;
        if (! (aThat instanceof Money) ) return false;
        Money that = (Money)aThat;
        for(int i = 0; i < this.getSigFields().length; ++i){
            if (!Objects.equals(this.getSigFields()[i], that.getSigFields()[i])){
                return false;
            }
        }
        return true;
    }

    public int hashCode(){
        return Objects.hash(getSigFields());
    }

    public int compareTo(Money that) {
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

    // PRIVATE //

    /**
     * The money amount.
     * Never null.
     * @serial
     */
    private BigDecimal amount;

    /**
     * The currency of the money, such as US Dollars or Euros.
     * Never null.
     * @serial
     */
    private final Currency currency;

    /**
     * The rounding style to be used.
     * See {@link BigDecimal}.
     * @serial
     */
    private final RoundingMode rounding;

    /**
     * The default currency to be used if no currency is passed to the constructor.
     */
    public static Currency DEFAULT_CURRENCY;

    /**
     * The default rounding style to be used if no currency is passed to the constructor.
     * See {@link BigDecimal}.
     */
    private static RoundingMode DEFAULT_ROUNDING;

    private Object[] getSigFields() {
        return new Object[] {amount, currency, rounding};
    }

    /**
     * Determines if a deserialized file is compatible with this class.
     *
     * Maintainers must change this value if and only if the new version
     * of this class is not compatible with old versions. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html> details. </a>
     *
     * Not necessary to include in first version of the class, but
     * included here as a reminder of its importance.
     */
    private static final long serialVersionUID = 7526471155622776147L;

    /**
     * Always treat de-serialization as a full-blown constructor, by
     * validating the final state of the de-serialized object.
     */
    private void readObject(
            ObjectInputStream inputStream
    ) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        inputStream.defaultReadObject();
        //defensive copy for mutable date field
        //BigDecimal is not technically immutable, since its non-final
        amount = new BigDecimal( amount.toPlainString() );
        //ensure that object state has not been corrupted or tampered with maliciously
        validateState();
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        outputStream.defaultWriteObject();
    }

    private void validateState(){
        if( amount == null ) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if( currency == null ) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        if ( amount.scale() > getNumDecimalsForCurrency() ) {
            throw new IllegalArgumentException(
                    "Number of decimals is " + amount.scale() + ", but currency only takes " +
                            getNumDecimalsForCurrency() + " decimals."
            );
        }
    }

    private int getNumDecimalsForCurrency(){
        return currency.getDefaultFractionDigits();
    }

    private void checkCurrenciesMatch(Money aThat){
        if (! this.currency.equals(aThat.getCurrency())) {
            throw new MismatchedCurrencyException(
                    aThat.getCurrency() + " doesn't match the expected currency : " + currency
            );
        }
    }

    /** Ignores scale: 0 same as 0.00 */
    private int compareAmount(Money aThat){
        return this.amount.compareTo(aThat.amount);
    }

    private BigDecimal asBigDecimal(double aDouble){
        String asString = Double.toString(aDouble);
        return new BigDecimal(asString);
    }
}
package com.abc.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public class Money implements Comparable<Money>{

	private static final BigDecimal MINUS_OME = new BigDecimal(-1);
	private static final Currency USD = Currency.getInstance("USD");

	public static final Money ZERO_USD = new Money("0.0");
	
	private final BigDecimal amount;
	private final Currency currency;

	
	/**
	 * Creates new money in USD
	 * 
	 * @param amount
	 */
	public Money(String amount) {
		this(new BigDecimal(amount), USD);
	}

	
	/**
	 * Creates new money in USD
	 * 
	 * @param amount
	 */
	public Money(BigDecimal amount) {
		this(amount, USD);
	}

	
	/**
	 * Creates new money in given currency
	 * 
	 *  
	 * @param amount
	 * @param currency
	 */
	public Money(BigDecimal amount, Currency currency) {
		Objects.requireNonNull(amount);
		Objects.requireNonNull(currency);

		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	

	public Currency getCurrency() {
		return currency;
	}

	public boolean areLowerThenZero() {
		return amount.compareTo(BigDecimal.ZERO) == -1;
	}

	public String getFormatted() {
		return currency.getSymbol() + getAmount().abs();
	}

	
	
	public Money plus(Money money) {
		Objects.requireNonNull(money);
		if (!money.getCurrency().equals(currency)) {
			throw new IllegalArgumentException("Could not add money in different currency: " +currency);
		}
		return new Money(amount.add(money.getAmount()), currency);
	}
	
	public Money minus(Money money) {
		Objects.requireNonNull(money);
		if (!money.getCurrency().equals(currency)) {
			throw new IllegalArgumentException("Could not add money in different currency: " +currency);
		}
		return new Money(amount.subtract(money.getAmount()), currency);
	}
	

	public Money negate() {
		return new Money(amount.multiply(MINUS_OME), currency);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currency.getCurrencyCode() == null) ? 0 : currency.getCurrencyCode().hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Money)) {
			return false;
		}
		Money other = (Money) obj;
		return (currency.getCurrencyCode().equals(other.currency.getCurrencyCode()) 
				&& (amount.compareTo(other.amount) == 0));
	}

	@Override
	public String toString() {
		return getFormatted();
	}


	public int compareTo(Money money) {
		Objects.requireNonNull(money);
		return this.amount.compareTo(money.amount);
	}
}

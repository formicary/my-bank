package com.abc.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Money {

	

	private static final BigDecimal MINUS_OME = new BigDecimal(-1);
	private static final Currency USD = Currency.getInstance("USD");

	public static final Money ZERO_USD = new Money("0.0");
	
	private final BigDecimal amount;
	private final Currency currency;

	public Money(String amount) {
		this(new BigDecimal(amount), USD);
	}

	public Money(BigDecimal amount) {
		this(amount, USD);
	}

	public Money(BigDecimal amount, Currency currency) {
		Objects.requireNonNull(amount);
		Objects.requireNonNull(currency);

		this.amount = amount;
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
		return currency.getSymbol() + getAmount().abs().setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	public Money add(Money money) {
		Objects.requireNonNull(money);
		if (!money.getCurrency().equals(currency)) {
			throw new IllegalArgumentException("Currencies must be the same");
		}
		return new Money(amount.add(money.getAmount()), currency);
	}

	public Money negate() {
		return new Money(amount.multiply(MINUS_OME), currency);
	}

	@Override
	public String toString() {
		return getFormatted();
	}
}

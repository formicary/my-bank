package com.abc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Currency;

/**
 * Class representing a transaction of funds.
 * 
 * @author Christopher J. Smith
 *
 */
public class Transaction {
	// Constants for value precision
	private static final int DECIMAL_PLACES = 4;
	private static final int STANDARD_DECIMAL_PLACES = 2;

	// Constants for formatting currency.
	public static final Currency CURRENCY = Currency.getInstance("USD");
	public static final String CURRENCY_SYMBOL = CURRENCY.getSymbol(Locale.US);
	public static final String PATTERN = "###,###,###,###,###,###.00";
	private static final DecimalFormat FORMATTER = new DecimalFormat(PATTERN);

	// Object state variables.
	private final BigDecimal AMOUNT;
	private final Date TRANSACTION_DATE;

	/**
	 * Constructor to create a transaction at the current time, of a given amount.
	 * 
	 * @param amount
	 *            is the amount the transaction represents.
	 * @throws IllegalArgumentException
	 *             if amount is null.
	 */
	public Transaction(BigDecimal amount) {
		// Ensure amount isn't null
		if (amount == null) {
			throw new IllegalArgumentException(Transaction.class + "::amount cannot be null.");
		}
		// Scale the amount to the set precision.
		AMOUNT = amount.setScale(DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
		TRANSACTION_DATE = DateProvider.getInstance().now();
	}

	/**
	 * Constructor to create a transaction at the current time, of a given amount.
	 * 
	 * @param amount
	 *            is the amount the transaction represents. Must be parsable by
	 *            BigDecimal.
	 * @throws IllegalArgumentException
	 *             if amount is null, or if amount isn't parsable.
	 */
	public Transaction(String amount) {
		// Check amount isn't null.
		if (amount == null) {
			throw new IllegalArgumentException(Transaction.class + "::amount cannot be null.");
		}

		// Try make a BigDecimal given the string provided.
		try {
			AMOUNT = new BigDecimal(amount).setScale(DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(Transaction.class + "::amount must be a parsable number.");
		}
		TRANSACTION_DATE = DateProvider.getInstance().now();
	}

	/**
	 * Constructor to create a transaction at the current time, of a given amount.
	 * 
	 * @param amount
	 *            is the amount the transaction represents.
	 * @throws IllegalArgumentException
	 *             if amount is null.
	 */
	public Transaction(int amount) {
		this(new BigDecimal(amount));
	}

	/**
	 * Constructor to create a transaction at the current time, of a given amount.
	 * 
	 * @param amount
	 *            is the amount the transaction represents.
	 * @throws IllegalArgumentException
	 *             if amount is null.
	 */
	public Transaction(long amount) {
		this(new BigDecimal(amount));
	}

	/**
	 * Constructor to create a transaction at the current time, of a given amount.
	 * 
	 * @param amount
	 *            is the amount the transaction represents.
	 * @throws IllegalArgumentException
	 *             if amount is null.
	 */
	public Transaction(short amount) {
		this(new BigDecimal(amount));
	}

	/**
	 * Constructor to create a transaction at the current time, of a given amount.
	 * 
	 * @param amount
	 *            is the amount the transaction represents.
	 * @throws IllegalArgumentException
	 *             if amount is null.
	 */
	public Transaction(double amount) {
		this(new BigDecimal(amount));
	}

	/**
	 * Constructor to create a transaction at the current time, of a given amount.
	 * 
	 * @param amount
	 *            is the amount the transaction represents.
	 * @throws IllegalArgumentException
	 *             if amount is null.
	 */
	public Transaction(float amount) {
		this(new BigDecimal(amount));
	}

	/**
	 * Get the amount the transaction was for rounded down to the currency standard.
	 * 
	 * @return Returns the amount the transaction was for rounded down to the
	 *         currency standard.
	 */
	public BigDecimal getAmount() {
		return AMOUNT.setScale(STANDARD_DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
	}

	/**
	 * Get the exact amount that is stored by the transaction not rounded.
	 * 
	 * @return Returns the non-rounded amount of the transaction.
	 */
	public BigDecimal getExactAmount() {
		return AMOUNT;
	}

	/**
	 * Get the date and time of the transaction.
	 * 
	 * @return Returns the date and time of the transaction.
	 */
	public Date getDate() {
		return TRANSACTION_DATE;
	}

	@Override
	public String toString() {
		return ("Transaction of " + CURRENCY_SYMBOL + getAmount() + " on " + TRANSACTION_DATE);
	}

	/**
	 * The toString method but has the amount in exact representation.
	 * 
	 * @return Returns a string representation of a transaction with exact fund amounts.
	 */
	public String toStringExact() {
		return ("Transaction of " + CURRENCY_SYMBOL + getExactAmount() + " on " + TRANSACTION_DATE);
	}

	/**
	 * A utility method for formatting currency to a string.
	 * 
	 * @param value
	 *            is the amount to be formatted as currency.
	 * @return Returns the formatted version of value. If value is null returns
	 *         blank string.
	 */
	public static String toCurrecy(BigDecimal value) {
		if (value == null) {
			return "";
		} else {
			return CURRENCY_SYMBOL + FORMATTER.format(value);
		}
	}

}

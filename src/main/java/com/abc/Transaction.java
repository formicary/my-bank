package com.abc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Currency;

public class Transaction {
    private static final int DECIMAL_PLACES = 4;
    private static final int STANDARD_DECIMAL_PLACES = 2;
    public static final Currency CURRENCY = Currency.getInstance("USD");
    public static final String CURRENCY_SYMBOL = CURRENCY.getSymbol(Locale.US);
    public static final String PATTERN = "###,###,###,###,###,###.00";
    private static final DecimalFormat FORMATTER = new DecimalFormat(PATTERN);
	
	private final BigDecimal AMOUNT;
    private final Date TRANSACTION_DATE;

    public Transaction(BigDecimal amount) {
    	AMOUNT = amount.setScale(DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
    	TRANSACTION_DATE = DateProvider.getInstance().now();
    }
    
    public Transaction(String amount) {
    	try {
    		AMOUNT = new BigDecimal(amount).setScale(DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
    	} catch(NumberFormatException e) {
    		throw new IllegalArgumentException("amount must be a parsable number.");
    	}
    	TRANSACTION_DATE = DateProvider.getInstance().now();
    }
    
    public Transaction(int amount) {this(new BigDecimal(amount));}
    public Transaction(long amount) {this(new BigDecimal(amount));}
    public Transaction(short amount) {this(new BigDecimal(amount));}
    public Transaction(double amount) {this(new BigDecimal(amount));}
    public Transaction(float amount) {this(new BigDecimal(amount));}
    
    public BigDecimal getAmount() {
    	return AMOUNT.setScale(STANDARD_DECIMAL_PLACES, BigDecimal.ROUND_DOWN);
    }
    
    public BigDecimal getExactAmount() {
    	return AMOUNT;
    }
    
    public Date getDate() {
    	return TRANSACTION_DATE;
    }
     
    @Override
    public String toString() {
    	return("Transaction of " + CURRENCY_SYMBOL + getAmount() + " on " + TRANSACTION_DATE);
    }
    
    public String toStringExact() {
    	return("Transaction of " + CURRENCY_SYMBOL + getExactAmount() + " on " + TRANSACTION_DATE);
    }
    
    public static String toCurrecy(BigDecimal value) {
    	return CURRENCY_SYMBOL + FORMATTER.format(value);
    }
    
}

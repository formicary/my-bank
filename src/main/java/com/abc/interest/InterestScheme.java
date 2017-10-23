package com.abc.interest;

import java.math.BigDecimal;
import java.util.List;

import com.abc.Transaction;

public interface InterestScheme {

	public BigDecimal getInterest(List<Transaction> transactions);

}
package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
	private BigDecimal amount;
	private LocalDate transactionDate;

	public Transaction(BigDecimal amount) {
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().getCurrentDate();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

}

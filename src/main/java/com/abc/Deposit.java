package com.abc;

import java.math.BigDecimal;
import java.util.Date;

public class Deposit {

	private final BigDecimal amount;
	private Date depositDate;

	public Deposit(BigDecimal amount) {
		this.amount = amount;
		this.depositDate = new Date();
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return String.format(Constants.DEPOSIT + amount + Constants.DATE_FORMAT_START + depositDate + Constants.DATE_FORMAT_END);
	}

}
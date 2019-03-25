package com.abc;

import java.math.BigDecimal;
import java.util.Date;

public class Withdrawal {

	private final BigDecimal amount;
	private Date withdrawalDate;

	public Withdrawal(BigDecimal amount) {
		this.amount = amount;
		this.withdrawalDate = new Date();
	}

	public Date getWithdrawalDate() {
		return withdrawalDate;
	}

	public void setWithdrawalDate(Date withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return String.format(Constants.WITHDRAWAL + amount + Constants.DATE_FORMAT_START + withdrawalDate + Constants.DATE_FORMAT_END);
	}

}

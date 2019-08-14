package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account{

	protected SavingsAccount() {
		super(AccountType.SAVINGS);
		// TODO Auto-generated constructor stub
	}

	// 0.1 then 0.2 after $1000
	@Override
	public Money interestEarned() {
		Money threshold = new Money("1000");
		Money sumTrans = this.sumTransactions();
		// if over threshold then 0.2
		if (sumTrans.getAmount().compareTo(threshold.getAmount()) == 1){
			return new Money(sumTrans.getAmount().multiply(new BigDecimal(0.002)));
		}else {
			return new Money(sumTrans.getAmount().multiply(new BigDecimal(0.001)));
		}
	}

}

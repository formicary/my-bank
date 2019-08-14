package com.abc;

import java.math.BigDecimal;

public class MaxiSavingsAccount extends Account{

	protected MaxiSavingsAccount() {
		super(AccountType.MAXI_SAVINGS);
	}

	// 2% then 5% between 1000-2000 then 10% after that
	@Override
	public Money interestEarned() {
		Money threshold1 = new Money("1000");
		Money threshold2 = new Money("2000");
		Money sumTrans = this.sumTransactions();
		
		
		if (sumTrans.getAmount().compareTo(threshold1.getAmount()) == -1)
			return new Money(sumTrans.getAmount().multiply(new BigDecimal(0.02)));
		
		else if ((sumTrans.getAmount().compareTo(threshold1.getAmount()) == 1) 
				&& sumTrans.getAmount().compareTo(threshold2.getAmount()) == -1)
			return new Money(sumTrans.getAmount().multiply(new BigDecimal(0.05)));
		
		else {
			return new Money(sumTrans.getAmount().multiply(new BigDecimal(0.1)));
		}
	}

}

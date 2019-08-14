package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account{

	public CheckingAccount(){
		super(AccountType.CHECKING);
	}
	
	
	// Flat rate of 0.1%
	@Override
	public Money interestEarned() {
		return new Money(this.sumTransactions().getAmount().multiply(new BigDecimal(0.001)));
	}


	
//  public double interestEarned() {
//  double amount = sumTransactions();
//  switch(accountType){
//      case SAVINGS:
//          if (amount <= 1000)
//              return amount * 0.001;
//          else
//              return 1 + (amount-1000) * 0.002;
////      case SUPER_SAVINGS:
////          if (amount <= 4000)
////              return 20;
//      case MAXI_SAVINGS:
//          if (amount <= 1000)
//              return amount * 0.02;
//          if (amount <= 2000)
//              return 20 + (amount-1000) * 0.05;
//          return 70 + (amount-2000) * 0.1;
//      default:
//          return amount * 0.001;
//  }
//}

}

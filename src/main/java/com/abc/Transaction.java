package com.abc;

import java.util.Date;

public class Transaction {
    private final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
   public Transaction(double amount, Date transactionDate) {
       this.amount = amount;
       this.transactionDate = transactionDate;
   }
   
   public double getAmount() {
	   return amount;
   }
   
   public Date getTransactionDate() {
	   return transactionDate;
   }
   
   public boolean isWithdraw() {
	   if (amount < 0) {
		   return true;
	   }else 
		   return false;
   }

}

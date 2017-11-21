package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;
    private boolean type;
    


	private Date transactionDate;
   


    public void setType(boolean type) {
		this.type = type;
	}


	public Transaction(double amount, boolean type) {
          this.amount = amount;
          this.transactionDate = DateProvider.getInstance().now();
          this.type=type;
         
      }
  

   public double getAmount() {
		return amount;
	}

 public Date getTransactionDate() {
		return transactionDate;
	}


 public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}



 public boolean getType() {
		return type;
	}



}

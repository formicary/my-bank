package com.abc;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    public final Money MONEY;
    private final Date TRANSACTION_DATE;
    // Every tx should have a unique, unchanging id
    private final UUID ID;

    public Transaction(Money money) {
    	// Check if the amount is not 0 (can be minus or positive) just not 0
    	if(money.getAmount().compareTo(new Money("0.00").getAmount()) == 0){
    		throw new IllegalArgumentException("Transaction value must not be 0");
    	}
    	this.MONEY = money;
        this.TRANSACTION_DATE = DateProvider.getInstance().now();
        this.ID = UUID.randomUUID();
    }

	public Money getMoney() {
		return MONEY;
	}

	public Date getTransactionDate() {
		return TRANSACTION_DATE;
	}

	public UUID getID() {
		return ID;
	}

	@Override
	public String toString() {
		return MONEY.getAmount() + "\t" + this.getTransactionDate() + "\t" + this.getID();
	}

}

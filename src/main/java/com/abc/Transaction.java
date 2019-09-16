package com.abc;

import java.util.Date;

/**
 * Transaction, stores amount and infers type from +ve/-ve value
 */
public class Transaction {
    private static final String WITHDRAWAL = "withdrawal";
    private static final String DEPOSIT = "deposit";

    private final Money amount;
    private final Date transactionDate;
    private final String type;

    public Transaction(Money amount){
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = (amount.compareTo(new Money("0")) < 0) ? WITHDRAWAL : DEPOSIT;
    }

    /**
     * @return "withdrawal" if negative amount, otherwise "deposit"
     */
    public String getType() {
        return type;
    }

    /**
     * @return amount of transaction
     */
    public Money getAmount(){
        return amount;
    };

    /**
     * @return date transaction was made
     */
    public Date getTransactionDate(){
        return transactionDate;
    }

}

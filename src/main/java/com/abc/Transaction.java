/*Edited by: Foyaz Hasnath*/
package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    public Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount(){
        return amount;
    }

}

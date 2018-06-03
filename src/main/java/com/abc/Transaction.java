package com.abc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This is a class for Transaction. It contains the date and amount of the transaction.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class Transaction {

    private final BigDecimal amount;
    private Date transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * This method gets the amount of money involved in the transaction
     * @return The amount of money
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method gets the transaction date of the transaction.
     * @return The transaction date.
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * This method sets the transaction date.
     * @param transactionDate
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * This method works out the date of 10 days after the transaction date.
     * @return
     */
    public Date getTransactionDatePlus10Days(){
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(transactionDate);
        cal.add(Calendar.DATE, 10);
        return cal.getTime();
    }

}

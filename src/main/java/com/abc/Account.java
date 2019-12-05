package com.abc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;


    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * @param amount to add to account
     */
    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * @param amount to remove from account
     */
    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public int getAccountType() {
        return accountType;
    }

    /**
     * @return The sum of all transactions
     */
    public BigDecimal sumTransactions() {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t: transactions)
            amount = amount.add(t.amount);
        return amount;
    }

    /**
     * Calculate compound interest by recalculating balance for each day since first transaction.
     * Assumes transactions are in chronological order
     * @return The interest earned
     */
    public BigDecimal interestEarned() {
        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal interest = BigDecimal.ZERO;
        int i = 0; //indexer for transactions
        int lastWithdraw = 11;//days since lastwithdraw (MAXI limit is 10)
        Calendar start = Calendar.getInstance();
        start.setTime(transactions.get(0).transactionDate);
        Date end = Calendar.getInstance().getTime();

        //increment days
        for (; !start.getTime().after(end); start.add(Calendar.DATE, 1)) {
            //add transactions to balance
            while (i < transactions.size() && !start.before(transactions.get(i))) {
                Transaction transaction = transactions.get(i);
                balance = balance.add(transaction.amount);
                //reset when there has been withdrawal
                if(transaction.amount.compareTo(BigDecimal.ZERO) < 0) {
                    lastWithdraw = 0;
                }
                //prevent overflow
                if(lastWithdraw < 11) {
                    lastWithdraw++;
                }
                i++;
            }
            BigDecimal dailyInterest = dailyInterest(balance, lastWithdraw);
            interest = interest.add(dailyInterest);
            balance = balance.add(dailyInterest);
        }
        return interest;
    }

    /**
     *  Calculate daily interest
     * @param amount The current balance
     * @param lastWithdraw Days since last withdrawal
     */
    private BigDecimal  dailyInterest(BigDecimal amount, int lastWithdraw) {
        BigDecimal savingsLimit = new BigDecimal(1000);
        BigDecimal pointOne = new BigDecimal(0.001);
        BigDecimal pointTwo = new BigDecimal(0.002);
        BigDecimal five = new BigDecimal(0.05);

        if(accountType == SAVINGS) {
            BigDecimal earned = BigDecimal.ZERO;
            if (amount.compareTo(savingsLimit) > 0) {
                //calculate interest earned for over 1000
                amount = amount.subtract(savingsLimit);
                earned = earned.add(amount.multiply(pointTwo));
                earned = earned.add(BigDecimal.ONE);
            }else {
                //calcuate interest under 1000
                earned = earned.add(amount.multiply(pointOne));
            }
            return earned;
        }else if(accountType == MAXI_SAVINGS) {
            //check if 10 days has past since last withdraw
            if (lastWithdraw > 10) {
                return amount.multiply(five);
            } else {
                return amount.multiply(pointOne);
            }
        }else{
            return amount.multiply(pointOne);
        }
    }
}

package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public static final double CHECKING_INTEREST = 1.001;
    public static final double SAVINGS_INTEREST_1 = 1.001;
    public static final double SAVINGS_INTEREST_2 = 1.002;
    public static final double MAXI_INTEREST_1 = 1.001;
    public static final double MAXI_INTEREST_2 = 1.05;
    public static final int MAXI_INTEREST_2_THRESH_DAYS = 10;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    // Overloaded methods to allow testing of various deposit dates
    public void deposit(double amount) {
        deposit(amount,DateProvider.getInstance().now());
    }
    public void deposit(double amount,Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,date));
        }
    }
    public void withdraw(double amount) {
        withdraw(amount,DateProvider.getInstance().now());
    }
    public void withdraw(double amount,Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else { //TODO if no overdraft must check if enough money in the account
            transactions.add(new Transaction(-amount,date));
        }
    }

    public void transfer(double amount, Account recipient) {
        /**
         * Transfers the amount from THIS account to recipient's account
         */
        // no need to check amount since withdraw and deposit both do it
         this.withdraw(amount);
         recipient.deposit(amount);
    }

    /*public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * SAVINGS_INTEREST_1;
                else
                    return 1 + (amount-1000) * SAVINGS_INTEREST_2;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                for (Transaction t: transactions) {
                    if(t.before(MAXI_INTEREST_2_THRESH_DAYS))
                        return amount * MAXI_INTEREST_1;
                }
                return amount * MAXI_INTEREST_2;
            default:
                return amount * CHECKING_INTEREST;
        }
    }*/
    public double interestEarned() {
        // Computer the interest amount for every date difference
        double amount = 0.0;
        for(int i=0; i< transactions.size(); i++) {
            long diff = dayDifference((i+1)>= transactions.size()?new Transaction(0):transactions.get(i+1)
                    ,transactions.get(i)); // Messy however it computes the day difference of
                                           // current and next transaction effectively

            amount += transactions.get(i).getAmount();
            switch(accountType){
                case SAVINGS:
                    if (amount <= 1000)
                        amount *= Math.pow(SAVINGS_INTEREST_1,diff);
                    else
                        amount =  (1000*Math.pow(SAVINGS_INTEREST_1,diff)) + ((amount-1000) * Math.pow(SAVINGS_INTEREST_2,diff));
                    break;
                case MAXI_SAVINGS:
                    for (Transaction t: transactions) {
                        if(t.before(MAXI_INTEREST_2_THRESH_DAYS)) {
                            amount *= Math.pow(MAXI_INTEREST_1,diff);
                            break;
                        }
                    }
                    amount *= Math.pow(MAXI_INTEREST_2,diff);
                    break;
                default:
                    amount *= Math.pow(CHECKING_INTEREST,diff);
            }
        }
        return round(amount-sumTransactions(),2); // Return the computed interest only
    }
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
    private double round(double a, double decimalPlaces) {
        double buff = (10.0*decimalPlaces);
        return Math.round(a* buff)/buff;
    }
    public long dayDifference(Transaction t1, Transaction t2) {
        return TimeUnit.DAYS.convert((t1.getTransactionDate().getTime() -
                                      t2.getTransactionDate().getTime()), TimeUnit.MILLISECONDS);
    }
    public int getAccountType() {
        return accountType;
    }

}

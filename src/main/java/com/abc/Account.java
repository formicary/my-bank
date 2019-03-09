package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private DateProvider dp;
    private Date creationDate;
    private Date lastWithdraw;
    private DateProvider date;
    public List<Transaction> transactions;

    //constructor
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();

        this.dp = new DateProvider();
        creationDate = dp.now();
        lastWithdraw = dp.now();
    }


    //set lastwithdraw manually
    public void setLastWithdraw(Date date)
    {
        lastWithdraw = date;
    }

    //add money
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    //remove money
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }

        lastWithdraw = dp.now();
    }


    //return interest earned
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                
                long dateDiff = dp.now().getTime() - lastWithdraw.getTime();
                int daysDiff = (int) (dateDiff / (24 * 60 * 60 * 1000));

                //if no withdraw in past 10 days => 5%
                if (daysDiff>10)
                {
                    return amount * 0.005;
                }
                //else 1%
                else
                {
                    return amount * 0.001;
                }               
            default:
                return amount * 0.001;
        }
    }

    //get balance
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    //check if transaction exists
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    //get account type
    public int getAccountType() {
        return accountType;
    }

    //get interest daily Rate
    public double getDailyInterestRate(){
        double amount = sumTransactions();
        double yearlyRate;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                {
                    return 0.01/365;
                }else{
                    return 0.02/365;
                }                    
            case MAXI_SAVINGS:               
                long dateDiff = dp.now().getTime() - lastWithdraw.getTime();
                int daysDiff = (int) (dateDiff / (24 * 60 * 60 * 1000));

                //if no withdraw in past 10 days => 5%
                if (daysDiff>10)
                {
                    return 0.05/365;
                }
                //else 1%
                else
                {
                    return 0.01/365;
                }               
            default:
                return 0.01/365;
        }
    }
    
    //apply rate to account balance
    public void applyInterestRate()
    {
        double interestRate = getDailyInterestRate();
        double toAdd = sumTransactions()*interestRate;
        deposit(toAdd);
    }

}

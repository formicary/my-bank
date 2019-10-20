package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;


    private final int accountType;
    private boolean lastdepositorwithdrawal = false;
    public List<Transaction> transactions;
    public DateProvider dateProvider = DateProvider.getInstance();



    public Date dateofwithdrawal;
    public Date dateofdeposit;
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();

    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            dateofdeposit = dateProvider.now();

            lastdepositorwithdrawal = true;
            //Last thing done was a deposit

        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            dateofwithdrawal = dateProvider.now();
            lastdepositorwithdrawal = false;
            //Last thing done was a withdrawal

        }
    }

    public void transfer(Account account, double amount){
        withdraw(amount);
        account.deposit(amount);
    }

    public long checkdayspassed(Date date1, Date date2)  {

        long difference = ((date1.getTime() - date2.getTime())/86400000);
        return Math.abs(difference);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        double overamount = 0;
        double standardinterest = 0;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000) {
                    if (lastdepositorwithdrawal == false) {
                        return (amount * Math.pow((0.001 / 365), checkdayspassed(dateProvider.now(), dateofwithdrawal)));
                        //Calculate based on date of withdrawal
                    } else if (lastdepositorwithdrawal == true) {
                        return (amount * Math.pow((0.001 / 365), checkdayspassed(dateProvider.now(), dateofdeposit)));
                        //Calculate based on date of deposit
                    }
                }
                else{
                    if (lastdepositorwithdrawal == false) {
                        standardinterest = (1000 * Math.pow((0.001/365), checkdayspassed(dateProvider.now(), dateofwithdrawal)));
                        overamount = amount - 1000;
                        return  standardinterest + (overamount) * Math.pow((0.002/365), checkdayspassed(dateProvider.now(), dateofwithdrawal));
                        //Calculate based on date of withdrawal
                    } else if (lastdepositorwithdrawal == true) {
                        standardinterest = (1000 * Math.pow((0.001/365), checkdayspassed(dateProvider.now(), dateofdeposit)));
                        overamount = amount - 1000;
                        return  standardinterest + (overamount) * Math.pow((0.002/365), checkdayspassed(dateProvider.now(), dateofdeposit));
                        //Calculate based on date of deposit
                    }

                }


//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if(checkdayspassed(dateProvider.now(), dateofwithdrawal) >=10){
                    return (amount * Math.pow((0.05/365), checkdayspassed(dateProvider.now(), dateofwithdrawal)));
                }
            default:
                if (lastdepositorwithdrawal == false) {
                    return amount * Math.pow((0.001/365), checkdayspassed(dateProvider.now(), dateofwithdrawal));
                }else
                {
                    return amount * Math.pow((0.001/365), checkdayspassed(dateProvider.now(), dateofdeposit));
                }
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}

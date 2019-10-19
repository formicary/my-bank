package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;


    private final int accountType;
    public List<Transaction> transactions;
    public DateProvider dateProvider = DateProvider.getInstance();
    public Date currentdate;

    public Date dateofwithdrawal;
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.currentdate = dateProvider.now();


    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            dateofwithdrawal = this.currentdate;
        }
    }

    public void transfer(Account account, double amount){
        withdraw(amount);
        account.deposit(amount);
    }

    public long checkdayspassed(Date date)  {

        long difference = ((date.getTime() - dateofwithdrawal.getTime())/86400000);
        return Math.abs(difference);
    }

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
                if(checkdayspassed(currentdate) >=10){
                    return amount * 0.05;
                }
            default:
                return amount * 0.001;
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

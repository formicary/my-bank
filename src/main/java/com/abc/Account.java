package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

    static final int CHECKING = 0;
    static final int SAVINGS = 1;
    static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        double total= sumTransactions();
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if(amount>total){
                throw new IllegalArgumentException("Overdraw is not allowed");
            }
            else{
                transactions.add(new Transaction(-amount));
            }
        }
}

    public double interestEarned() {
        double amount = sumTransactions();
        Calendar cal = Calendar.getInstance();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return dailyInterest(amount,0.001,cal.getTime());
                else
                    return  dailyInterest(amount,0.001,cal.getTime()) + dailyInterest(amount-1000,0.002,cal.getTime());

            case MAXI_SAVINGS:
                cal.add(Calendar.DATE, -10);
                if(checkIfTransactionsExist(cal.getTime())){
                    cal.add(Calendar.DATE, +10);
                    return dailyInterest(amount,0.001,cal.getTime());
                }
                else
                    cal.add(Calendar.DATE, +10);
                    return dailyInterest(amount,0.05,cal.getTime());

            default:
                return dailyInterest(amount,0.001,cal.getTime());
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public boolean checkLeapYear(int year)
    {
            if(year % 400 == 0)
            {
                return true;
            }
            else if (year % 100 == 0)
            {
               return false;
            }
            else if(year % 4 == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
    }
    public double dailyInterest(double initialAmount,double interest,Date now){
        double amount=initialAmount;
        int totalNumberOfDays;
        //Used deprecated function because I did not want to calculate using miliseconds number given by now.getTime()
        int year=now.getYear()+1900;
        if(checkLeapYear(year))
            totalNumberOfDays=366;
        else
            totalNumberOfDays=365;
        interest/=totalNumberOfDays;
        for(int i=0 ;i<=totalNumberOfDays;i++){
            amount+=amount*interest;
        }
        return amount-initialAmount;
    }

    private boolean checkIfTransactionsExist(Date check) {
        for (Transaction t : transactions) {
            if (t.getDate().after(check) && t.getAmount()<0) {
                return true;
            }
        }
        return false;

    }

    public int getAccountType() {
        return accountType;
    }
    public List<Transaction> getTransactions(){
        return this.transactions;
    }
    public void printTransactions(){
        for (Transaction i:transactions){
            System.out.println(i.getAmount()+" "+ i.getDate());
        }
    }

}

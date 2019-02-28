package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
/*----------------------------------------------------------------------------- 
                            Abstract Account Class
-----------------------------------------------------------------------------*/
abstract class Account{
    private List<Transaction> transactions;
    
    public Account(){
        this.transactions = new ArrayList<Transaction>();
    }
    
    public void deposit(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }
    public void withdraw(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }
    public double sumTransactions(){
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getTransactionAmount();
        
        return amount;
    }

    public List<Transaction> getTransactions(){
        return this.transactions;
    }
    
    public abstract double interestEarned();
    public abstract String getAccountType();
}

/*-----------------------------------------------------------------------------
                            Checking Account Class
-----------------------------------------------------------------------------*/
class CheckingAccount extends Account{
    public double interestEarned(){
        return sumTransactions() * 0.001;
    }

    public String getAccountType(){
        return "Checking Account\n";
    }
}

/*-----------------------------------------------------------------------------
                            Savings Account Class
-----------------------------------------------------------------------------*/
class SavingsAccount extends Account {
    public double interestEarned() {
        double amount = sumTransactions();

        if (amount <= 1000){
            return amount * 0.001;
        }else{
            return 1 + ((amount - 1000) * 0.002);
        }
    }

    public String getAccountType() {
        return "Savings Account\n";
    }
}

/*-----------------------------------------------------------------------------
                            Maxi-Savings Account Class
-----------------------------------------------------------------------------*/
class MaxiSavingsAccount extends Account {
    public double interestEarned() {
        double amount = sumTransactions();
        boolean five_percent=true;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);
        Date back=cal.getTime();

        for(Transaction t: getTransactions()){
            if(t.getTransactionAmount()<0 && t.getTransactionDate().after(back)){
                five_percent=false;
                break;
            }
        }

        if(five_percent==true){
            return amount * 0.05;
        }

        return amount * 0.001;
    }

    public String getAccountType() {
        return "Maxi Savings Account\n";
    }
}



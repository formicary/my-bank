package com.abc;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    private final int accountType;
    private double balance;
    public List<Transaction> transactions;
    //tracking variables
    private DateTime lastWithdrawal;
    private DateTime lastInterest;
    private double annualInterest;
    private double interestSum;
    
    public Account(int accountType) {
        if(accountType < 0 || accountType > 2){
            throw new IllegalArgumentException("accountType must be 0,1 or 2 (checkings,savings or maxisavings)");
        }
        else {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.lastInterest = LocalDateTime.now().toDateTime();
        this.lastWithdrawal = LocalDateTime.now().toDateTime();
        this.balance=0;
        this.interestSum=0;
        
        }
    }

    //pre-loaded account
    public Account(int accountType, double amount, DateTime lastInt) {
        if(accountType < 0 || accountType > 2){
            throw new IllegalArgumentException("accountType must be 0,1 or 2 (checkings,savings or maxisavings)");
        }
        else {
            this.interestSum=0;
            this.balance=0;
            this.lastInterest=lastInt;
            this.lastWithdrawal=lastInt;
            this.accountType = accountType;
            this.transactions = new ArrayList<Transaction>();
            this.deposit(amount,lastInt);            
        }
    }
   
    public void deposit(double amount) {
        if (Days.daysBetween(this.lastInterest, LocalDateTime.now().toDateTime()).getDays()>=1){
            double interest=this.interestEarned()*this.balance;
            this.interestSum+=interest;
            this.deposit(interest);
            
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if(transactions.add(new Transaction(amount)));
            this.balance+=amount;
        }        
    }
    //deposit at different dates
    public void deposit(double amount, DateTime transDate) {
        if (Days.daysBetween(this.lastInterest, transDate).getDays()>=1){
            double interest=this.interestEarned()*this.balance;
            this.interestSum+=interest;
            this.deposit(interest,transDate);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if(transactions.add(new Transaction(amount,transDate)));
            this.balance+=amount;
        }
        
    }
 
    public void withdraw(double amount) {
        if (Days.daysBetween(this.lastInterest, LocalDateTime.now().toDateTime()).getDays()>=1){
            double interest=this.interestEarned()*this.balance;
            this.interestSum+=interest;
            this.deposit(interest);
            }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } 
        else if (amount > this.balance) {
            throw new IllegalArgumentException("insufficient balance");
        } 
        else {
            transactions.add(new Transaction(-amount));
            this.balance-=amount;
            this.lastWithdrawal=LocalDateTime.now().toDateTime();
            }
    }
    public void withdraw(double amount, DateTime transDate) {
        if (Days.daysBetween(this.lastInterest, transDate).getDays()>=1){
            double interest=this.interestEarned()*this.balance;
            this.interestSum+=interest;
            this.deposit(interest,transDate);
            }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } 
        else if (amount > this.balance) {
            throw new IllegalArgumentException("insufficient balance");
        } 
        else {
            transactions.add(new Transaction(-amount,transDate));
            this.balance-=amount;
            this.lastWithdrawal=transDate;
            }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000){
                    this.annualInterest = (double) 0.001;
                    }
                else{
                    this.annualInterest = (double) 0.002;
                    }
                break;
            case MAXI_SAVINGS:
                if (Days.daysBetween(this.lastWithdrawal, LocalDateTime.now().toDateTime()).getDays()>10){
                    this.annualInterest=(double) 0.05;
                }
                else
                    this.annualInterest=(double) 0.001;
                break;
            default:
                this.annualInterest=0.001;
        }
            
            int days4interest=Days.daysBetween(this.lastInterest, LocalDateTime.now().toDateTime()).getDays();
            this.lastInterest = LocalDateTime.now().toDateTime();
            
            return helper.dailyInterest(days4interest,this.annualInterest);
        
    }
    
    public double getInterestSum(){
        return this.interestSum;
    }
    
    public double interestEarned(DateTime transDate) {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000){
                    this.annualInterest = (double) 0.001;
                    }
                else{
                    this.annualInterest = (double) 0.002;
                    }
                break;
            case MAXI_SAVINGS:
                if (Days.daysBetween(this.lastWithdrawal, transDate).getDays()>10){
                    this.annualInterest=(double) 0.05;
                }
                else
                    this.annualInterest=(double) 0.001;
                break;
            default:
                this.annualInterest=0.001;
        }
            
            int days4interest=Days.daysBetween(this.lastInterest, transDate).getDays();
            this.lastInterest = transDate;
            return helper.dailyInterest(days4interest,this.annualInterest);
        
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
    
    public String statementForAccount() {
        String s = "";
        if (Days.daysBetween(this.lastInterest, DateTime.now().toDateTime()).getDays()>=1){
            double interest=this.interestEarned()*this.balance;
            this.interestSum+=interest;
            this.deposit(interest);
            }

       //Translate to pretty account type
        switch(this.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : this.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + helper.toDollars(t.amount) + "   date:"+t.TransactionDate()+"\n";
            total += t.amount;
        }
        s += "Total " + helper.toDollars(total);
        return s;
    }
    public int getAccountType() {
        return accountType;
    }

}

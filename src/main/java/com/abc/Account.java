package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    //add a variable that holds the the last date a withdrawl is made
    private Date lastWithdrawn = new Date(0);
    private Date dateOpened;
    
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        
        /*
        either initialise lastWithdrawn to day account is opened if bonus interest starts 10 days after opened
        or set the date to earliest date if bonus should be applied immediately. 
         */
        dateOpened = new Date();
        //lastWithdrawn = new Date(0); 
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
            //set date to lastwithdrawn variable
            lastWithdrawn = new Date();
        }
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
                Date today = new Date();
                double diff = today.getTime() - lastWithdrawn.getTime();
                
                if (diff>=8.64e+8){//if diff>=10days
                    return amount * 0.05;
                        
                }else{
                    return amount * 0.001;
                        
                }
                
            default:
                return amount * 0.001;
                
        }
    }
    
    public double compoundInterest(){
        
      double principal = sumTransactions();  
      double rate = principal/interestEarned();
      double compound = 365;
      Date today = new Date();
      double years =  today.getTime() - dateOpened.getTime();
      years = years/3.154e+10;
      
      double compoundRate = Math.pow(1+rate/compound, compound*years);
      
      return compoundRate * principal;
     }
    
    public double compoundInterest(double years){
        
      double principal = sumTransactions();  
      double rate = interestEarned()/principal;
      double compound = 365;
      
      double compoundRate = Math.pow(1+rate/compound, compound*years);
      
      return compoundRate * principal;
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

package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
    
import static java.lang.Math.abs;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType; 
    private List<Transaction> transactions;
    // UPDATE: add balance, avoid having to sum up transaction every time when request
    // UPDATE: use BigDecimal or Currency over double to prevent inaccuracy regrading to currency operations 
    private double balance; 
    // TO-DO: Better accuracy over double when representing currency and operation performs
    // private Currency balance; 
    private InterestProvider interestProvider; 


    public Account(int accountType) {
        this.accountType = accountType;
        this.balance = 0;  // initial balance is set to 0
        this.interestProvider = new InterestProvider();
        this.transactions = new ArrayList<Transaction>();
    }

    // UPDATE: Check if a transcation is valied before committing to a transcation
    // UPDATE: Exceptions are now managed in a single class for ease of debugging 
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction temp = new Transaction(amount);
            if(this.initTransaction(temp))
                this.commitTransaction(temp);
            else 
                throw new IllegalArgumentException("acccount reaches upper limit");
        }
    }

    // UPDATE: Check if a transcation is valied before committing to a transcation
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            Transaction temp = new Transaction(-amount);
            if(this.initTransaction(temp))
                this.commitTransaction(temp);
            else
               throw new IllegalArgumentException("not enough balance");
        }
    }

    // UPDATE: In case of the interet rate changes made by Bank, these chanegs need to propagates to all 
    // account more effeicently. 
    // the interest rate is now hanldle by InterestProvider 
    public double interestEarned() {
        switch(this.accountType){
            case Account.SAVINGS:
                return this.interestProvider.interestEarnedSaving(this.balance);
            case Account.MAXI_SAVINGS:
                return this.interestProvider.interestEarnedMaxiSaving(this.balance);
            default:
                return this.interestProvider.interestEarnedChecking(this.balance);
        }
    }

    // REMOVED: replace by get balance, avoid calculations when there is a lot of transcations
    // public double sumTransactions() {
    //    return checkIfTransactionsExist(true);
    // }
    // private double checkIfTransactionsExist(boolean checkAll) {
    //     double amount = 0.0;
    //     for (Transaction t: transactions)
    //         amount += t.amount;
    //     return amount;
    // }

    // UPDATE: commit transcation and update balance
    private void commitTransaction(Transaction t){
        this.transactions.add(t);
        this.balance += t.amount;
    }


    // UPDATE: Additional feature, allow Customer to transfer fund within accounts
    public void transferTranscation(Account a,double amount){
        Transaction withdraw = new Transaction(-amount);
        Transaction deposit = new Transaction(amount);
        if(this.initTransaction(withdraw) && a.initTransaction(deposit)){
            this.commitTransaction(withdraw);
            a.commitTransaction(deposit);
        } else {
            throw new IllegalArgumentException("Transfer Failed");
        }
    }
    
    // UPDATE: initiate a transcation
    // Return true if the transcation is valied -> new balance > 0 
    // TO-DO: Not sure if the possible concurrent transcations need to be handled as part of the test
    // TO-DO: If so, initTransaction should lock the 'balance' in order to prevent issues caused by 
    // TO-DO  customer making transcations for a same account during the same time.  
    private boolean initTransaction(Transaction t){
        return (this.balance + t.amount) > 0;  
    }


    public int getAccountType() {
        return accountType;
    }

    // UPDATE: replace of sumTransactions() method
    public double getBalance(){
        return this.balance;
    }

    // UPDATE: return a statment for current account; 
    public String getStatement(){
        String s = "";

       //Translate to pretty account type
        switch(this.accountType){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        for (Transaction t : this.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
        }
        // UPDATE : get balance instantly 
        s += "Total " + toDollars(this.getBalance());
        return s;
    }


    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }


    // UPDATE: change interest rate to a new InteresProvider
    // NOTE: Ignore the possibility of accounts failed to update thier InterestProvider
    public void updateInterestRate(InterestProvider newInterestRate){
        this.interestProvider = newInterestRate;
    }

}

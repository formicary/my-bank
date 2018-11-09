package com.abc;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    private int ACCT;
    public List<Transaction> transactions;
    private double acctotal;

    public Account(int ACCT) {
        this.ACCT = ACCT;
        this.transactions = new ArrayList<Transaction>();
    }

    public double ACCTotal(){
        return acctotal;
    }
    
    public void deposit(double AMT) {
        if (AMT <= 0) {
            throw new IllegalArgumentException("to withdraw money ammount must be greater than Zero");
        } else {
            transactions.add(new Transaction(AMT, Transaction.DEP));
            acctotal += AMT;
            System.out.print("ammount deposited successfully");
        }
    }

    public void withdraw(double AMT) {
    if (AMT <= 0) {
        throw new IllegalArgumentException("to withdraw money ammount must be greater than Zero");
    } else {
        transactions.add(new Transaction(-AMT, Transaction.WD));
        acctotal -= AMT;
        System.out.print("ammount withdrawn successfully");
    }
}
        //funds are withdrawn from account called on in method and deposited 
        // to target account that is specified by a user.
    public void ACCTransfer(double AMT, Account ACC){
        if (AMT <= 0) {
        throw new IllegalArgumentException("to Transfer money ammount must be greater than Zero");
         } else {
        transactions.add(new Transaction(-AMT, ACC));
        ACC.transactions.add(new Transaction(AMT, this));
        
        System.out.print("Transfer successful");
       }
    }

    private static long NumberOfDaysCalculator(Date Current, Date Previous, TimeUnit TU){//used to calculate the number of days between the current date and previous date of transaction 
       long differanceCalc = Math.abs(Current.getTime() - Previous.getTime());
           long DiferanceinDays = TimeUnit.DAYS.convert(differanceCalc, TimeUnit.MILLISECONDS);
           return DiferanceinDays;
    }

    public double interestEarned() {
        double TotalAM = TotalForTransactions();
        double amountThreshold = 1000;
        double CheckingAndMaxiMinimum = 0.001; // interest rate for checking account and default interest for maxi savings if withdrawals have been made within 10 days
        double SavingACIntrest = 0.002; // set interest rate for a savings account.
        double MaxiSInterest = 0.05; //maximum interest rate for a maxi savings account if no withdrawals have been made in the past 10 days
        switch(ACCT){
            case SAVINGS:
                if (TotalAM <= amountThreshold)
                    return TotalAM * CheckingAndMaxiMinimum;
                else
                    return 1 + (TotalAM-amountThreshold) * SavingACIntrest;

            case MAXI_SAVINGS:
                int LastWithdrawal = 0;
                Date now = DateProvider.getInstance().now();
                //gets the information of the last withdrawal from the account.
                for(int MI = transactions.size()-1; MI >= 0; MI--){
                    if(transactions.get(MI).AMT< 0 ){
                        LastWithdrawal = MI;
                    }
                }
                Date lastwithdrawDate = transactions.get(LastWithdrawal).getTD();
                //checks if any withdrawls have been made within the last 10 days from the account
                if (LastWithdrawal == 0 || NumberOfDaysCalculator(lastwithdrawDate,now, TimeUnit.DAYS) > 10)
                   return TotalAM * MaxiSInterest;
                else 
                    return TotalAM * CheckingAndMaxiMinimum;
            default:
                return TotalAM * CheckingAndMaxiMinimum;
        }
    }
    //calculates the daily interest for an account by dividing the per-annum interest value by the days in a year
    public void DailyIntrestcalc(){
        double dayintrest = interestEarned()/365;
        this.acctotal += dayintrest; 
    }
    public double TotalForTransactions() {
       double Total = 0.0;
       for(Transaction t: transactions)
           Total += t.getAM();
       return Total;
    }

    public int getAccountType() {
        return ACCT;
    }

}

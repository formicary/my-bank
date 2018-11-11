package src.main.java.com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    private final int aDay = 1000*60*60*24;

    private final int accountType;
    public List<Transaction> transactions;
    private Date latestWithdrawal = new Date(0);
    private Date createdDate;

    
    public Account(int accountType) {
        if (accountType < 0 || accountType > 2) throw new IndexOutOfBoundsException("account type doesn't exist");
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.createdDate = DateProvider.getInstance().now();
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
            Transaction temp = new Transaction(-amount);
            transactions.add(temp);
            latestWithdrawal = temp.getDate();
        }
    }
    
    //to calculate accrued interest
    private double calculateInterest(double amount, double rate, int days){
        double dailyInterest = rate/365; //rate/365; //rate is annual rate
        double totalInterest = 0.0;
        double temp;
        //if (days == 0) days = 1; assume at least 1 day has passed
        for (int i = 0; i<days; i++){
            temp = amount * dailyInterest;
            amount += temp;
            /*
             * need to increase the amount because compound interest
             * increases as it does
               */
            totalInterest += temp; //sums JUST the interest
        }
        return totalInterest;
    }

    //use this version for testing (to calculate interest after fixed numbers of days)
    public double interestEarnedTestVer(int ageInDays) {
        double amount = sumTransactions();
        //no such thing as -ve interest
        if (amount <= 0) return 0.0;
        
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    //return amount * 0.001;
                    return (calculateInterest(amount, 0.001, ageInDays));
                else
                    //return 1 + (amount-1000) * 0.002;
                    return (calculateInterest(1000, 0.001, ageInDays))
                    + (calculateInterest((amount-1000), 0.002, ageInDays));
            //            case SUPER_SAVINGS:
            //                if (amount <= 4000)
            //                    return 20;
            /*case MAXI_SAVINGS: //the original MAXI_SAVINGS
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;*/
                
            case MAXI_SAVINGS: //the newer MAXI_SAVINGS 
                //has it been ten days since the last withdrawal?
                Date temp = DateProvider.getInstance().now();
                long diff = temp.getTime() - latestWithdrawal.getTime();
                int days = (int) (diff / aDay);
                if (days > 10) //was the latest withdrawal more than 10 days ago?
                    //return amount * 0.05;
                    return calculateInterest(amount, 0.05, ageInDays);
                else 
                    //return amount * 0.001;
                    return calculateInterest(amount, 0.001, ageInDays);
                
            default:
                return calculateInterest(amount, 0.001, ageInDays);
        }
    }    
    
    public double interestEarned() {
        double amount = sumTransactions();
        //no such thing as -ve interest
        if (amount <= 0) return 0.0;
        //interest calculated daily, so calculate number of days
        Date temp = DateProvider.getInstance().now();
        long accountAge = temp.getTime() - createdDate.getTime();
        int ageInDays = (int) (accountAge/aDay);
        
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    //return amount * 0.001;
                    return (calculateInterest(amount, 0.001, ageInDays));
                else
                    //return 1 + (amount-1000) * 0.002;
                    return (calculateInterest(1000, 0.001, ageInDays))
                    + (calculateInterest((amount-1000), 0.002, ageInDays));
            //            case SUPER_SAVINGS:
            //                if (amount <= 4000)
            //                    return 20;
            /*case MAXI_SAVINGS: //the original MAXI_SAVINGS
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;*/
                
            case MAXI_SAVINGS: //the newer MAXI_SAVINGS 
                //has it been ten days since the last withdrawal?
                long diff = temp.getTime() - latestWithdrawal.getTime();
                int days = (int) (diff / aDay);
                if (days > 10) //was the latest withdrawal more than 10 days ago?
                    //return amount * 0.05;
                    return calculateInterest(amount, 0.05, ageInDays);
                else 
                    //return amount * 0.001;
                    return calculateInterest(amount, 0.001, ageInDays);
                
            default:
                return calculateInterest(amount, 0.001, ageInDays);
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

//Manolis Tomadakis
//09/10/18
package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
/*Account class to facilitate the deposit and withdraw function
calculates the interest earned according to the type of account
and then sums the transactions as well as check for the existence
of a transaction
*/

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    private final int accountType;
    public List<Transaction> transactions;

//constructor using accountType field and a list of transaction
    public Account(int accountType, ArrayList<Transaction> transactions) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
// deposit method
    public void deposit(double depositAmount, Date depositDate) {
        if (depositAmount <= 0) {
            throw new IllegalArgumentException("amount to be deposited must be greater than zero");
        } else {
            this.transactions.add(new Transaction(depositAmount,Calendar.getInstance().getTime()));
        }
    }
//withdraw method
    public void withdraw(double withdrawAmount, Date withdrawDate) {
        withdrawDate = Calendar.getInstance().getTime();
        if (withdrawAmount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        this.transactions.add(new Transaction(-withdrawAmount, withdrawDate));
        
    }
}
/** Method to calculate interest on a daily basis. The numOfDays is the total
 * number of days that we would like to calculate the interest for.
 * @param numOfDays
 * @return 
 */
    public double interestEarned(int numOfDays) {
       
        double totalAmount = sumTransactions();
        int threshold = 1000;
//default interest rate used for checking accounts and maxiSavings when
//withdrawal has been made
        double defaultInterest = 0.001;
//Savings interest rate
        double savingInterest = 0.002;
//Maxi Saving interest rate if no withdrawal has beeen made in past 10 days
        double maxiSavingInterest  = 0.05;
 //Switch case to calculate the appropriate interest depending on the bank account.
        switch(accountType){
            case SAVINGS:
                if (totalAmount <= threshold)
                    return totalAmount *Math.pow((1+ defaultInterest/365),numOfDays);
                else
                    return (threshold*Math.pow(1+defaultInterest/365,numOfDays) + (totalAmount-threshold) * Math.pow(1+savingInterest/365,numOfDays)) - totalAmount;
            case MAXI_SAVINGS:
                if(checkForRecentWithdrawal(Calendar.getInstance().getTime()))
                    return (totalAmount*Math.pow(1+defaultInterest/365,numOfDays)) - totalAmount;
                else
                   return (totalAmount*Math.pow(1+maxiSavingInterest/365,numOfDays)) - totalAmount;     
            default:
                return (totalAmount *Math.pow(1 + defaultInterest/365,numOfDays)) - totalAmount;
        }
    }
//Method to sumTransactions after checking if they exist
    public double sumTransactions() {
       double sum = 0.0;
       if (this.transactions.isEmpty())
           throw new IllegalArgumentException("there are no transactions on this account");
        for(Transaction t: this.transactions){
          sum += t.getAmount();
       }
        return sum;
    }
//Method to get the type of account
    public int getAccountType() {
        return accountType;
    }
/**method to check if withdrawals within 10 days of the current date have been 
 * made. Initially checks for all negative amounts to determine when a 
 * withdrawal has been made. If a negative amount is found, the date this amount
 * was withdrawn is fetched and compared to the current time and date. After 
 * calculating the difference in days a check is made on that difference to see
 * if that difference is 10 days or more in which case a boolean value is set to 
 * true in order to update our interest rate.
 */
    public Boolean checkForRecentWithdrawal(Date d){
        boolean hasRecentWithdrawal = false;
        for(Transaction t: transactions){
            if(t.getAmount() <= 0){
               long diff = d.getTime() - t.getDate().getTime();
               double diffInDays = (double)(diff/(24*1000*60*60));
               if (diffInDays <= 10)
                   hasRecentWithdrawal = true;
               else
                   hasRecentWithdrawal = false;     
            }
        }
        return hasRecentWithdrawal;
    }
}

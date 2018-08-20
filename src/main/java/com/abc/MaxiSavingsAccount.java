
package com.abc;

import java.util.Date;

/**
 *
 * @author Bayzid
 */
public class MaxiSavingsAccount extends Account{

    public static final double interestRateDefault = 0.001;
    public static final double interestRateNoWithdrawals = 0.005;
    public static double limit = 1000;
    public static final long miliseconds = 86400000;
    
    public MaxiSavingsAccount(){
        super(Account.MAXI_SAVINGS);
    }
    
    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 0) {
            return 0;
        }
        
        Date dateNow = DateProvider.getInstance().now();
        Date withdrawalDate = new Date();
           
        for (int i=getTransactions().size()-1; i>=0; i--){
            Transaction withdrawal = getTransactions().get(i);
            //loop backwards for last negative amount
            if (withdrawal.getAmount() < 0) {
                withdrawalDate = withdrawal.getTransactionDate();
                break;
            }
        }
        
        long days = (withdrawalDate.getTime() - dateNow.getTime())/miliseconds;
        if(days > 10){
            return amount * interestRateNoWithdrawals;
        } else {
            return amount * interestRateDefault;
        }
    }
   
}

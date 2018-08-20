
package com.abc;

/**
 *
 * @author Bayzid
 */
public class SavingsAccount extends Account {
    
    public static final double interestRateBefore = 0.001;
    public static final double interestRateAfter = 0.002;
    public static double limit = 1000;
    
    public SavingsAccount(){
        super(Account.SAVINGS);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 0) {
            return 0;
        }
        else if (amount <= limit) {
            return amount * interestRateBefore;
        } else {
            return (limit * interestRateBefore) + ((amount - limit) * interestRateAfter);
        }
    }
    
}

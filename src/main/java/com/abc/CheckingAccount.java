
package com.abc;

/**
 *
 * @author Bayzid
 */
public class CheckingAccount extends Account{

    public static final double interestRate = 0.001;
    
    public CheckingAccount(){
        super(Account.CHECKING);
    }
    
    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 0) {
            return 0;
        } else {
            return amount * interestRate;
        }
    }
    
}

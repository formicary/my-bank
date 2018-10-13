package com.abc;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class MaxiSavingAccount extends AbstractAccount {

	/**
     * Constructor 
     * @param customer The customer this account belongs to
     * @param balance The starting balance
     */
	public MaxiSavingAccount(Customer customer, double balance) {
		super(customer, balance);
		setType(AccountEnum.MAXISAVINGACCOUNT);
	}

	/*
	 * (non-Javadoc)
	 * @see com.abc.Account#interestEarned()
	 */
	public double interestEarned() {
		double rate = 0.05;
		
		if(this.hasTransactions()) {
			if(DateProvider.getInstance().getDifference(getLatestTransaction().getDate()) > 864000 & getLatestTransaction().getType().equals("Withdraw")) {
				rate = 0.001;
			}
		}
		
		double interest = 0;
		if (balance <= 1000) {
			interest = balance * rate;
			balance += interest;
            return interest;
		}
		
        if (balance <= 2000) {
        	interest = 20 + (balance-1000) * rate;
        	balance += interest;
            return interest;
        }
	    interest = 70 + (balance-2000) * rate; 
        return interest;
    }
}

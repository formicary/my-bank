/**
 * 
 */
package com.abc;

/**
 * @author Harry
 *
 */
public class MaxiSavingsAccount extends Account {

	private final double normIR = 0.001;
    private final double specialIR = 0.05;
	
	/**
	 * @param accountType
	 * @param openingBalance
	 */
	public MaxiSavingsAccount(double openingBalance) {
		super(MAXI_SAVINGS, openingBalance);
	}

	/**
	 * @param accountType
	 */
	public MaxiSavingsAccount() {
		super(MAXI_SAVINGS, 0.0f);
	}

	/**
	 * checking if last transaction was less than 10 days ago
	 */
	@Override
	public double interestEarned() {
			
		Transaction lastTrans = this.getMostRecentTransaction();		
		double rate = normIR;
		
		if (lastTrans != null && lastTrans.timeSinceTransaction() < -10) {
			rate = specialIR;
		}	
				
		return this.getBalance() * rate;
		
	}

}

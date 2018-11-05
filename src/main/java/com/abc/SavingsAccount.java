package com.abc;

public class SavingsAccount extends Account {

    private int tierOne;

    public SavingsAccount () {
        super();
        tierOne = 1000;
    }

    /**
     * Get the minimum amount for the first tier where interest can rise.
     * @return The amount for the first tier.
     */

    protected int getTierOne () {
        return tierOne;
    }

    /**
     * Calculate the annual interest earned from a SavingsAccount.
     * interest at 0.1% for the first $1000 then 0.2% after.
     * @return The balance after annual interest is calculated.
     */

    @Override
    public double interestEarned () {

        if(getBalance() <= tierOne) {
            return getBalance() * getInterest();
        }
        else {
            setInterest(0.002);
            return 1 + ((getBalance() - tierOne) * getInterest());
        }

    }

    /**
     * String representation to be displayed on statements.
     */
    @Override
    public String toString() {
        return "Savings Account\n";
    }
}

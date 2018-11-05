package com.abc;

import java.util.Calendar;

public class MaxiSavingsAccount extends SavingsAccount {

    private int tierTwo;

    public MaxiSavingsAccount () {
        super();
        setInterest(0.02);
       // setInterest(0.05);  set initial interest if implementing additional tasks and use daily interest.
        tierTwo = 2000;
    }

    /**
     * Calculate the annual interest earned from a Maxi-SavingsAccount.
     * interest at 0.1% for the first $1000 then 0.2% after.
     * @return The balance after annual interest is calculated.
     */

    @Override
    public double interestEarned () {

        if(getBalance() <= getTierOne()) {
            return getBalance() * getInterest();
        }
        else if ((getBalance() > getTierOne()) && getBalance() <= tierTwo) {
            setInterest(0.05);
            return 20 + ((getBalance() - getTierOne()) * getInterest());
        }
        else {
            setInterest(0.1);
            return 70 + ((getBalance() - tierTwo) * getInterest());
        }

    }


    /** FUNCTION YET TO BE INTEGRATED IN TO THIS SYSTEM.
     * Calculate the interest earned from a Maxi-SavingsAccount.
     * To be called daily so that interest may accrue.
     * Interest at 5%. At 0.1% if withdrawals take place. Reverts back to 5% after 10 days clear of no withdrawals.
     * percentage per day at 5% = 0.05 / 365 = 0.00013698630137 (multiply by 100 for percentage)
     * percentage per day at 0.1% = 0.001 / 365 = 0.000002739726027 (multiply by 100 for percentage)
     * @return The balance post daily compound interest calculation.
     */

    public double dailyInerest () {

        Calendar tenDays = Calendar.getInstance();
        tenDays.add(Calendar.DATE, -10);
        boolean lostInterest = false;

        for(Transaction transaction: getTransactions()) {
            if(transaction.getTransactionDate().after(tenDays.getTime()) && transaction.getAmount() < 0) {
                lostInterest = true;
            }
        }

        if(lostInterest == true) {

            setInterest(0.001);
            // if balance was $100 => new balance with daily interest = $100.013698630137 ($100.01 rounded) .
            double newBalance = (1 + (getInterest() / 365)) * getBalance();
            // set new balance for next daily interest calculation (compound).
            setBalance(newBalance);
            return newBalance;
        }
        else {
            setInterest(0.05);
            double newBalance = (1 + (getInterest() / 365)) * getBalance();
            setBalance(newBalance);
            return newBalance;
        }
    }


    /**
     * String representation to be displayed on statements.
     */
    @Override
    public String toString() {
        return "Maxi-Savings Account\n";
    }

}

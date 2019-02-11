package com.abc.accounts;

import com.abc.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A class to represent one the account options available to the customer.
 * It inherits from the Account class.
 */
public class MaxiSavingsAccount extends Account {

    /**
     * Constructor for the class
     *
     * @param accountID A unique ID for the account.
     */
    public MaxiSavingsAccount(int accountID) {
        super(AccountType.MaxiSavings, accountID);
    }

    /**
     * Method to check is check when the last withdrawal relative to given date.
     *
     * @return true if it has otherwise return false.
     */
    public int daysSinceLastWithdrawal(Date transactionDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Integer> withdrawalDifference = new ArrayList<>();

        for (Transaction transaction : getTransactions()) {
            if (transaction.getAmount() < 0) {
                try {
                    Date thisTransaction = dateFormatter.parse(dateFormatter.format(transaction.getTransactionDate()));
                    if (transactionDate.after(thisTransaction)) {
                        withdrawalDifference.add(getDateDiff(thisTransaction, transactionDate));
                    }
                } catch (ParseException e) {
                    System.out.println(transaction.getTransactionDate().toString());
                }
            }
        }

        return withdrawalDifference.size() > 0 ? Collections.min(withdrawalDifference) : 0;
    }

    /**
     * A method to define the interest earned on this account type.
     *
     * @param balance      The amount the interest is earned on.
     * @param numberOfDays The number of days interest being earned.
     * @return the interest earned.
     */


    @Override
    public double interestEarned(Double balance, int numberOfDays, Date from) {
        /*
        The specification is as follows:
        Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals
        in the past 10 days otherwise 0.1%
        Because interest is compounded daily, this means you will earn an interest of 0.1%
        for the next 10 days every time you make a withdrawal.
        After 10 days you will start earning 5% again.
        */

        int daysSinceLastWithdrawal = daysSinceLastWithdrawal(from);
        int daysInterest001 = daysSinceLastWithdrawal > 0 ? 10 - (daysSinceLastWithdrawal - numberOfDays) : 0;
        int daysInterest005 = numberOfDays - daysInterest001;
        double interest = compoundFormula(balance, 0.001, daysInterest001);

        if (daysInterest005 > 0) {
            interest += compoundFormula(balance + interest, 0.05, daysInterest005);
        }

        return interest;

    }

}

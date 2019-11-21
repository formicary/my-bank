package com.abc;

import com.abc.utils.DateConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class MaxiSavingsAccount extends Account {

    MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    @Override
    double calcInterest() {
        long numDays = daysSinceInterestApplied();
        double oldInterest = getEarnedInterest();

        Transaction withdrawal = lastWithdrawal();
        Date now = DateProvider.getInstance().now();


        // By design, customer's are deemed ineligible for the 5% interest rate
        // on accounts younger than 10 days old.
        Date withdrawalDate = (withdrawal == null) ? now : withdrawal.getTransactionDate();
        if (now.getTime() - getCreationDate().getTime() >= DateConstants.TEN_DAYS) withdrawalDate = getCreationDate();

        // Checking if 10 days has passed since the last withdrawal
        double interestRate = (now.getTime() - withdrawalDate.getTime() >= DateConstants.TEN_DAYS)
            ? 0.05 / 365
            : 0.001 / 365;
        double interest = getBalance() * interestRate * numDays;

        applyInterest(interest);

        return getEarnedInterest() - oldInterest;
    }

    @Override
    String genStatement() {
        return genStatement("Maxi-Savings Account");
    }

    /**
     * Finds the last withdrawal made from this account.
     * @return the most recent transaction which was a withdrawal
     */
    private Transaction lastWithdrawal() {
        List<Transaction> reversedTransactions = genReversedTransactions();

        for (Transaction transaction : reversedTransactions) {
            if (transaction.getAmount() < 0) return transaction;
        }

        return null;
    }

    /**
     * @return a list of all transactions made by the account in reversed order
     */
    private List<Transaction> genReversedTransactions() {
        List<Transaction> reversedList = new ArrayList<Transaction>(getTransactions());
        Collections.reverse(reversedList);

        return reversedList;
    }
}

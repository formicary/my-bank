package com.abc;

/* -- Maxi Savings Account Class --
    Represents a savings account. 
        - CheckingAccount(): Creates and returns a savings account.
        - interestEarned(): Returns a double of the interest the account
        will earn. 
*/
public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount() {
        super(account_type.MAXI_SAVINGS);
    }

    public double interestEarned() {
        double mostRecentWithdrawal = 0;
        // Look through transactions to find the most recent withdrawal. 
        for (Transaction transaction : this.transactions) {
            // No need to consider deposits. 
            if (transaction.amount < 0) {
                double dateCreatedCurrentTransaction = transaction.
                                    date_transaction_created().getTime();
                if ((mostRecentWithdrawal == 0) ||
                    (dateCreatedCurrentTransaction > mostRecentWithdrawal)) {
                        mostRecentWithdrawal = dateCreatedCurrentTransaction;
                    }
            }
        }

        // Get date right now in MS. 
        double rightNow = new DateProvider().now().getTime();
        double msInADay = 1000 * 60 * 60 * 24;

        // Get 10 day cut off by subtacting 10 days worth of milliseconds from
        // right now. 
        double tenDayCutoff = rightNow - (10 * msInADay);

        // Subtract tenDayCutOff from most recently seen deposit date. If the 
        // answer is negative, then that means the deposite date occured before
        // the cut off, and the 5% interest can be applied. 
        double amount = sumTransactions();
        if (0 >= (mostRecentWithdrawal - tenDayCutoff)) {
            return amount * 0.05;
        } else {
            return amount * 0.001;
        }
    }

    /*public double interestEarned() { - Original implementation
        double amount = sumTransactions();
        if (amount <= 1000) {
            return amount * 0.02;
        } else if (amount <= 2000) {
            return 20 + (amount-1000) * 0.05;
        } else {
            return 70 + (amount-2000) * 0.1;
        }
    }*/
}
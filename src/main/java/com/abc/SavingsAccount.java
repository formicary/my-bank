package com.abc;


/* -- Savings Account Class --
    Represents a savings account. 
        - CheckingAccount(): Creates and returns a savings account.
        - interestEarned(): Returns a double of the interest the account
        will earn. 
*/
public class SavingsAccount extends Account {
    public SavingsAccount() {
        super(account_type.SAVINGS);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * 0.001;
        else {
            return 1 + (amount - 1000) * 0.002;
        }
    }
}
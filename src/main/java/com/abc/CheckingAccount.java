package com.abc;


/* -- Checking Account Class --
    Represents a checking account. 
        - CheckingAccount(): Creates and returns a checking account.
        - interestEarned(): Returns a double of the interest the account
        will earn. 
*/
public class CheckingAccount extends Account {
    public CheckingAccount() {
        super(account_type.CHECKING);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        return amount * 0.001;
    }
}
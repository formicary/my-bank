package com.abc;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

    //Enumeration class local to Account to specify account type.

    public enum Type{
      CHECKING("Checking Account"), SAVINGS("Savings Account"), MAXI_SAVINGS("Maxi Savings Account");

        private final String value;

        Type(String value){
            this.value = value;
        }

        @Override
        public String toString(){
            return value;
        }
    }

    private Type accountType;
    public List<Transaction> transactions;

    public Account(Type accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }


    //Simple stub methods for adding/subtracting value from an account.
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /*  Calculates money paid to account per day.
    *   Leap years not taken into consideration.
    */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                return (amount <= 1000 ? amount * (0.001/365) : 1 + (amount-1000) * (0.002/365));
            case MAXI_SAVINGS:
                boolean check = false;
                Date right_now = DateProvider.getInstance().now();
                for (Transaction trans:transactions){
                    if(trans.amount < 0){
                        check = check || withinTenDays(right_now, trans.timestamp());
                    }
                }
                return (check ? amount * (0.001/365) : amount * (0.05/365));
            default:
                return amount * (0.001/365);
        }
    }

    /*
        One thing that I am not sure about in regards to the "Additional features" section was whether or not the
        interest should actually be credited into the account daily or not. If not, then below is a modified loop to
        interestEarned that will accumulate interest in the account over a number of days.

        Of course, this is left commented out because I assume that the final bank application as a whole system will
        have a daily update function that calculates the interest earned for each account and then pays that in at
        the end-of-day.

     public double interestEarned() {

        double amount = sumTransactions();
        double result = 0;

        Date right_now = DateProvider.getInstance().now();

        //While not defined, getLastTransaction is a simple "minimum of a list" function.
        Date last_transaction = getLastTransaction(transactions);

        int days_since = (right_now.getTime() - last_transaction.getTime())/(26*60*60*1000);

        double interest = 0;
        switch(accountType){
            case SAVINGS:
                for(int i = 0; i < days_since; i++){
                    interest = (amount <= 1000 ? amount * (0.001/365) : 1 + (amount-1000) * (0.002/365));
                    result, amount += interest;
                }
                return result;
            case MAXI_SAVINGS:
                // Interestingly, the use of last_transaction allows for the removal of withinTenDays.
                interest = (days_since <= 10 ? (0.001/365) : (0.05/365));
                break;
            default:
                interest = (0.001/365);
        }

        I hope I can talk a bit here about what I did above and why I'm proud of it; because the interest needs to be
        paid in every day, the first case is in savings, because the interest paid may affect the level of interest
        later on; thus it has its own loop which updates interest regularly. The other two account types have a fixed
        interest rate, and therefore this rate can be calculated statically based on the type of account. This can then
        be applied in a loop later, as the Savings account will have returned before reaching this.

        for(int i = 0; i < days_since; i++){
            result, amount += amount * interest;
        }
        return result;
     }

     */

    //Helper method to ensure correct behaviour for Maxi Savings accounts.
    public boolean withinTenDays(Date current, Date then){
      return ((current.getTime() - then.getTime()) / (24*60*60*1000)) <= 10;
    }

    //Helper method used to calculate total money before interest.
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    // Stub get method
    public Type getAccountType() {
        return accountType;
    }

}

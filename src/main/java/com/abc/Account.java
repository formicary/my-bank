package main.java.com.abc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.StrictMath.round;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    //Sets the current Account type
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    //Deposit an amount of money into the Account
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    //Withdraw an amount of money from the Account
    public void withdraw(double amount) {
        double accountValue = sumTransactions();
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if(accountValue - amount < 0) {
                throw new IllegalArgumentException("account does not contain enough money");
            } else {
                transactions.add(new Transaction(-amount));
            }
        }
    }

    //Calculate the amount of interest earned
    public double interestEarned(double amount, double years) {
        DecimalFormat df = new DecimalFormat("#.00");
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return roundDouble(getInterestAmount(amount,0.1, years), 2);
                else
                    return roundDouble((interestEarned(1000, years)) + (getInterestAmount(amount-1000, 0.2, years)), 2);
            case MAXI_SAVINGS:
                int savingsInterest = 2;
                if(lastTransactionDate() >= 10)
                    savingsInterest = 5;
                if (amount <= 1000)
                    return roundDouble(getInterestAmount(amount, savingsInterest, years), 2);
                if (amount <= 2000)
                    return roundDouble((interestEarned(1000, years)) + (getInterestAmount(amount-1000, 5, years)), 2);
                return roundDouble((interestEarned(2000, years)) + (getInterestAmount(amount-2000, 10, years)), 2);
            default:
                return roundDouble( getInterestAmount(amount,0.1, years), 2);
        }
    }

    //Sums together all Account transactions
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    //Checks if Account has at least one transaction
    private boolean checkIfTransactionsExist() {
        if(transactions.size() >= 1){
            return true;
        }
        return false;
    }

    public int getAccountType() {
        return accountType;
    }

    public double getInterestAmount(double amount, double interest, double years){
        double compoundInterest = Math.pow((1 + (interest / 100) / 365), 365 * years);
        return (amount * compoundInterest) - amount;
    }

    public int lastTransactionDate(){
        if(checkIfTransactionsExist()) {
            long millisecondDifference = Math.abs(DateProvider.getInstance().now().getTime() - transactions.get(transactions.size()-1).getDate().getTime());
            int daysDifference = (int)Math.floor(TimeUnit.DAYS.convert(millisecondDifference, TimeUnit.MILLISECONDS));
            return daysDifference;
        }
        return 0;
    }

    public double roundDouble(double amount, int dp){
        return Math.round(amount*Math.pow(10,dp))/Math.pow(10,dp);
    }

}

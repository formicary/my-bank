package com.abc;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AccountUtility {
    public static void isValidAmount(double amount) {
        if (amount <= 0) {
            throw new Error("Amount has to be greater than zero");
        }
    }

    public static void isBalanceEnough(double amount, Account account) {
        if(account.getBalance() < amount) {
            throw new Error( "You don't have sufficient funds in your account.");
        }
    }

    public static double daysSinceFirstTransaction(Account account) {
        System.out.println("In days since first transaction");

        Date firstTransactionDate = account.transactions.get(0).getTransactionDate();
        System.out.println(firstTransactionDate);
        Date lastTransactionDate = account.transactions.get(account.transactions.size()-1).getTransactionDate();
        System.out.println(lastTransactionDate);

        long diffInMilliseconds = Math.abs(lastTransactionDate.getTime() - firstTransactionDate.getTime());
        // Convert milliseconds to days
        double diffInDays = Math.floor(TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS));
        return diffInDays;
    }
}

package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private double accountTotal;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountTotal = 0.0;
    }

    public int getAccountType() {
        return accountType;
    }

    public double getAccountTotal() {
        return accountTotal;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountTotal += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            accountTotal -= amount;
        }
    }

    public double interestEarned() {
        double amount = getAccountTotal();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                int lastWithdrawalIndex = 0;
                for(int t = transactions.size()-1; t >= 0; t--){
                    if (transactions.get(t).amount < 0){ // if transaction is a withdrawal
                        lastWithdrawalIndex = t;
                    }
                }
                Date now = DateProvider.getInstance().now();
                Date lastWithdrawal = transactions.get(lastWithdrawalIndex).getTransactionDate();
                // if there have been no withdrawals or it has been more than 10 days since last withdrawal
                if (lastWithdrawalIndex == 0 || getDaysSinceLastTransaction(lastWithdrawal, now, TimeUnit.DAYS) > 10)
                    return amount * 0.05;
                else
                    return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public void creditDailyInterestRate(){
        double dailyInterest = interestEarned()/365; // divide per-annum interest by 365 to get daily interest
        this.accountTotal += dailyInterest;
    }

    public static long getDaysSinceLastTransaction(Date lastTrans, Date now, TimeUnit timeUnit){
        long timeDiff = now.getTime() - lastTrans.getTime();
        return timeUnit.convert(timeDiff,TimeUnit.MILLISECONDS);
    }

}

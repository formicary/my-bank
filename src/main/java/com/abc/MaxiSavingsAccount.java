package com.abc;

public class MaxiSavingsAccount extends Account {

    private Transaction getlastWithdrawal() {
        Transaction latestWithdrawal = null;
        for (int i = super.transactions.size() - 1; i >= 0; i--) {
            if (super.transactions.get(i).amount < 0) {
                latestWithdrawal = super.transactions.get(i);
                break;
            }
        }
        return latestWithdrawal;
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        double interest;
        if(getlastWithdrawal() != null){
            if(getlastWithdrawal().transactionAge() <= 10) {
                interest = amount * (0.1/365);
                this.deposit(interest);
                return interest;
            }
        }
        interest = amount * (5.0/365);
        this.deposit(interest);
        return interest;
    }

    @Override
    public String getAccountType() {
        return "Maxi Savings Account\n";
    }
}

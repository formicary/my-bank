package com.abc.accounts;

import com.abc.util.DateProvider;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @project MyBank
 */
public class MaxiSavings extends Account {

    private boolean hadWithdrawal = false;

    public MaxiSavings(){
        super();
        interestRate = 0.05;
        accrueRate = interestRate/365;
    }

    @Override
    protected void compoundInterest() {
        balance += balance * interestRate;
    }

    @Override
    protected void accrueInterest() {
        
        boolean hadWithdrawal = hadWithdrawalInPast(10);
        
        if(hadWithdrawal == this.hadWithdrawal){
            interestRate += accrueRate;
        }else{
            this.hadWithdrawal = hadWithdrawal;
            interestRate = hadWithdrawal ? 0.001 : 0.05;
            accrueRate = interestRate/365;
        }
    }

    private boolean hadWithdrawalInPast(int numbOfDays) {
        LocalDateTime today = DateProvider.getInstance().now();
        return transactions.stream()
                .anyMatch(t -> t.getTransactionType() == 0 && DAYS.between(t.getTransactionDate(), today)<=numbOfDays);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000) return amount * 0.02;
        if (amount <= 2000) return 20 + (amount-1000) * 0.05;
        return 70 + (amount-2000) * 0.1;
    }

    @Override
    public String toString() {
        return "Maxi Savings Account";
    }
}

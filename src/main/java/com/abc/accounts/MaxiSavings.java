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
        init();
    }

    public MaxiSavings(LocalDateTime date){
        super(date);
        init();
    }

    private void init(){
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
    public String toString() {
        return "Maxi Savings Account";
    }
}

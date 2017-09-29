package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;

public class MaxiSavingsAccount extends Account {
    private double ifNoWithdrawalsRate = 0.05;

    public MaxiSavingsAccount(){
        this.transactions = new ArrayList<Transaction>();
        totalAmount = 0;
        defaultInterestRate = 0.001;
    }

    public double interestEarned() {
        return interestEarned(totalAmount, DateProvider.getInstance().now().toLocalDate());
    }

    protected double interestEarned(double amount, LocalDate date) {
        if (noWithdrawPastDays(date,10)) {
            return amount * ifNoWithdrawalsRate;
        } else {
            return amount * defaultInterestRate;
        }
    }

    private boolean noWithdrawPastDays(LocalDate toDate, int numberOfDay) {
        for (Transaction t: transactions) {

            LocalDate dateOfTransaction = t.getTransactionDate().toLocalDate();

            if(t.getAmount() < 0 && !t.getAnotherAccount().isPresent()
                && dateOfTransaction.isAfter(toDate.minusDays(numberOfDay))
                && dateOfTransaction.isBefore(toDate.plusDays(1))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "Maxi Savings Account\n";
    }
}

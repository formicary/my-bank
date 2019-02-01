package com.abc.account;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class MaxiSavingsAccount extends Account {

    private boolean higherInterestEligible = true;

    public void withdraw(double amount) {
        super.withdraw(amount);
        higherInterestEligible = false;
    }

    public double interestEarned() {
        if (higherInterestEligible) {
            higherInterestEligible = !withdrawnInTenDays();
        }
        double amount = balance();
        if (amount == 0.0) {
            return 0.0;
        }
        return ( Math.round(interestEarnedAfter(amount, 365, getRate(amount)) * 100.0) / 100.0 ) - amount;
    }

    protected double getRate(double balance) {
        return higherInterestEligible ? 0.05 : 0.001;
    }

    private boolean withdrawnInTenDays() {
        LocalDate lastWithdrawalDate = transactions.get(transactions.size()-1).getDate();
        return LocalDate.now().minusDays(10).isBefore(lastWithdrawalDate);
    }

    public String accountStatement() {
        String stmt = "Maxi Savings Account\n";
        //Display transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            stmt += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Transaction.toDollars(t.amount) + "\n";
            total += t.amount;
        }
        stmt += "Total " + Transaction.toDollars(total);

        return stmt;
    }

    public Account.AccountType getAccountType() {
        return Account.AccountType.MAXI_SAVINGS;
    }
}

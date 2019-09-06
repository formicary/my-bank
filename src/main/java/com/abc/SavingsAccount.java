package com.abc;

import java.util.Date;

public class SavingsAccount extends Account {

    public SavingsAccount() {
        super(SAVINGS);
    }

    public double interestEarned() {
        double startAmount = 0;
        double interest = 0;
        double totalInterest = 0;
        double firstRate = 0.001;
        double secondRate = 0.002;

        Date startDate = transactions.get(0).transactionDate;

        for (int i = 1; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            int days = t.daysToDate(startDate);

            for (int j = 0; j < days; j++) {
                if (startAmount <= 1000)
                    interest = startAmount * firstRate;
                else
                    interest = 1 + (startAmount - 1000) * secondRate;

                totalInterest += interest;
                startAmount += interest;
            }
            startAmount += t.amount;
            startDate = t.transactionDate;
        }

        int days = DateProvider.getInstance().daysToNow(startDate);

        for (int j = 0; j < days; j++) {
            if (startAmount <= 1000)
                interest = startAmount * firstRate;
            else
                interest = 1 + (startAmount - 1000) * secondRate;

            totalInterest += interest;
            startAmount += interest;
        }
        
        return totalInterest;
    }

}
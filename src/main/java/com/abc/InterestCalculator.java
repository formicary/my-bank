/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import com.abc.accounts.Account.AccountType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author comet
 */
public class InterestCalculator {

    List<Transaction> transactions;
    AccountType accountType;

    public InterestCalculator(List<Transaction> transactions, AccountType accountType) {
        this.transactions = transactions;
        //All transactions should already be in chronological order, this confirms it
        Collections.sort(this.transactions);
        this.accountType = accountType;
    }

 
    public double calcCompoundInterest() throws ParseException {
        double accountTotal = 0.0;
        int daysSinceLastWithdrawal = 0;

        for (int i = 0; i < transactions.size() - 1; i++) {
            Transaction currentT = transactions.get(i);
            Date nextDate = transactions.get(i + 1).getDate();
            
            if (currentT.getAmount() < 0) {
                daysSinceLastWithdrawal = 0;
            }

            accountTotal = calculateTransactionPeriod(accountTotal, currentT, nextDate, daysSinceLastWithdrawal);
            daysSinceLastWithdrawal += DateUtils.differenceInDays(currentT.getDate(), nextDate);;
        }

        Transaction lastT = transactions.get(transactions.size() - 1);
        if (lastT.getAmount() < 0) {
            daysSinceLastWithdrawal = 0;
        }

        //Test code for DateProvider.now() injection
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String testDateInString = "30-01-2000 00:00:00";
        Date testDate = sdf.parse(testDateInString);
        accountTotal = calculateTransactionPeriod(accountTotal, lastT, testDate, daysSinceLastWithdrawal);
        
        //accountTotal = calculateTransactionPeriod(accountTotal, lastT, DateUtils.now(), daysSinceLastWithdrawal);
        return accountTotal - sumTransactions();
    }

    private double calculateTransactionPeriod(double accountTotal, Transaction transaction, Date nextTransactionDate, int daysSinceLastWithdrawal) {
        int daysBeforeNextTransaction = DateUtils.differenceInDays(transaction.getDate(), nextTransactionDate);
        accountTotal += transaction.getAmount();

        return calcPeriodInterest(accountTotal, daysBeforeNextTransaction, daysSinceLastWithdrawal);
    }

    public double calcPeriodInterest(double amount, int days, int daysSinceLastWithdrawal) {
        for (int i = 0; i < days; i++) {
            amount = calcDaysInterest(amount, daysSinceLastWithdrawal);
            daysSinceLastWithdrawal++;
        }
        return amount;
    }

    private double calcDaysInterest(double amount, int daysSinceLastWithdrawal) {
        switch (accountType) {
            case CHECKING:
                return amount * (1 + calculateDailyInterestRate(0.1));
            case SAVINGS:
                if (amount <= 1000.0) {
                    return amount * (1 + calculateDailyInterestRate(0.1));
                } else {
                    return ((amount - 1000) * (1 + calculateDailyInterestRate(0.2)) + 1000 * (1 + calculateDailyInterestRate(0.1)));
                }
            case MAXI_SAVINGS:
                if (daysSinceLastWithdrawal < 10) {
                    return amount * (1 + calculateDailyInterestRate(0.1));
                } else {
                    return amount * (1 + calculateDailyInterestRate(5));
                }
        }
        return 0.0;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    private double calculateDailyInterestRate(double yearlyInterestRate) {
        return (yearlyInterestRate / 365) / 100;
    }

}

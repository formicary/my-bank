package com.accenture.mybank.accounts;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.accenture.mybank.Transaction;
import com.accenture.mybank.utils.CommonUtil;
import com.accenture.mybank.utils.DateProvider;
/**
 * @author anusha.a.avuthu
 *
 */


public class MaxiSavingsAccount extends Account {
    //interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%

    public MaxiSavingsAccount() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public double calculateInterest(double amount, Date date) {
        double rate;

        if(noWithdrawalsInTenDays(date)){
            rate = CommonUtil.maxiSavingsAccountNoWithdrawalsInterestRate/CommonUtil.noOfDays;
        }
        else{
            rate = CommonUtil.maxiSavingsAccountInterestRate/CommonUtil.noOfDays;
        }
        return rate;
    }

    private boolean noWithdrawalsInTenDays(Date currentDay){
        DateProvider dateProvider = new DateProvider();
        double difference;

        for (Transaction transaction: this.transactions) {
            difference = dateProvider.calculateDifferenceInDays(transaction.getTransactionDate(), currentDay, Locale.getDefault());

            if(difference >= 0 && difference <= 10){
                return false;
            }
        }

        return true;
    }

    @Override
    public String getType() {
        return CommonUtil.maxiSavingsAccountType;
    }
}
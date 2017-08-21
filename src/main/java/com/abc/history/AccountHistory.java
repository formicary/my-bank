package com.abc.history;

import com.abc.rates.Interest;
import com.abc.rates.InterestPayment;
import com.abc.transactions.AbstractTransaction;
import com.abc.utilities.DateProvider;
import com.abc.utilities.Days;

import java.util.Date;

/**
 * Created by vahizan on 18/08/2017.
 */
public class AccountHistory{
    InterestHistory interestHistory;
    TransactionHistory transactionHistory;
    public AccountHistory(){
        interestHistory= new InterestHistory();
        transactionHistory=new TransactionHistory();
    }
    public void addInterestPayment(InterestPayment interest){
        interestHistory.addInterest(interest);
    }
    public void addTransaction(AbstractTransaction transaction){
        transactionHistory.addTransaction(transaction);
    }
    public boolean withdrawalInPeriod(Days numberOfDays){
        Date current = new Date();
        Date lastWithdrawal = transactionHistory.lastWithdrawal();
        int days = DateProvider.getInstance().daysBetween(lastWithdrawal,current);
        return (days<numberOfDays.value());
    }
    public float totalInterestPaid(){
        return interestHistory.totalInterest();
    }
    public float totalInterestInPeriod(Date dateAfter, Date dateBefore){
        return interestHistory.totalInterestInPeriod(dateAfter, dateBefore);
    }
    public String statement(){
        return transactionHistory.fullStatement();
    }
    public String statementInPeriod(Date dateAfter,Date dateBefore){
        return transactionHistory.periodStatement(dateAfter,dateBefore);
    }
}

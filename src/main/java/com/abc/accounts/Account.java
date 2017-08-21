package com.abc.accounts;

import com.abc.constants.AccountConstantsNew;
import com.abc.constants.TransactionConstantsNew;
import com.abc.history.AccountHistory;
import com.abc.rates.Interest;
import com.abc.rates.InterestPayment;
import com.abc.transactions.*;
import com.abc.utilities.Days;
import com.abc.utilities.Money;

/**
 * Created by vahizan on 07/08/2017.
 */

public abstract class Account {
    private Money total;
    private AccountHistory accountHistory;
    public Account(){
        total=new Money(0);
        accountHistory=new AccountHistory();
    }
    public void withdrawMoney(Money amount){
        AbstractTransaction withdraw= TransactionCreator.createTransaction(TransactionConstantsNew.WITHDRAW, amount);
        updateTransactions(withdraw);
        total= withdraw.doTransaction(this);
    }
    public void depositMoney(Money amount){
        AbstractTransaction deposit= TransactionCreator.createTransaction(TransactionConstantsNew.DEPOSIT, amount);
        updateTransactions(deposit);
        total= deposit.doTransaction(this);
    }
    public void depositInterest(Interest interest){
        Money interestAmount=interest.calculateDailyInterest(total());
        AbstractTransaction depositInterest= TransactionCreator.createTransaction(TransactionConstantsNew.PAY_INTEREST,interestAmount);
        updateTransactions(depositInterest);
        updateInterests(new InterestPayment(interestAmount));
        total= depositInterest.doTransaction(this);
    }
    public boolean withdrawalInPeriod(Days numberOfDays){
        return accountHistory.withdrawalInPeriod(numberOfDays);
    }
    private void updateTransactions(AbstractTransaction transaction){
       accountHistory.addTransaction(transaction);
    }
    private void updateInterests(InterestPayment interest){
        accountHistory.addInterestPayment(interest);
    }
    public float totalInterest(){
        return accountHistory.totalInterestPaid();
    }
    public String bankStatement(){
        return accountHistory.statement();
    }
    public Money total(){
        return this.total;
    }
    private AccountHistory accountHistory(){
        return accountHistory;
    }
    public abstract AccountConstantsNew accountType();
}

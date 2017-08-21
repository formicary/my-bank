package com.abc.transactions;

import com.abc.accounts.Account;
import com.abc.utilities.DateProvider;
import com.abc.utilities.Money;

/**
 * Created by vahizan on 07/08/2017.
 */
public class Withdraw extends AbstractTransaction {
    private Money amount;
    private String transactionDate;

    public Withdraw(Money amount){
        this.amount = amount;
        //set deposit date
        transactionDate= DateProvider.getInstance().generateDate();
    }
    public Money doTransaction(Account account){
       return subtract(account.total(), amount);
    }
    public String transactionInformation(){
        StringBuilder sb = new StringBuilder();
        sb.append("Withdrawal: -")
                .append(money().amount())
                .append("\n")
                .append("Date: ")
                .append(stringDate());
        return sb.toString();
    }
    @Override
    public String stringDate() {
        return transactionDate;
    }
    @Override
    public Money money() {
        return amount;
    }
    private Money subtract(Money total, Money amount){
        //subtract amount from total money in account
        float totalMoney=total.amount();
        float subtractAmount=amount.amount();
        return new Money(totalMoney-subtractAmount);
    }

}

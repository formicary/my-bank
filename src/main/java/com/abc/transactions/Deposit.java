package com.abc.transactions;

import com.abc.accounts.Account;
import com.abc.utilities.DateProvider;
import com.abc.utilities.Money;

/**
 * Created by vahizan on 07/08/2017.
 */
public class Deposit extends AbstractTransaction {
    private Money amount;
    private String transactionDate;

    public Deposit(Money amount){
        this.amount =amount;
        //set deposit date
        transactionDate= DateProvider.getInstance().generateDate();
    }

    public Money doTransaction(Account account){
        return addMoney(account.total(), amount);
    }

    public String transactionInformation(){
        StringBuilder sb = new StringBuilder();
        sb.append("Deposit: ").append(money()
                .amount())
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

    private Money addMoney(Money total, Money amount){
        //add deposit amount to total
        float totalMoney = total.amount();
        float depositAmount = amount.amount();
        return new Money(totalMoney+depositAmount);
    }
}

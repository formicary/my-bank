package com.abc.transactions;

import com.abc.utilities.Money;

/**
 * Created by vahizan on 09/08/2017.
 */
public class InterestDeposit extends Deposit{

    public InterestDeposit(Money amount) {
        super(amount);
    }

    public String transactionInformation(){
        StringBuilder sb = new StringBuilder();
        sb.append("INTEREST Deposit: ")
                .append(money().amount())
                .append("\n")
                .append("Date: ")
                .append(stringDate());
        return sb.toString();
    }

}

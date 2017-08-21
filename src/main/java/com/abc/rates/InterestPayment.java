package com.abc.rates;

import com.abc.utilities.DateProvider;
import com.abc.utilities.Money;

/**
 * Created by vahizan on 19/08/2017.
 */
public class InterestPayment {
    private Money money;
    private String interestPaymentDate;
    public InterestPayment(Money money){
        this.money=money;
        interestPaymentDate= DateProvider.getInstance().generateDate();
    }
    public String date(){
        return interestPaymentDate;
    }
    public Money value(){
        return money;
    }
}

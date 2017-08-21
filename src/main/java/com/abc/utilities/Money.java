package com.abc.utilities;

import com.abc.constants.OperationConstants;

/**
 * Created by vahizan on 18/08/2017.
 */
public class Money {
    private float amount;

    public Money(float amount){
        this.amount = amount;
    }
    public float amount(){
        return amount;
    }
    public void calculate(Money money,OperationConstants operator){
       if(operator==OperationConstants.ADD){
           amount+= money.amount();
       }else if(operator==OperationConstants.SUBTRACT){
           amount-=money.amount();
       }
    }

}

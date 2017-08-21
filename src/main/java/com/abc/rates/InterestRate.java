package com.abc.rates;

import com.abc.constants.AccountConstantsNew;

/**
 * Created by vahizan on 18/08/2017.
 */
public class InterestRate {
   private float interestRate;
   private AccountConstantsNew accountType;
   public InterestRate(float interestRate,AccountConstantsNew accountType){
       this.interestRate=interestRate;
       this.accountType=accountType;
   }
   public float rate(){
       return this.interestRate;
   }
   public AccountConstantsNew rateType(){
       return accountType;
   }
}

package com.abc.rates;

import com.abc.utilities.Money;

import java.util.HashMap;

/**
 * Created by vahizan on 19/08/2017.
 */
public class Band {
    private HashMap<Money,InterestRate> interestBands;
    public Band(){
        interestBands=new HashMap<Money,InterestRate>();
    }
    public void addBand(Money key, InterestRate value){
        interestBands.put(key,value);
    }
    public float interestInBand(Money total){
        Money bandKey =new Money(0);
        Money lowestBand=new Money(0);
        for(Money key:interestBands.keySet()){
            bandKey=getKeyBand(total, key, bandKey);
            lowestBand=lowestBand(total,key,lowestBand);
        }
        return sendCorrectInterest(bandKey,lowestBand);
    }
    private float sendCorrectInterest(Money bandKey,Money lowestBand){
        if(bandKey.amount()==0){
            return assignInterest(interestBands,lowestBand);
        }
        return assignInterest(interestBands,bandKey);
    }
    private Money lowestBand(Money total,Money key,Money lowestBand){
        if(total.amount()<=key.amount() && (lowestBand.amount()>key.amount()||lowestBand.amount()==0)){
            return key;
        }
        return lowestBand;
    }

    private Money getKeyBand(Money total,Money key,Money previousBand){
        if(total.amount()>=key.amount() && previousBand.amount()<key.amount()){
            return key;
        }
        return previousBand;
    }
    private float assignInterest(HashMap<Money,InterestRate> band,Money bandKey){
        if(bandKey.amount()>=0){
            InterestRate rate=band.get(bandKey);
            return rate.rate();
        }
        return 0;
    }
}

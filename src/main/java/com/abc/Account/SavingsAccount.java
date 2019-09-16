package com.abc.Account;

import com.abc.Money;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Savings Account";
    private ArrayList<InterestRule> interestRules = new ArrayList<InterestRule>();

    public SavingsAccount(){
        super();
        interestRules.add(new InterestRule(
                new BigDecimal("0.001"),
                new Money("0"),
                new Money("1000")
        ));
        interestRules.add(new InterestRule(
                new BigDecimal("0.002"),
                new Money("1000")
        ));
    }

    public String getName() {
        return ACCOUNT_NAME;
    }

    @Override
    public ArrayList<InterestRule> getInterestRules() {
        return interestRules;
    }

}

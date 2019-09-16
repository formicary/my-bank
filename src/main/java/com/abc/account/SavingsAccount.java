package com.abc.account;

import com.abc.Money;
import com.abc.account.interest.InterestRule;
import com.abc.account.interest.ValueBasedInterestRule;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Savings account";
    private ArrayList<InterestRule> interestRules = new ArrayList<InterestRule>();

    public SavingsAccount(){
        super();
        interestRules.add(new ValueBasedInterestRule(
                new BigDecimal("0.001"),
                new Money("0"),
                new Money("1000")
        ));
        interestRules.add(new ValueBasedInterestRule(
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

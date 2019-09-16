package com.abc.account;

import com.abc.Money;
import com.abc.account.interest.InterestRule;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CheckingAccount extends Account {

    private static final String ACCOUNT_NAME = "Checking account";
    private ArrayList<InterestRule> interestRules = new ArrayList<InterestRule>();

    public CheckingAccount(){
        super();
        interestRules.add(new InterestRule(new BigDecimal("0.001"), new Money("0")));
    }

    @Override
    public ArrayList<InterestRule> getInterestRules() {
        return interestRules;
    }

    public String getName(){
        return ACCOUNT_NAME;
    }

}

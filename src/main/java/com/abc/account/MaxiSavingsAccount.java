package com.abc.account;

import com.abc.Money;
import com.abc.account.interest.InterestRule;
import com.abc.account.interest.TimeBasedInterestRule;
import com.abc.account.interest.ValueBasedInterestRule;;import java.math.BigDecimal;
import java.util.ArrayList;

public class MaxiSavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Maxi-Savings account";
    private ArrayList<InterestRule> interestRules = new ArrayList<InterestRule>();

    public MaxiSavingsAccount(){
        super();
        interestRules.add(new TimeBasedInterestRule(
                new BigDecimal("0.001"),
                new BigDecimal("0.05"),
                10
                )
        );
    }

    public String getName() {
        return ACCOUNT_NAME;
    }

    @Override
    public ArrayList<InterestRule> getInterestRules() {
        return interestRules;
    }

}

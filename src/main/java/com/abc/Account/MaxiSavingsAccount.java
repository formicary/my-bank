package com.abc.Account;

import com.abc.Money;;import java.math.BigDecimal;
import java.util.ArrayList;

public class MaxiSavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Maxi-Savings Account";
    private static ArrayList<InterestRule> interestRules = new ArrayList<InterestRule>();

    public MaxiSavingsAccount(){
        super();
        interestRules.add(new InterestRule(
                new BigDecimal("0.02"),
                new Money("0"),
                new Money("1000")
        ));
        interestRules.add(new InterestRule(
                new BigDecimal("0.05"),
                new Money("1000"),
                new Money("2000")
        ));
        interestRules.add(new InterestRule(
                new BigDecimal("0.1"),
                new Money("2000")
        ));
    }

    public String getName() {
        return ACCOUNT_NAME;
    }

    @Override
    public ArrayList<InterestRule> getInterestRules() {
        return interestRules;
    }

    public Money getTotalInterestEarned() {
        Money balance = getBalance();

        Money lowerInterest = calculateInterest(
                balance,
                new BigDecimal("0.02"),
                new Money("0"),
                new Money("1000")
        );
        Money middleInterest = calculateInterest(
                balance,
                new BigDecimal("0.05"),
                new Money("1000"),
                new Money("2000")
        );
        Money topInterest = calculateInterest(
                balance,
                new BigDecimal("0.1"),
                new Money("2000")
        );

        return lowerInterest.add(middleInterest).add(topInterest);
    }
}

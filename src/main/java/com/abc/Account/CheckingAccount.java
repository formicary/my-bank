package com.abc.Account;

import com.abc.Money;
import com.sun.tools.javac.comp.Check;;import java.math.BigDecimal;
import java.util.ArrayList;

public class CheckingAccount extends Account {

    private static final String ACCOUNT_NAME = "Checking Account";
    private static ArrayList<InterestRule> interestRules = new ArrayList<InterestRule>();

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

    public Money getTotalInterestEarned() {
        Money balance = getBalance();

        return calculateInterest(
                balance,
                new BigDecimal("0.001"),
                new Money("0")
        );
    }

}

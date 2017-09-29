package com.abc;

import java.util.List;

public interface InterestByRules {

    default double calculateInterestWithRules
            (double amount, List<InterestRateWithValue> rules, double defaultRate, double acc) {
        if (rules == null || rules.isEmpty()) {
            return acc + amount * defaultRate;
        } else {
            InterestRateWithValue head = rules.get(0);
            double decisionValue = head.getDecisionValue();
            double interestValue = head.getInterestRate();

            if (amount <= decisionValue) {
                return amount * interestValue;
            } else {
                return calculateInterestWithRules(amount - decisionValue, rules.subList(1,rules.size()),
                        defaultRate, acc + interestValue * decisionValue);
            }
        }
    }
}

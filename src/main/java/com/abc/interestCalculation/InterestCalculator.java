package com.abc.interestCalculation;

import com.abc.Account;
import com.abc.interestCalculation.interestCalculationStrategies.InterestCalculationStrategy;

public class InterestCalculator {

    private final InterestCalculationStrategyFactory interestCalculationStrategyFactory = new InterestCalculationStrategyFactory();

    public double calculateInterest(Account account) {
        InterestCalculationStrategy interestCalculationStrategy = interestCalculationStrategyFactory.getInterestCalculationStrategy(account.getAccountType());
        
        return interestCalculationStrategy.calculateInterest(account);
    }
}

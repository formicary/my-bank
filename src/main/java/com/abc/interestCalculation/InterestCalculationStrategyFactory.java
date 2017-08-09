package com.abc.interestCalculation;

import com.abc.AccountType;
import com.abc.interestCalculation.interestCalculationStrategies.CheckingAccountInterestCalculationStrategy;
import com.abc.interestCalculation.interestCalculationStrategies.InterestCalculationStrategy;
import com.abc.interestCalculation.interestCalculationStrategies.MaxiSavingsAccountInterestCalculationStrategy;
import com.abc.interestCalculation.interestCalculationStrategies.SavingsAccountInterestCalculationStrategy;

class InterestCalculationStrategyFactory {

    private final InterestCalculationStrategy currentAccountInterestCalculationStrategy = new CheckingAccountInterestCalculationStrategy();
    private final InterestCalculationStrategy savingsAccountInterestCalculationStrategy = new SavingsAccountInterestCalculationStrategy();
    private final InterestCalculationStrategy maxiSavingsAccountInterestCalculationStrategy = new MaxiSavingsAccountInterestCalculationStrategy();

    //A factory can create a new instance of a class for each request, but since our calculation strategies are stateless,
    //we can hang on to a single instance of each strategy and return that whenever someone asks for it.
    public InterestCalculationStrategy getInterestCalculationStrategy(AccountType accountType) {
        switch (accountType) {
            case CHECKING: return currentAccountInterestCalculationStrategy;
            case SAVINGS: return savingsAccountInterestCalculationStrategy;
            case MAXI_SAVINGS: return maxiSavingsAccountInterestCalculationStrategy;
        }
        throw new IllegalArgumentException("Not valid accountType");
    }
}

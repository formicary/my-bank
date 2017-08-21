package com.abc.rates;

import com.abc.utilities.DateProvider;
import com.abc.utilities.Money;
import static com.abc.constants.AccountConstants.DAYS_IN_A_YEAR;


/**
 * Created by vahizan on 07/08/2017.
 */
public class Interest {
    private InterestRate interestRate;
    private Band interestRateBand;

    public Interest(InterestRate interestRate,Band interestRateBand){
        this.interestRate=interestRate;
        this.interestRateBand=interestRateBand;
    }
    public Money calculateDailyInterest(Money money) {
        float interest = interestRateBand.interestInBand(money);
        interest /= (DAYS_IN_A_YEAR*100);
        return new Money(interest*money.amount());
    }
    public InterestRate interestRate() {
        return this.interestRate;
    }
    public String dateAdded() {
        return DateProvider.getInstance().generateDate();
    }
}

package com.abc.interestbehaviors;

import com.abc.accounts.Account;
import com.abc.util.Money;

public interface InterestBehavior {

    Money getInterest(Account account);

}

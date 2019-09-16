package com.abc.account.interest;

import com.abc.Money;
import com.abc.account.Account;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Defines and interest rule and calculates amount of interest applied to a balance for that rule
 */
public abstract class InterestRule {

    public abstract Money calculateInterest(Account account);
}

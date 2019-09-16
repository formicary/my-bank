package com.abc.account;

import com.abc.Money;
import com.abc.account.interest.InterestRule;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InterestRuleTest {

    // test for when only lowerBoundary is present
    @Test
    public void fromLowerBoundary() {
        InterestRule rule = new InterestRule(
                new BigDecimal("0.02"),
                new Money("0")
        );
        assertEquals(new Money("2"), rule.calculateInterest(new Money("100")));
    }

    //test for when balance is between upper and lower boundary
    @Test
    public void betweenUpperAndLowerBoundary() {
        InterestRule rule = new InterestRule(
                new BigDecimal("0.02"),
                new Money("100"),
                new Money("210")
        );
        assertEquals(new Money("2"), rule.calculateInterest(new Money("200")));
    }

    //test for when balance is above upper boundary
    @Test
    public void aboveUpperBoundary() {
        InterestRule rule = new InterestRule(
                new BigDecimal("0.02"),
                new Money("100"),
                new Money("200")
        );
        assertEquals(new Money("2"), rule.calculateInterest(new Money("350")));
    }
}
package com.abc.account;

import com.abc.Money;
import com.abc.Transaction;
import com.abc.account.interest.InterestRule;
import com.abc.account.interest.ValueBasedInterestRule;
import com.abc.exception.InsufficientBalanceException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InterestRuleTest {

    // test for when only lowerBoundary is present
    @Test
    public void fromLowerBoundary() {
        InterestRule rule = new ValueBasedInterestRule(
                new BigDecimal("0.02"),
                new Money("0")
        );
        Account fred = new CheckingAccount();
        try{
            fred.processTransaction(new Transaction(new Money("100")));
        } catch(InsufficientBalanceException e) {}
        assertEquals(new Money("2"), rule.calculateInterest(fred));
    }

    //test for when balance is between upper and lower boundary
    @Test
    public void betweenUpperAndLowerBoundary() {
        InterestRule rule = new ValueBasedInterestRule(
                new BigDecimal("0.02"),
                new Money("100"),
                new Money("210")
        );
        Account fred = new CheckingAccount();
        try{
            fred.processTransaction(new Transaction(new Money("200")));
        } catch(InsufficientBalanceException e) {}
        assertEquals(new Money("2"), rule.calculateInterest(fred));
    }

    //test for when balance is above upper boundary
    @Test
    public void aboveUpperBoundary() {
        InterestRule rule = new ValueBasedInterestRule(
                new BigDecimal("0.02"),
                new Money("100"),
                new Money("200")
        );
        Account fred = new CheckingAccount();
        try{
            fred.processTransaction(new Transaction(new Money("350")));
        } catch(InsufficientBalanceException e) {}
        assertEquals(new Money("2"), rule.calculateInterest(fred));
    }
}
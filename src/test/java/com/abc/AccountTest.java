package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.CheckingAccount;
import com.abc.Accounts.MaxiSavingsAccount;
import com.abc.Accounts.SavingsAccount;
import com.abc.util.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private static final Money DELTA = new Money("1e-15");

//    // value we are aiming to get
//    Money targetValue = new Money("0.1");
//
//    // absolute difference between target value and test value
//    Money difference = targetValue.subtract(bank.totalInterestPaid()).abs();
//
//    // if difference between test and target is less than delta then it is considered accurate
//    boolean lessThanDelta = DELTA.compareTo(difference) >= 0;
//
//        assert(lessThanDelta);

    // TODO add extra checking account tests for edge cases


    @Test
    public void checkingAccountDailyInterest(){
        Account checkingAccount = new CheckingAccount();

        // amount of money to check daily interest on
        Money amount = new Money("100");

        checkingAccount.deposit(amount, Transaction.CUSTOMER);
        Money interestRate = new Money("0.001");
        final Money days = new Money("365");

        // target result
        Money target = amount.multiply(interestRate.divide(days)); // amount*(interestRate/days)


        assert(target.compareTo(checkingAccount.dailyInterestEarned()) == 0);
    }


    // TODO add extra savings account tests for edge cases

    @Test
    public void savings_accountDailyInterest() {
        Account savingsAccount = new SavingsAccount();

        savingsAccount.deposit(new Money("1500"), Transaction.CUSTOMER);

        // value we are aiming to get
        Money target = new Money("0.00821917808219178082191780822000");

        assert(target.compareTo(savingsAccount.dailyInterestEarned()) == 0);
    }

    // TODO add extra maxi savings account tests for edge cases

    @Test
    public void maxiSavingsAccountDailyInterest() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        maxiSavingsAccount.deposit(new Money("3000.0"), Transaction.CUSTOMER);

        // value we are aiming to get
        Money target = new Money("0.410958904109589041095890410970000");

        assert(target.compareTo(maxiSavingsAccount.dailyInterestEarned()) == 0);
    }
}

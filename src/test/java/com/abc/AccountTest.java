package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.CheckingAccount;
import com.abc.Accounts.MaxiSavingsAccount;
import com.abc.Accounts.SavingsAccount;
import com.abc.util.Money;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    /**
     * checks if daily interest is calculated properly
     */
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



    @Test
    public void savings_accountDailyInterest() {
        Account savingsAccount = new SavingsAccount();

        savingsAccount.deposit(new Money("1500"), Transaction.CUSTOMER);

        // value we are aiming to get
        Money target = new Money("0.00821917808219178082191780822000");

        assert(target.compareTo(savingsAccount.dailyInterestEarned()) == 0);
    }


    @Test
    public void maxiSavingsAccountDailyInterest() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        maxiSavingsAccount.deposit(new Money("3000.0"), Transaction.CUSTOMER);

        // value we are aiming to get
        Money target = new Money("0.410958904109589041095890410970000");

        assert(target.compareTo(maxiSavingsAccount.dailyInterestEarned()) == 0);
    }
}

package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.AccountFactory;
import com.accenture.intereststrategies.FlatRateInterest;
import com.accenture.intereststrategies.MaxiSavingsInterestNoWithdrawals;
import com.accenture.intereststrategies.SavingsInterest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountFactoryTests {

    @Test
    public void canCreateCheckingAccount() {
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.CHECKING, "test");
        boolean isInstanceOfSavingAccount = account.getInterestStrategy() instanceof FlatRateInterest;
        assertEquals(true,  isInstanceOfSavingAccount);

    }

    @Test
    public void canCreateSavingsAccount() {
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS, "test");
        boolean isInstanceOfSavingAccount = account.getInterestStrategy() instanceof SavingsInterest;
        assertEquals(true,  isInstanceOfSavingAccount);
    }

    @Test
    public void canCreateMaxiSavingsAccount() {
        Account account = AccountFactory.createAccount(AccountFactory.AccountType.MAXI_SAVINGS, "test");
        boolean isInstanceOfSavingAccount = account.getInterestStrategy() instanceof MaxiSavingsInterestNoWithdrawals;
        assertEquals(true,  isInstanceOfSavingAccount);
    }


}

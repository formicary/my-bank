package com.abc.account;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AccountShould {
    private static final double DOUBLE_DELTA = 1e-15;
    private Account account;

    @Before
    public void setUp() {
        account = Mockito.mock(Account.class,
                Mockito.withSettings()
                        .useConstructor()
                        .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    public void AllowDeposits_GivenValueIsPositive(){
        account.deposit(1.0d);

        double accountBalance = account.sumTransactions();

        assertEquals(1.0d, accountBalance, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowInvalidArgumentException_GivenANonPositiveDeposit(){
        account.deposit(-1.0d);
    }

    @Test
    public void AllowWithdrawals_GivenValueIsPositive(){
        account.withdraw(1.0d);

        double accountBalance = account.sumTransactions();

        assertEquals(-1.0d, accountBalance, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowInvalidArgumentException_GivenANonPositiveWithdrawal(){
        account.deposit(-1.0d);
    }

    @Test
    public void GenerateCorrectAccountStatement_GivenThereAreNoTransactions(){
        when(account.getPrettyAccountType()).thenReturn("Pretty Account Name");

        String expectedAccountStatement = "Pretty Account Name\nTotal $0.00";

        assertEquals(expectedAccountStatement, account.generateStatement());
    }

    @Test
    public void GenerateCorrectAccountStatement_GivenAnAccountHasTransactions(){
        when(account.getPrettyAccountType()).thenReturn("Pretty Account Name");

        account.deposit(5.0d);
        account.withdraw(2.0d);

        String expectedAccountStatement = "Pretty Account Name\n  deposit $5.00\n  withdrawal $2.00\nTotal $3.00";


        System.out.println(account.generateStatement());
        assertEquals(expectedAccountStatement, account.generateStatement());
    }
}


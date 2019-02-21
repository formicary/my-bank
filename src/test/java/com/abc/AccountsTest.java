package com.abc;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AccountsTest {

    private static final double DOUBLE_DELTA = 1e-15;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;
    private Date date;

    @BeforeEach
    public void setUp(){
        checkingAccount = new CheckingAccount(UUID.randomUUID());
        savingsAccount = new SavingsAccount(UUID.randomUUID());
        maxiSavingsAccount = new MaxiSavingsAccount(UUID.randomUUID());
        date = DateProvider.getInstance().now();
    }

    @Nested
    public class DepositTest{
        @Test
        public void negativeAmountTest(){
            RuntimeException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> checkingAccount.deposit(-1000, date));
            assertTrue(thrown.getMessage().contains("amount must be greater than zero"));
        }

        @Test
        public void correctAmount(){
            checkingAccount.deposit(1000.0, date);
            assertEquals(1000.0, checkingAccount.sumTransactions());
        }
    }

    @Nested
    public class WithdrawTest{
        @Test
        public void insufficientFundsTest(){
            RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> checkingAccount.withdraw(1000, date));
            assertTrue(thrown.getMessage().contains("account: " + checkingAccount.getAccountID() + " has insufficient funds"));
        }

        @Test
        public void negativeAmountTest(){
            RuntimeException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(-1000, date));
            assertTrue(thrown.getMessage().contains("amount must be greater than zero"));
        }

        @Test
        public void correctAmount(){
            checkingAccount.deposit(1000.0, date);
            checkingAccount.withdraw(200.0, date);
            assertEquals(800.0, checkingAccount.sumTransactions());
        }
    }

    @Nested
    public class SavingsAccountTest{
        @Test
        public void interestEarned_Yearly_LessThanThousand_Test(){
            savingsAccount.deposit(800, date);
            assertEquals(0.8, savingsAccount.interestEarned(Account.YEARLY), DOUBLE_DELTA);
        }

        @Test
        public void interestEarned_Yearly_MoreThanThousand_Test(){
            savingsAccount.deposit(2000, date);
            assertEquals(3, savingsAccount.interestEarned(Account.YEARLY), DOUBLE_DELTA);
        }

        @Test
        public void interestEarned_Daily_LessThanThousand_Test(){
            savingsAccount.deposit(365, date);
            assertEquals(0.001, savingsAccount.interestEarned(Account.DAILY), DOUBLE_DELTA);
        }
    }

    @Nested
    public class MaxiSavingsAccountTest{

        private Date oldDate;

        @BeforeEach
        public void setUpMaxi(){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, -10);
            oldDate = cal.getTime();
        }

        @Test
        public void interestEarned_Yearly_NoWithdrawalsLastTenDays_Test(){
            maxiSavingsAccount.deposit(1000, date);
            assertEquals(50, maxiSavingsAccount.interestEarned(Account.YEARLY), DOUBLE_DELTA);
        }

        @Test
        public void interestEarned_Yearly_WithdrawalsLastTenDays_Test(){
            maxiSavingsAccount.deposit(3000.0, date);
            maxiSavingsAccount.withdraw(1000.0, oldDate);

            assertEquals(100.0, maxiSavingsAccount.interestEarned(Account.YEARLY), DOUBLE_DELTA);
        }

        @Test
        public void interestEarned_Daily_NoWithdrawalsLastTenDays_Test(){
            maxiSavingsAccount.deposit(365000, date);
            assertEquals(50.0, maxiSavingsAccount.interestEarned(Account.DAILY), DOUBLE_DELTA);
        }
    }

    @Nested
    public class CheckingAccountTest{
        @Test
        public void interestEarned_Yearly_Test(){
            checkingAccount.deposit(1000, date);
            assertEquals(1.0, checkingAccount.interestEarned(Account.YEARLY), DOUBLE_DELTA);
        }

        @Test
        public void interestEarned_Daily_Test(){
            checkingAccount.deposit(365000, date);
            assertEquals(1.0, checkingAccount.interestEarned(Account.DAILY), DOUBLE_DELTA);
        }
    }

    @Test
    public void sumTransactionsTest(){
        checkingAccount.deposit(3000.0, date);
        checkingAccount.withdraw(1500.0, date);
        assertEquals(1500.0, checkingAccount.sumTransactions());
    }
}

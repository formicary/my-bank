package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test(expected = IllegalArgumentException.class)
    public void deposit_zero_amount_on_account_does_not_create_transaction() {
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(0);
        assertEquals(0, maxiSavingsAccount.getTransactions().size());
    }

    /**
     * A customer can transfer between their accounts
     */
    @Test
    public void transfer_between_accounts_is_successful() {
        Account from = new Account(AccountType.CHECKING);
        Account to = new Account(AccountType.SAVINGS);
        double amount = 500.00;
        from.deposit(2000);
        to.deposit(3000);

        int preTransferTotalTransactionsOfFromAccount = from.getTransactions().size();
        int preTransferTotalTransactionsOfToAccount = to.getTransactions().size();

        from.transferAmountTo(amount, to);
        assertEquals(from.getSumTransactions(), 1500, DOUBLE_DELTA);
        assertEquals(to.getSumTransactions(), 3500, DOUBLE_DELTA);

        int postTransferTotalTransactionsOfFromAccount = from.getTransactions().size();
        int postTransferTotalTransactionsOfToAccount = to.getTransactions().size();

        assertEquals(postTransferTotalTransactionsOfFromAccount, preTransferTotalTransactionsOfFromAccount + 1);
        assertEquals(postTransferTotalTransactionsOfToAccount, preTransferTotalTransactionsOfToAccount + 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void deposit_from_one_account_to_the_same_account_is_not_possible() {
        AccountType checking = AccountType.CHECKING;
        Account account = new Account(checking);

        int preDepositTransactions = account.getTransactions().size();
        account.transferAmountTo(1000, account);
        int postDepositTransactions = account.getTransactions().size();

        assertEquals(preDepositTransactions, postDepositTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfer_with_insufficient_balance_account_is_not_possible() {
        Account accountFrom = new Account(AccountType.CHECKING);
        Account accountTo = new Account(AccountType.CHECKING);
        accountFrom.deposit(1000);
        accountTo.deposit(100);

        int preDepositFromTransactions = accountFrom.getTransactions().size();
        int preDepositToTransactions = accountTo.getTransactions().size();
        accountFrom.transferAmountTo(1500, accountTo);

        int postDepositFromTransactions = accountFrom.getTransactions().size();
        int postDepositToTransactions = accountTo.getTransactions().size();

        assertEquals(preDepositFromTransactions, postDepositFromTransactions);
        assertEquals(preDepositToTransactions, postDepositToTransactions);

    }

    @Test
    public void interest_paid_matches_expected_for_maxi_savings_account_with_no_withdrawal_on_the_past_ten_days() {
        Account maxiAccount = new Account(AccountType.MAXI_SAVINGS);
        Date date = new Date();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.add(Calendar.DAY_OF_MONTH, -12);
        cal2.setTime(date);
        cal2.add(Calendar.DAY_OF_MONTH, -11);

        maxiAccount.specificDeposit(3000.0, cal1.getTime());
        /* withdrawal 11 days ago */
        maxiAccount.specificWithdraw(500.0, cal2.getTime());

        double totalInterestPaidBeforeTenDays = maxiAccount.getInterestEarned();
        assertEquals(125.0, totalInterestPaidBeforeTenDays, DOUBLE_DELTA);

        /* withdrawal 5 days ago */
        cal3.setTime(date);
        cal3.add(Calendar.DAY_OF_MONTH, -5);
        maxiAccount.specificWithdraw(500.0, cal3.getTime());
        double totalInterestPaidBetweenTenDays = maxiAccount.getInterestEarned();
        assertEquals(2.0, totalInterestPaidBetweenTenDays, DOUBLE_DELTA);
    }

    @Test
    public void interest_paid_matches_expected_for_maxi_savings_account() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(1000);
        assertEquals(1, account.getInterestEarned(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void specific_deposit_with_negative_amount_is_not_possible() {
        Account account = new Account(AccountType.CHECKING);
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JANUARY, 1, 10, 11, 12);
        Date date = cal.getTime();

        int preDepositTransactions = account.getTransactions().size();
        account.specificDeposit(-1000.0, date);
        int postDepositTransactions = account.getTransactions().size();

        assertEquals(preDepositTransactions, postDepositTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void specific_withdrawal_with_negative_amount_is_not_possible() {
        Account account = new Account(AccountType.CHECKING);
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JANUARY, 5, 11, 12, 13);
        Date date = cal.getTime();

        int preDepositTransactions = account.getTransactions().size();
        account.specificWithdraw(-1000, date);
        int postDepositTransactions = account.getTransactions().size();

        assertEquals(preDepositTransactions, postDepositTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deposit_with_negative_amount_is_not_possible() {
        Account account = new Account(AccountType.CHECKING);
        int preDepositTransactions = account.getTransactions().size();

        account.deposit(-1000.0);
        int postDepositTransactions = account.getTransactions().size();

        assertEquals(preDepositTransactions, postDepositTransactions);
    }

    @Test
    public void specific_withdraw_transaction_on_previous_dates_is_possible() {
        Account account = new Account(AccountType.CHECKING);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.set(2015, Calendar.JANUARY, 1, 10, 11, 12);
        cal2.set(2015, Calendar.JANUARY, 2, 10, 11, 12);
        Date date1 = cal1.getTime();
        Date date2 = cal1.getTime();

        account.specificDeposit(2000.0, date1);
        account.specificWithdraw(500, date2);

        assertEquals(1500, account.getSumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void specific_withdrawal_on_the_past_with_insufficient_balance_is_not_possible() {
        Account account = new Account(AccountType.CHECKING);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.set(2015, Calendar.JANUARY, 1, 10, 11, 12);
        cal2.set(2015, Calendar.JANUARY, 2, 10, 11, 12);
        Date date1 = cal1.getTime();
        Date date2 = cal2.getTime();

        account.specificDeposit(1000, date1);
        int preDepositTransactions = account.getTransactions().size();

        account.specificWithdraw(2000, date2);
        int postDepositTransactions = account.getTransactions().size();

        assertEquals(preDepositTransactions, postDepositTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawal_with_negative_amount_is_not_possible() {
        Account account = new Account(AccountType.CHECKING);
        int preDepositTransactions = account.getTransactions().size();

        account.withdraw(-1000.0);
        int postDepositTransactions = account.getTransactions().size();

        assertEquals(preDepositTransactions, postDepositTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawal_with_insufficient_balance_is_not_possible() {
        Account account = new Account(AccountType.CHECKING);

        account.deposit(1000);
        int preDepositTransactions = account.getTransactions().size();
        account.withdraw(2000);
        int postDepositTransactions = account.getTransactions().size();

        assertEquals(preDepositTransactions, postDepositTransactions);
    }

    @Test
    public void interests_earned_for_savings_account() {
        Account account = new Account(AccountType.SAVINGS);

        account.deposit(1000);
        assertEquals(1, account.getInterestEarned(), DOUBLE_DELTA);

        account.deposit(1000);
        assertEquals(3, account.getInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void daily_accrue_of_interests() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account maxiAccount = new Account(AccountType.MAXI_SAVINGS);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal4 = Calendar.getInstance();
        cal1.set(2015, Calendar.MARCH, 20, 10, 11, 12);
        cal2.set(2015, Calendar.MARCH, 30, 10, 11, 12);
        cal3.set(2015, Calendar.MARCH, 31, 10, 11, 12);
        cal4.set(2015, Calendar.APRIL, 1, 10, 11, 12);
        Date date1 = cal1.getTime();
        Date date2 = cal2.getTime();
        Date date3 = cal3.getTime();
        Date date4 = cal4.getTime();

        /* interest calculated for 3 deposits on checking account */
        checkingAccount.specificDeposit(1000, date1);
        checkingAccount.specificDeposit(2000, date2);
        checkingAccount.specificDeposit(3000, date3);
        double postDepositDailyInterestAccrualChecking = checkingAccount.getInterestEarned(date4);
        assertEquals(0.1232876712328767, postDepositDailyInterestAccrualChecking, DOUBLE_DELTA);

        /* interest calculated for 3 deposits on saving account */
        savingsAccount.specificDeposit(1000, date1);
        savingsAccount.specificDeposit(2000, date2);
        savingsAccount.specificDeposit(3000, date3);
        double postDepositDailyInterestAccrualSavings = savingsAccount.getInterestEarned(date4);
        assertEquals(1.1506849315068493, postDepositDailyInterestAccrualSavings, DOUBLE_DELTA);

        /* withdrawal on the last 10 days, between transactions */
        maxiAccount.specificDeposit(1000, date1);
        maxiAccount.specificWithdraw(1000, date2);
        maxiAccount.specificDeposit(3000, date3);
        double postDepositDailyInterestAccrualMaxi1 = maxiAccount.getInterestEarned(date4);
        assertEquals(0.03287671232876713, postDepositDailyInterestAccrualMaxi1, DOUBLE_DELTA);

        /* withdrawal older than 10 days, between transactions */
        maxiAccount.specificDeposit(1000, date1);
        maxiAccount.specificWithdraw(500, date1);
        maxiAccount.specificDeposit(3000, date3);
        double postDepositDailyInterestAccrualMaxi2 = maxiAccount.getInterestEarned(date4);
        assertEquals(0.13835616438356163,postDepositDailyInterestAccrualMaxi2,DOUBLE_DELTA);
    }
}

package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    /*Note: BigDecimals are rounded to 15.d.p. and compared to expected values. Expected values calculated using
    high-precision calculator (source: https://keisan.casio.com/calculator) and rounded to 15.d.p.
     */
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill", "bill@email.com").addAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(BigDecimal.valueOf(1000));
        checkingAccount.accrueInterest();
        // Testing daily interest accrual
        BigDecimal expectedValue = BigDecimal.valueOf(0.002739726027397); //3 last digit
        BigDecimal actualValue15DP = checkingAccount.getInterestAccrued().setScale(15, RoundingMode.HALF_UP);
        //Checking equality by comparing BigDecimals rounded to 15.D.P.
        assertEquals(0, expectedValue.compareTo(actualValue15DP));

        //testing compound interest - 1 full year.

        for (int i = 0; i < 364; i++) {
            checkingAccount.accrueInterest();
        }
        BigDecimal expectedValueFullYear = BigDecimal.valueOf(1.000498795477285); //3 last digit
        BigDecimal actualValueFullYear15DP = checkingAccount.getInterestAccrued().setScale(15, RoundingMode.HALF_UP);
        assertEquals(0, expectedValueFullYear.compareTo(actualValueFullYear15DP));
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill", "bill@email.com").addAccount(savingsAccount));
        savingsAccount.deposit(BigDecimal.valueOf(1500));
        savingsAccount.accrueInterest();
        BigDecimal expectedValue = BigDecimal.valueOf(0.005479452054795); //0.005479452054795 = expected 1 day int for 1500.
        BigDecimal actualValue15DP = savingsAccount.getInterestAccrued().setScale(15, RoundingMode.HALF_UP);
        assertEquals(0, expectedValue.compareTo(actualValue15DP));
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill", "bill@email.com").addAccount(maxiSavingsAccount));

        // Testing interest accrual.
        maxiSavingsAccount.deposit(BigDecimal.valueOf(3000));
        maxiSavingsAccount.accrueInterest();
        BigDecimal expectedValue = BigDecimal.valueOf(0.410958904109589);
        BigDecimal actualValue15DP = maxiSavingsAccount.getInterestAccrued().setScale(15, RoundingMode.HALF_UP);
        assertEquals(0, expectedValue.compareTo(actualValue15DP));

        //Testing interest toggling depending on days since last withdrawal.
        maxiSavingsAccount.withdraw(BigDecimal.valueOf(1000)); // Withdrawal made at present time.
        LocalDate tenDaysAhead = DateProvider.now().plusDays(10);
        LocalDate elevenDaysAhead = DateProvider.now().plusDays(11);
        BigDecimal expectedInterestRateNoRecentWithdrawal = BigDecimal.valueOf(0.05);
        BigDecimal expectedInterestRateAfterWithdrawal = BigDecimal.valueOf(0.001);
        assertEquals(expectedInterestRateNoRecentWithdrawal.compareTo(maxiSavingsAccount.getCurrentYearlyInterest()), 0);

        maxiSavingsAccount.toggleInterestRate(tenDaysAhead);
        //interest rate is adjusted as if it were 10 days in the future - in effect, withdrawal was made within last 10 days.
        assertEquals(expectedInterestRateAfterWithdrawal.compareTo(maxiSavingsAccount.getCurrentYearlyInterest()), 0);
        maxiSavingsAccount.toggleInterestRate(elevenDaysAhead);
        //Interest rate adjusted as if withdrawal were made 11 days ago - outside the 10 day limit for low interest rates.
        assertEquals(expectedInterestRateNoRecentWithdrawal.compareTo(maxiSavingsAccount.getCurrentYearlyInterest()), 0);
    }
}



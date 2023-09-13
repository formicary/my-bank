package com.abc;

import com.abc.account.CheckingAccount;
import com.abc.account.Account;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
import org.junit.Before;
import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");

    private Customer customer;
    private Bank bank;

    @Before
    public void setUp() {
        decimalFormatter.setRoundingMode(RoundingMode.UP);
        bank = new Bank();
        customer = new Customer("Wayne");
        bank.addCustomer(customer);
    }

    @Test
    public void test_customerSummaryWithOneAccount() {
        customer.openAccount(new CheckingAccount());
        assertEquals("Customer Summary\r\n - John (1 account)", bank.printCustomerSummary());
    }

    @Test
    public void test_customerSummaryWithMoreAccounts() {
        customer.openAccount(new CheckingAccount());
        customer.openAccount(new MaxiSavingsAccount());
        assertEquals("Customer Summary\r\n - John (2 accounts)", bank.printCustomerSummary());
    }

    /**
     * to calculate test result calculator from following site was used
     * <a href="https://www.thecalculatorsite.com/finance/calculators/daily-compound-interest.php">...</a>
     * with following setup
     * principal - 1000
     * interest rate - 0.1% yearly
     * years - 1
     * include all days of week - yes
     * daily reinvest rate - 100%
     * Additional contributions - none
     */
    @Test
    public void test_checkingAccountSingleDeposit() {
        Account checkingAccount = new CheckingAccount();
        customer.openAccount(checkingAccount);
        checkingAccount.deposit(1000.0, LocalDateTime.now().minusDays(365));
        assertEquals("1.01", decimalFormatter.format(bank.calculateTotalInterestsPaid()));
    }

    /**
     * to calculate test result calculator from following site was used
     * <a href="https://www.thecalculatorsite.com/finance/calculators/daily-compound-interest.php">...</a>
     * with following setup
     * principal - 1000
     * interest rate - 0.1% yearly
     * years - 1
     * include all days of week - yes
     * daily reinvest rate - 100%
     * Additional contributions - yes
     * One-time additional deposit - 2000 03/16/2023
     * Start date 09/12/2022
     */
    @Test
    public void test_checkingAccountTwoDeposits() {
        Account checkingAccount = new CheckingAccount();
        customer.openAccount(checkingAccount);
        checkingAccount.deposit(2000.0, LocalDateTime.now().minusDays(180));
        checkingAccount.deposit(1000.0, LocalDateTime.now().minusDays(365));
        assertEquals("1.99", decimalFormatter.format(bank.calculateTotalInterestsPaid()));
    }

    /**
     * to calculate test result calculator from following site was used
     * <a href="https://www.thecalculatorsite.com/finance/calculators/daily-compound-interest.php">...</a>
     * with following setup
     * principal - 2000
     * interest rate - 0.2% yearly
     * years - 1
     * include all days of week - yes
     * daily reinvest rate - 100%
     * Additional contributions - none
     */
    @Test
    public void test_savingAccountSingleDeposit() {
        Account saving = new SavingsAccount();
        customer.openAccount(saving);
        saving.deposit(2000.0,LocalDateTime.now().minusYears(1));
        assertEquals("4.01", decimalFormatter.format(bank.calculateTotalInterestsPaid()));
    }

    @Test
    public void test_savingAccountTwoDeposit() {
        Account saving = new SavingsAccount();
        customer.openAccount(saving);
        saving.deposit(1000,LocalDateTime.now().minusDays(180));
        saving.deposit(1000,LocalDateTime.now().minusDays(365));
        assertEquals("2.49", decimalFormatter.format(bank.calculateTotalInterestsPaid()));
    }
    /**
     * to calculate test result calculator from following site was used
     * <a href="https://www.thecalculatorsite.com/finance/calculators/daily-compound-interest.php">...</a>
     * with following setup
     * principal - 1000
     * interest rate - 5% yearly
     * years - 1
     * include all days of week - yes
     * daily reinvest rate - 100%
     * Additional contributions - none
     */
    @Test
    public void test_maxiSavingAccount() {
        Account saving = new MaxiSavingsAccount();
        customer.openAccount(saving);
        saving.deposit(1000,LocalDateTime.now().minusDays(365));
        assertEquals("51.27", decimalFormatter.format(bank.calculateTotalInterestsPaid()));
    }

    @Test
    public void test_multipleCustomersWithAccount() {
        Account checkingAccount = new CheckingAccount();
        customer.openAccount(checkingAccount);
        checkingAccount.deposit(1000.0, LocalDateTime.now().minusDays(365));

        Customer secondCustomer = new Customer("Rooney");
        Account savingsAccount = new SavingsAccount();
        savingsAccount.deposit(2000.0,LocalDateTime.now().minusDays(365));
        secondCustomer.openAccount(savingsAccount);
        bank.addCustomer(secondCustomer);
        assertEquals("5.01", decimalFormatter.format(bank.calculateTotalInterestsPaid()));
    }

}

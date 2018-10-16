package com.abc;
import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import java.math.RoundingMode;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    // Opens checking account and tests the interest rate.
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(0.1).setScale(2, RoundingMode.HALF_UP), bank.totalInterestPaid());
    }

    // Opens savings account and tests the interest rate.
    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(BigDecimal.valueOf(1500));
        assertEquals(BigDecimal.valueOf(2).setScale(2, RoundingMode.HALF_UP), bank.totalInterestPaid());
    }

    // Opens Maxi Savings Account and checks the interest rate with no withdrawals in last 10 days.
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(BigDecimal.valueOf(3000));
        assertEquals(BigDecimal.valueOf(150).setScale(2, RoundingMode.HALF_UP), bank.totalInterestPaid());
    }
    // Opens Maxi Savings Account and checks the interest rate with a withdrawal in last 10 days.
    @Test
    public void maxi_savings_account_withdraw() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(BigDecimal.valueOf(3000));
        checkingAccount.withdraw(BigDecimal.valueOf(2000));
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP), bank.totalInterestPaid());
    }

}

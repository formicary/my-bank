package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.AccountFactory;
import com.abc.util.Money;
import com.abc.util.USDFactory;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class BankTest {

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount((AccountFactory.createAccount(AccountFactory.AccountType.CHECKING)));
        bank.addCustomer(john);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Money.init(Currency.getInstance("USD"), RoundingMode.HALF_EVEN);
        Bank bank = new Bank();
        Account checkingAccount = AccountFactory.createAccount(AccountFactory.AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        Money ONE_HUNDRED = USDFactory.DollarToMoney(100L);
        checkingAccount.deposit(ONE_HUNDRED);

        Money expectedTotalInterestPaid = new Money(BigDecimal.valueOf(10));

        assertEquals(expectedTotalInterestPaid.getAmount().longValue(), bank.totalInterestPaid().getAmount().longValue());
    }

    @Test
    public void savings_account() {
        Money.init(Currency.getInstance("USD"), RoundingMode.HALF_EVEN);
        Bank bank = new Bank();
        Account checkingAccount = AccountFactory.createAccount(AccountFactory.AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));


        Money money = USDFactory.DollarToMoney(1500L);
        checkingAccount.deposit(money);

        Money expectedInterest = USDFactory.DollarToMoney(2L);

        assertEquals(expectedInterest.getAmount().longValue(), bank.totalInterestPaid().getAmount().longValue());
    }

    @Test
    public void maxi_savings_account() {
        Money.init(Currency.getInstance("USD"), RoundingMode.HALF_EVEN);
        Bank bank = new Bank();
        Account checkingAccount = AccountFactory.createAccount(AccountFactory.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(USDFactory.DollarToMoney(3000L));

        Money expectedInterest = USDFactory.DollarToMoney(170L);

        assertEquals(expectedInterest.getAmount().longValue(), bank.totalInterestPaid().getAmount().longValue());
    }

}

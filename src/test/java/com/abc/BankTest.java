package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.CheckingAccount;
import com.abc.util.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final Money DELTA = new Money("1e-15");

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(new Money("100.0"));

        // value we are aiming to get
        Money targetValue = new Money("0.1");

        // absolute difference between target value and test value
        Money difference = targetValue.subtract(bank.totalInterestPaid()).abs();

        // if difference between test and target is less than delta then it is considered accurate
        boolean lessThanDelta = DELTA.compareTo(difference) >= 0;

        assert(lessThanDelta);
    }

//    @Test
//    public void savings_account() {
//        Bank bank = new Bank();
//        Account checkingAccount = new SavingsAccount();
//        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
//
//        checkingAccount.deposit(1500.0);
//
//        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
//    }
//
//    @Test
//    public void maxi_savings_account() {
//        Bank bank = new Bank();
//        Account checkingAccount = new MaxiSavingsAccount();
//        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
//
//        checkingAccount.deposit(3000.0);
//
//        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
//    }

}

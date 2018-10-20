package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.CheckingAccount;
import com.abc.util.Money;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BankTest {

    private Account mockAccount;
    private Account mockAccount2;

    @Before
    public void Setup(){
        //mockAccount = mock(CheckingAccount.class, CALLS_REAL_METHODS);

        mockAccount = spy(new CheckingAccount());
        mockAccount2 = spy(new CheckingAccount());

        // daily interest earned is always 1
        // allows us to count how many times interest is given (actual result not important)
        doReturn(new Money("1")).when(mockAccount).dailyInterestEarned();
        doReturn(new Money("1")).when(mockAccount2).dailyInterestEarned();
    }

    // TODO add extra customer summary tests
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    /**
     * check no accounts
     */
    @Test
    public void customerSummaryNoAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (0 accounts)", bank.customerSummary());
    }

    /**
     * check high number of accounts
     */
    @Test
    public void customerSummaryElevenAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        for (int i = 0; i < 11; i++) {
            john.openAccount(new CheckingAccount());
        }
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (11 accounts)", bank.customerSummary());
    }

    /**
     * check that no interest has been paid out to customer account
     */
    @Test
    public void NoInterestEarned() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(new Money("100.0"), Transaction.CUSTOMER);

        // value we are aiming to get
        Money targetValue = Money.ZERO;

        assert targetValue.compareTo(bank.totalInterestPaid()) == 0;
    }

    @Test
    public void NoCustomersNoInterestEarned() {
        Bank bank = new Bank();

        bank.payDailyInterest();

        // value we are aiming to get
        Money targetValue = Money.ZERO;

        assert targetValue.compareTo(bank.totalInterestPaid()) == 0;
    }

    /**
     * ensures accounts are being properly paid their daily interest
     */
    @Test
    public void SingleDailyInterestEarned() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill").openAccount(mockAccount);

        bank.addCustomer(bill);

        mockAccount.deposit(new Money("100.0"), Transaction.CUSTOMER);

        // value we are aiming to get
        Money targetValue = new Money("1");

        // pay daily interest to all customers
        bank.payDailyInterest();

        // ensure that total interest paid has happened 1 time
        assert bank.totalInterestPaid().compareTo(targetValue) == 0;
    }

    /**
     * ensures all customers are being paid their
     */
    @Test
    public void DoubleDailyInterestEarnedMultipleCustomers() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill").openAccount(mockAccount);
        Customer steve = new Customer("Steve").openAccount(mockAccount2);

        bank.addCustomer(bill);
        bank.addCustomer(steve);

        mockAccount.deposit(new Money("100.0"), Transaction.CUSTOMER);

        // value we are aiming to get
        Money targetValue = new Money("4");

        // pay daily interest to all customers twice
        bank.payDailyInterest();
        bank.payDailyInterest();

        // ensure that total interest paid has happened 1 time
        assert bank.totalInterestPaid().compareTo(targetValue) == 0;
    }
}

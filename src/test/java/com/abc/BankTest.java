package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

import static com.abc.Account.*;

public class BankTest {

    public static final Double DOUBLE_DELTA = 1e-15;

    @Test
    public void testApp() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Customer henry = new Customer("Henry");
        bank.addCustomer(bill);
        bank.addCustomer(henry);

        //No Accounts
        assertEquals("Customer Summary"
                + "\n - Bill (0 accounts)"
                + "\n - Henry (0 accounts)"
                , bank.printCustomerSummary());

        //Some Accounts
        bill.openAccount(AccountType.CHECKING);
        henry.openAccount(AccountType.MAXI_SAVINGS_I);
        henry.openAccount(AccountType.SAVINGS);

        assertEquals("Customer Summary"
                        + "\n - Bill (1 account)"
                        + "\n - Henry (2 accounts)"
                , bank.printCustomerSummary());

        if (bill.getAccounts().containsKey(AccountType.CHECKING)) {
            bill.getAccounts().get(AccountType.CHECKING).deposit(100d);
            bill.getAccounts().get(AccountType.CHECKING).withdraw(10d);
        }
        assertEquals(0.09, bank.getTotalInterestPaid(), DOUBLE_DELTA);
        assertEquals(bill.getTotalInterestEarned(), bank.getTotalInterestPaid(), DOUBLE_DELTA);

        if (henry.getAccounts().containsKey(AccountType.SAVINGS))
            henry.getAccounts().get(AccountType.SAVINGS).deposit(1500d);
        assertEquals(0.09 + 2, bank.getTotalInterestPaid(), DOUBLE_DELTA);
        assertEquals(bill.getTotalInterestEarned() + henry.getTotalInterestEarned(), bank.getTotalInterestPaid(), DOUBLE_DELTA);

        if (henry.getAccounts().containsKey(AccountType.MAXI_SAVINGS_I))
            henry.getAccounts().get(AccountType.MAXI_SAVINGS_I).deposit(3000d);
        assertEquals(0.09 + 2 + 170, bank.getTotalInterestPaid(), DOUBLE_DELTA);
        assertEquals(bill.getTotalInterestEarned() + henry.getTotalInterestEarned(), bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

}

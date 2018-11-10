package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountTypes.CHECKING));
        bank.addCustomer(john);

        Customer henry = new Customer("Henry");
        henry.openAccount(new Account(AccountTypes.CHECKING));
        henry.openAccount(new Account(AccountTypes.SAVINGS));
        bank.addCustomer(henry);

        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(AccountTypes.CHECKING));
        oscar.openAccount(new Account(AccountTypes.SAVINGS));
        oscar.openAccount(new Account(AccountTypes.MAXI_SAVINGS));
        bank.addCustomer(oscar);

        String expected = "Customer Summary";
        for (Customer c : bank.getCustomers()) {
            expected += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }
        
        assertEquals(expected, bank.customerSummary());
    }

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountTypes.CHECKING);
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingAccount = new Account(AccountTypes.SAVINGS);
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openAccount(savingAccount);

        savingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingAccount = new Account(AccountTypes.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openAccount(maxiSavingAccount);

        maxiSavingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

package com.abc;

import com.abc.Exceptions.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        try {
            bank.addCustomer(john);

        } catch (CustomerNameAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
        }

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() throws CustomerNameAlreadyExistsException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() throws CustomerNameAlreadyExistsException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() throws CustomerNameAlreadyExistsException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test(expected = CustomerNameAlreadyExistsException.class)
    public void duplicate_name() throws CustomerNameAlreadyExistsException {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Customer bill_two = new Customer("Bill");
        bank.addCustomer(bill);
        bank.addCustomer(bill_two);

    }

    @Test
    public void remove_customer() throws CustomerNameAlreadyExistsException {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        assertEquals(true, bank.removeCustomer("Bill"));
        assertEquals(false, bank.removeCustomer("Bill"));

    }

    @Test
    public void first_customer() throws CustomerNameAlreadyExistsException {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Customer jason = new Customer("Jason");
        Customer bob = new Customer("Bob");
        bank.addCustomer(bill);
        bank.addCustomer(jason);
        bank.addCustomer(bob);

        assertEquals("Bill", bank.getFirstCustomer());

        bank.removeCustomer("Bill");

        assertEquals("Jason", bank.getFirstCustomer());

        bank.removeCustomer("Jason");

        assertEquals("Bob", bank.getFirstCustomer());

        bank.removeCustomer("Bob");

        assertEquals("Bank has no customers", bank.getFirstCustomer());

    }

}

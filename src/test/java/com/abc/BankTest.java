package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    /* A customer can open an account */
    @Test
    public void customer_summary_matches_expected_summary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        AccountType checking = AccountType.CHECKING;
        john.openAccount(new Account(checking));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void bank_interest_paid_matches_expected_for_checking_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interest_paid_matches_expected_for_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interest_paid_matches_expected_for_maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        double totalInterestPaid = bank.getTotalInterestPaid();
        assertEquals(150.0, totalInterestPaid, DOUBLE_DELTA);
    }

    @Test
    public void verify_name_of_first_customer_of_the_bank() {
        Bank bank = new Bank();
        Customer anna = new Customer("Anna");
        Customer manos = new Customer("Manos");
        bank.addCustomer(anna);
        bank.addCustomer(manos);

        assertEquals("Anna", bank.getFirstCustomer());
    }

    @Test
    public void verify_retrieval_of_banks_first_customer_produce_error_if_no_customers_added() throws Exception {
        Bank bank = new Bank();
        assertEquals("Error, no customers found", bank.getFirstCustomer());
    }
}

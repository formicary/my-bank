package com.abc;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummaryOnePersonOneAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryOnePersonTwoAccounts(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummaryTwoPeople(){
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        Customer bob = new Customer("Bob");
        bank.addCustomer(bob);

        assertEquals("Customer Summary\n - John (1 account)\n - Bob (0 accounts)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_no_withdrawal() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_recent_withdrawal(){
        String accountCreation = "01/03/2019 09:29:58";
        String transActionDate = "25/03/2019 10:31:48";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date d1 = format.parse(accountCreation);
            Date d2 = format.parse(transActionDate);

            Bank bank = new Bank();
            Account checkingAccount = new Account(Account.MAXI_SAVINGS, d1);
            bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

            checkingAccount.deposit(3000.0);
            checkingAccount.withdraw(1000.0,d2);

            assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void maxi_savings_account_not_recent_withdrawal(){
        String accountCreation = "01/03/2019 09:29:58";
        String transActionDate = "10/03/2019 10:31:48";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date d1 = format.parse(accountCreation);
            Date d2 = format.parse(transActionDate);

            Bank bank = new Bank();
            Account checkingAccount = new Account(Account.MAXI_SAVINGS, d1);
            bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

            checkingAccount.deposit(3000.0);
            checkingAccount.withdraw(1000.0,d2);

            assertEquals(100.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

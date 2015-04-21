package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void test_statement_generation(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void test_one_account(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void test_two_accounts(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void test_three_accounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void transfer_within_accounts() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openAccount(checkingAccount);
        bill.openAccount(savingsAccount);

        checkingAccount.deposit(1500.0);
        savingsAccount.deposit(3000.0);

        checkingAccount.transfer(savingsAccount, 1500.0);


        assertEquals("Statement for Bill\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,500.00\n" +
                "  withdrawal $1,500.00\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $3,000.00\n" +
                "  deposit $1,500.00\n" +
                "Total $4,500.00\n" +
                "\n" +
                "Total In All Accounts $4,500.00", bill.getStatement());
    }

    @Test
    public void test_overdraft_transfer() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openAccount(checkingAccount);
        bill.openAccount(savingsAccount);

        checkingAccount.deposit(1500.0);

        try {
            checkingAccount.transfer(savingsAccount, 3000.0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "amount must be less than total amount in account");
        }

    }
}

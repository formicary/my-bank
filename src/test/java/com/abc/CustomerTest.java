package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test //Test customer statement generation
    public void testApp() {

        Account checkingAccount = new Account("iban1", Account.CHECKING);
        Account savingsAccount = new Account("iban2", Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(BigDecimal.valueOf(100.0));
        savingsAccount.deposit(BigDecimal.valueOf(4000.0));
        savingsAccount.withdraw(BigDecimal.valueOf(200.0));

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account [iban1]\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account [iban2]\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account("iban", Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account("iban1", Account.SAVINGS));
        oscar.openAccount(new Account("iban2", Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void transfer() {
        String ibanWithdrawAccount = "123";
        String ibanDepositAccount = "456";
        Account withdrawAccount = new Account(ibanWithdrawAccount, Account.CHECKING);
        withdrawAccount.deposit(BigDecimal.valueOf(100));
        Account depositAccount = new Account(ibanDepositAccount, Account.CHECKING);
        depositAccount.deposit(BigDecimal.valueOf(25));

        Customer customer = new Customer("John Doe").openAccount(withdrawAccount).openAccount(depositAccount);

        customer.transfer(ibanWithdrawAccount, ibanDepositAccount, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(50), withdrawAccount.sumTransactions());
        assertEquals(BigDecimal.valueOf(75), depositAccount.sumTransactions());
    }

    @Test
    public void transfer_negativeNumberThrowsException() {
        String ibanWithdrawAccount = "123";
        String ibanDepositAccount = "456";
        Account withdrawAccount = new Account(ibanWithdrawAccount, Account.CHECKING);
        withdrawAccount.deposit(BigDecimal.valueOf(100));
        Account depositAccount = new Account(ibanDepositAccount, Account.CHECKING);
        depositAccount.deposit(BigDecimal.valueOf(25));

        Customer customer = new Customer("John Doe").openAccount(withdrawAccount).openAccount(depositAccount);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("amount for transfer must be greater than zero");

        customer.transfer(ibanWithdrawAccount, ibanDepositAccount, BigDecimal.valueOf(-50));
    }

    @Test
    public void transfer_customerMustOwnWithdrawalAccount() {
        String ibanWithdrawAccount = "123";
        String ibanDepositAccount = "456";
        Account withdrawAccount = new Account(ibanWithdrawAccount, Account.CHECKING);
        withdrawAccount.deposit(BigDecimal.valueOf(100));
        Account depositAccount = new Account(ibanDepositAccount, Account.CHECKING);
        depositAccount.deposit(BigDecimal.valueOf(25));

        Customer customer = new Customer("John Doe").openAccount(withdrawAccount).openAccount(depositAccount);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("account does not exist");

        customer.transfer("nonExistingAccount", ibanDepositAccount, BigDecimal.valueOf(50));
    }

    @Test
    public void transfer_customerMustOwnDepositAccount() {
        String ibanWithdrawAccount = "123";
        String ibanDepositAccount = "456";
        Account withdrawAccount = new Account(ibanWithdrawAccount, Account.CHECKING);
        withdrawAccount.deposit(BigDecimal.valueOf(100));
        Account depositAccount = new Account(ibanDepositAccount, Account.CHECKING);
        depositAccount.deposit(BigDecimal.valueOf(25));

        Customer customer = new Customer("John Doe").openAccount(withdrawAccount).openAccount(depositAccount);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("account does not exist");

        customer.transfer(ibanWithdrawAccount, "nonExistingAccount", BigDecimal.valueOf(50));
    }

}

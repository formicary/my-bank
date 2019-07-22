package com.abc;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        john.openAccount(new Account(Account.MAXI_SAVINGS));

        Customer lukas = new Customer("Lukas");
        lukas.openAccount(new Account(Account.CHECKING));
        lukas.openAccount(new Account(Account.SAVINGS));
        lukas.openAccount(new Account(Account.MAXI_SAVINGS));
        bank.addCustomer(john);
        bank.addCustomer(lukas);

        assertEquals("Customer Summary\n - John (3 accounts)\n - Lukas (3 accounts)", bank.customerSummary());
    }

    @Test
    public void addFlatRatePerAnnum() {
        Bank bank = new Bank();
        Customer john = new Customer("John");

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        john.openAccount(checkingAccount);
        john.openAccount(savingsAccount);
        john.openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(200.0);
        maxiSavingsAccount.deposit(500.0);

        // Calculation of flat rate for the first year
        bank.addFlatRatePerAnnum(john);

        assertEquals("Statement for John\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $0.10\n" +
                "Total $100.10\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $200.00\n" +
                "  deposit $0.20\n" +
                "Total $200.20\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $500.00\n" +
                "  deposit $10.00\n" +
                "Total $510.00\n" +
                "\n" +
                "Total In All Accounts $810.30", john.getStatement());

        // Calculation of flat rate for the second year
        bank.addFlatRatePerAnnum(john);

        assertEquals("Statement for John\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $0.10\n" +
                "  deposit $0.10\n" +
                "Total $100.20\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $200.00\n" +
                "  deposit $0.20\n" +
                "  deposit $0.20\n" +
                "Total $200.40\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $500.00\n" +
                "  deposit $10.00\n" +
                "  deposit $10.20\n" +
                "Total $520.20\n" +
                "\n" +
                "Total In All Accounts $820.80", john.getStatement());
    }

    @Test
    public void addInterestRateDaily_lastWithdrawIsInLastTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(1600.0);
        maxiSavingsAccount.withdraw(100.0);

        // Calculation of interest rate for first day
        bank.addInterestRateDaily(bill);

        assertEquals("Statement for Bill\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,600.00\n" +
                "  withdrawal $100.00\n" +
                "Total $1,500.00\n" +
                "\n" +
                "Total In All Accounts $1,500.00", bill.getStatement());

        // Calculation of interest rate for second day
        maxiSavingsAccount.withdraw(100.0);
        bank.addInterestRateDaily(bill);

        assertEquals("Statement for Bill\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,600.00\n" +
                "  withdrawal $100.00\n" +
                "  withdrawal $100.00\n" +
                "Total $1,400.00\n" +
                "\n" +
                "Total In All Accounts $1,400.00", bill.getStatement());
    }

    @Test
    public void addInterestRateDaily_lastWithdrawIsNotInLastTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(1500.0);
        maxiSavingsAccount.withdraw(100.0);

        for (Transaction trans : maxiSavingsAccount.transactions) {
            trans.setTransactionDate(generateDate("01/07/2019"));
        }

        // Calculation of interest rate for first day
        bank.addInterestRateDaily(bill);

        assertEquals("Statement for Bill\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,500.00\n" +
                "  withdrawal $100.00\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "  deposit $0.11\n" +
                "Total $1,402.34\n" +
                "\n" +
                "Total In All Accounts $1,402.34", bill.getStatement());
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

    @Test(expected = IllegalArgumentException.class)
    public void checkingAccount_depositIsLessThenZero() throws Exception {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(-100.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingAccount_withdrawIsLessThenZero() throws Exception {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.withdraw(-100.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noTransactions() throws Exception {
        Account checkingAccount = new Account(Account.CHECKING);
        List<Transaction> transactions = new ArrayList<Transaction>();
        checkingAccount.isLastWithdrawInLastTenDays(transactions);
    }

    @Test
    public void savingsAccount_moreThenThousand() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount_lessThenThousand() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(500.0);

        assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount_lastWithdrawInLastTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(1600.0);
        maxiSavingsAccount.withdraw(100.0);

        assertEquals(20.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount_lastWithdrawIsNotInLastTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(1600.0);
        maxiSavingsAccount.withdraw(100.0);

        for (Transaction trans : maxiSavingsAccount.transactions) {
            trans.setTransactionDate(generateDate("01/07/2019"));
        }

        assertEquals(45.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void perAnnum() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);
        customer.openAccount(maxiSavingsAccount);
        bank.addCustomer(customer);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(100.0);
        maxiSavingsAccount.deposit(1500.0);

        bank.addFlatRatePerAnnum(customer);

        assertEquals("Statement for Bill\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $0.10\n" +
                "Total $100.10\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $100.00\n" +
                "  deposit $0.10\n" +
                "Total $100.10\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,500.00\n" +
                "  deposit $45.00\n" +
                "Total $1,545.00\n" +
                "\n" +
                "Total In All Accounts $1,745.20", customer.getStatement());
    }

    private LocalDate generateDate(String dateInString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return LocalDate.parse(dateInString, formatter);
    }

}

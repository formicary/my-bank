package com.abc;

import com.abc.account.Account;
import com.abc.account.AccountType;
import com.abc.bank.Bank;
import com.abc.customer.Customer;
import com.abc.account.AccountService;
import com.abc.bank.BankService;
import com.abc.customer.CustomerService;
import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionType;
import com.abc.util.DateProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private static AccountService accountService;
    private static CustomerService customerService;
    private static BankService bankService;
    private static DateProvider dateProvider;

    @BeforeClass
    public static void setup() {
        accountService = AccountService.getInstance();
        customerService = CustomerService.getInstance();
        bankService = BankService.getInstance();
        dateProvider = DateProvider.getInstance();
    }

    @Test
    public void customerSummary() {
        // given
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("John");
        customerService.openAccount(customer, checkingAccount);
        bankService.addCustomer(bank, customer);

        // when
        String customerSummary = bankService.customerSummary(bank);

        // than
        assertEquals("Customer Summary\n - John (1 account)", customerSummary);
    }

    @Test
    public void checkingAccount() {
        // given
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer customer = new Customer("Bill");
        customerService.openAccount(customer, checkingAccount);
        bankService.addCustomer(bank, customer);
        accountService.deposit(checkingAccount, 100.0);

        // when
        double totalInterestPaid = bankService.totalInterestPaid(bank);

        // than
        assertEquals(0.1, totalInterestPaid, DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        // given
        Bank bank = new Bank();
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Customer customer = new Customer("Bill");
        customerService.openAccount(customer, savingsAccount);
        bankService.addCustomer(bank, customer);
        accountService.deposit(savingsAccount, 1500.0);

        // when
        double totalInterestPaid = bankService.totalInterestPaid(bank);

        // than
        assertEquals(2.0, totalInterestPaid, DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        // given
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customerService.openAccount(customer, maxiSavingsAccount);
        bankService.addCustomer(bank, customer);
        accountService.deposit(maxiSavingsAccount, 3000.0);

        // when
        double totalInterestPaid = bankService.totalInterestPaid(bank);

        // than
        assertEquals(170.0, totalInterestPaid, DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountMoreThan10Days() {
        // given
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer customer = new Customer("Bill");
        customerService.openAccount(customer, maxiSavingsAccount);
        bankService.addCustomer(bank, customer);

        // when
        Transaction transaction = new Transaction(TransactionType.DEPOSIT,3000.0);
        transaction.setTransactionDate(dateProvider.addDaysToCurrentDate(-11));
        maxiSavingsAccount.getTransactions().add(transaction);

        // than
        double totalInterestPaid = bankService.totalInterestPaid(bank);
        assertEquals(178.5, totalInterestPaid, DOUBLE_DELTA);
    }

    @Test
    public void getFirstCustomer() {
        // given
        Bank bank = new Bank();
        Customer firstCustomer = new Customer("Bill");
        Customer secondCustomer = new Customer("John");
        bankService.addCustomer(bank, firstCustomer);
        bankService.addCustomer(bank, secondCustomer);

        // when
        String firstCustomerName = bankService.getFirstCustomerName(bank);

        // than
        assertEquals("Bill", firstCustomerName);
    }

    @Test
    public void getFirstCustomerNoCustomers() {
        // given
        Bank bank = new Bank();

        // when
        String firstCustomerName = bankService.getFirstCustomerName(bank);

        // than
        assertNull("First customer name should be null", firstCustomerName);
    }

}

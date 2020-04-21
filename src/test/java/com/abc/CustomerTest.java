package com.abc;

import com.abc.account.Account;
import com.abc.account.AccountType;
import com.abc.customer.Customer;
import com.abc.account.AccountService;
import com.abc.customer.CustomerService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;

    private static AccountService accountService;
    private static CustomerService customerService;

    @BeforeClass
    public static void setup() {
        accountService = AccountService.getInstance();
        customerService = CustomerService.getInstance();
    }

    @Test //Test customer statement generation
    public void testApp() {
        // given
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer customer = new Customer("Henry");
        customerService.openAccount(customer, checkingAccount);
        customerService.openAccount(customer, savingsAccount);

        accountService.deposit(checkingAccount, 100.0);
        accountService.deposit(savingsAccount, 4000.0);
        accountService.withdraw(savingsAccount, 200.0);

        // when
        String statement = customerService.getStatement(customer);

        // than
        assertEquals("Statement for Henry\n" +
                "\n" + //
                "Checking Account\n" + //
                "  deposit $100.00\n" + //
                "Total $100.00\n" + //
                "\n" + //
                "Savings Account\n" + //
                "  deposit $4,000.00\n" + //
                "  withdrawal $200.00\n" + //
                "Total $3,800.00\n" + //
                "\n" + //
                "Total In All Accounts $3,900.00", statement);
    }

    @Test
    public void testOneAccount() {
        // given
        Customer customer = new Customer("Oscar");
        Account savingsAccount = new Account(AccountType.SAVINGS);
        customerService.openAccount(customer, savingsAccount);

        // when
        int numberOfAccounts = customerService.getNumberOfAccounts(customer);

        //than
        assertEquals(1, numberOfAccounts);
    }

    @Test
    public void testTwoAccounts() {
        // given
        Customer customer = new Customer("Oscar");
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account checkingAccount = new Account(AccountType.CHECKING);
        customerService.openAccount(customer, savingsAccount);
        customerService.openAccount(customer, checkingAccount);

        // when
        int numberOfAccounts = customerService.getNumberOfAccounts(customer);

        //than
        assertEquals(2, numberOfAccounts);
    }

    @Test
    public void testThreeAccounts() {
        // given
        Customer customer = new Customer("Oscar");
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account maxiSavingAccount = new Account(AccountType.MAXI_SAVINGS);
        customerService.openAccount(customer, savingsAccount);
        customerService.openAccount(customer, checkingAccount);
        customerService.openAccount(customer, maxiSavingAccount);

        // when
        int numberOfAccounts = customerService.getNumberOfAccounts(customer);

        //than
        assertEquals(3, numberOfAccounts);
    }

    @Test
    public void transfer() {
        // given
        Account sourceAccount = new Account(AccountType.CHECKING);
        Account destinationAccount = new Account(AccountType.SAVINGS);

        Customer customer = new Customer("Henry");
        customerService.openAccount(customer, sourceAccount);
        customerService.openAccount(customer, destinationAccount);

        accountService.deposit(sourceAccount, 1000.0);

        // when
        accountService.transfer(sourceAccount, destinationAccount, 600.0);

        // than
        double balanceOfSourceAccount = accountService.sumTransactions(sourceAccount);
        double balanceOfDestinationAccount = accountService.sumTransactions(destinationAccount);

        assertEquals(400.0, balanceOfSourceAccount, DOUBLE_DELTA);
        assertEquals(600.0, balanceOfDestinationAccount, DOUBLE_DELTA);
    }

    @Test
    public void transferAmountBiggerThanBalance() {
        // given
        Account sourceAccount = new Account(AccountType.CHECKING);
        Account destinationAccount = new Account(AccountType.SAVINGS);

        Customer customer = new Customer("Henry");
        customerService.openAccount(customer, sourceAccount);
        customerService.openAccount(customer, destinationAccount);

        accountService.deposit(sourceAccount, 100.0);

        // when
        try {
            accountService.transfer(sourceAccount, destinationAccount, 600.0);
        } catch (IllegalArgumentException ex) {
            // than
            assertEquals("Amount must be greater than the balance of the source account. Balance: 100.0, "
                    + "amount: 600.0", ex.getMessage());
        }

        double balanceOfSourceAccount = accountService.sumTransactions(sourceAccount);
        double balanceOfDestinationAccount = accountService.sumTransactions(destinationAccount);

        assertEquals(100.0, balanceOfSourceAccount, DOUBLE_DELTA);
        assertEquals(0.0, balanceOfDestinationAccount, DOUBLE_DELTA);
    }
}

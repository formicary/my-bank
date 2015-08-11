package test.com.abc;


import org.junit.Test;
import com.abc.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Calendar calendar = Calendar.getInstance();

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        calendar.add(Calendar.DATE,-1);
        checkingAccount.deposit(100.0, calendar.getTime());

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        calendar.add(Calendar.DATE,-10);
        checkingAccount.deposit(1500.0,calendar.getTime());

        assertEquals(20.15, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        calendar.add(Calendar.DATE,-1);
        checkingAccount.deposit(3000.0,calendar.getTime());

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    @Test
    public void maxi_savings_account_withdraw() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        calendar.add(Calendar.DATE,-10);
        checkingAccount.deposit(3000.0,calendar.getTime());
        calendar.add(Calendar.DATE,5);
        checkingAccount.withdraw(1000.0,calendar.getTime());

        assertEquals(1610.4, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}

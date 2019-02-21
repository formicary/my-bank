package com.abc;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Calendar;
import java.util.UUID;



public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;
    private Date date;
    private Date oldDate;
    private Bank bank;
    private Customer john;

    @BeforeEach
    public void setUp(){
        date = DateProvider.getInstance().now();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -10);
        oldDate = cal.getTime();

        bank = new Bank();
        john = new Customer("John");

    }

    @Test
    public void customerSummary() {
        john.openAccount(new CheckingAccount(UUID.randomUUID()));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Nested
    public class TotalInterestRateTest{
        @Test
        public void checking_account() {
            Account checkingAccount = new CheckingAccount(UUID.randomUUID());
            john.openAccount(checkingAccount);
            bank.addCustomer(john);

            checkingAccount.deposit(100.0, date);

            assertEquals(0.1, bank.totalInterestPaid(Account.YEARLY), DOUBLE_DELTA);
        }

        @Test
        public void savings_account() {
            SavingsAccount savingsAccount = new SavingsAccount(UUID.randomUUID());
            john.openAccount(savingsAccount);
            bank.addCustomer(john);

            savingsAccount.deposit(1500.0, date);

            assertEquals(2.0, bank.totalInterestPaid(Account.YEARLY), DOUBLE_DELTA);
        }

        @Test
        public void maxi_savings_account() {
            MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount(UUID.randomUUID());
            john.openAccount(maxiSavingsAccount);
            bank.addCustomer(john);

            maxiSavingsAccount.deposit(3000.0, date);
            maxiSavingsAccount.withdraw(1000.0, oldDate);

            assertEquals(100.0, bank.totalInterestPaid(Account.YEARLY), DOUBLE_DELTA);
        }
    }

    @Test
    public void daily_interest_rates(){
        CheckingAccount checkingAccount = new CheckingAccount(UUID.randomUUID());
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(3650.0, date);

        assertEquals(0.01, bank.totalInterestPaid(Account.DAILY), DOUBLE_DELTA);
    }
}

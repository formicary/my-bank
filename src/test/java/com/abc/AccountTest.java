package com.abc;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    private static Customer henry;
    private static Account checking;

    @BeforeClass
    public static void setup() {
        henry = new Customer("Henry");
        henry.openAccount(new Account(AccountTypes.CHECKING));
        checking = henry.getAccounts().get(0);
    }

    @Test
    public void testDeposit() {
        checking.deposit(200);
        assertEquals(200, checking.getBalance(), DOUBLE_DELTA);
    }

    // Demonstrates customer can be overdrawn
    @Test
    public void testWithdraw() {
        checking.withdraw(500);
        assertEquals(-500, checking.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned(){
        checking.deposit(500);
        assertEquals(0.5, checking.interestEarned(), DOUBLE_DELTA);

        Account saving = new Account(AccountTypes.SAVINGS);
        saving.deposit(500);
        assertEquals(0.5, saving.interestEarned(), DOUBLE_DELTA);
        saving.deposit(1000);
        assertEquals(2, saving.interestEarned(), DOUBLE_DELTA);

        Account maxi = new Account(AccountTypes.MAXI_SAVINGS);
        maxi.deposit(500);
        assertEquals(10, maxi.interestEarned(), DOUBLE_DELTA);
        maxi.deposit(1000);
        assertEquals(45, maxi.interestEarned(), DOUBLE_DELTA);
        maxi.deposit(1000);
        assertEquals(120, maxi.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void checkLastTransaction() {
        double interest = 0;
        Account account = new Account(AccountTypes.MAXI_SAVINGS);

        account.deposit(5000);
        account.withdraw(700);
        account.withdraw(700);
        account.deposit(1700);

        Date today = DateProvider.getInstance().now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR, -10);
        Date tenDays = cal.getTime();

        List<Transaction> transactions = account.getTransactions();
        Transaction last = null;
        for (int i = transactions.size() - 1; i >= 0; i-- ) {
            if (transactions.get(i).getAmount() < 0) {
                last = transactions.get(i);
                break;
            }
        }
//        cal.add(Calendar.DAY_OF_YEAR, -5);
//        last.setTransactionDate(cal.getTime());
        if (last.getTransactionDate().before(tenDays) ) {
            interest = 0.05;
        } else if (last.getTransactionDate().after(tenDays)) {
            interest = 0.001;
        }
        assertEquals(0.001, interest, DOUBLE_DELTA);
//        assertEquals(0.05, interest, DOUBLE_DELTA);
    }
}

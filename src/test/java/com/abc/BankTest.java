package com.abc;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;


    @Test
    public void TestGetFirstCustomer_ShouldBeSame() {
        String[] customerNames = {"Eric", "Michael", "Don", "Walter", "Skyler"};
        Collections.shuffle(Arrays.asList(customerNames), new Random(42));

        Bank bank = new Bank();

        List<Customer> customers = new ArrayList<>();

        for (String name : customerNames ) {
            Customer customer = new Customer(name);

            customers.add(customer);
            bank.addCustomer(customer);
        }
        assertEquals(customers.get(0), bank.getFirstCustomer());
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void TestGetFirstCustomer_NoCustomers() {
        Bank bank = new Bank();
        bank.getFirstCustomer();
    }

    @Test (expected=UnsupportedOperationException.class)
    public void TestBankAddCustomer_SameCustomer() {
        Bank bank = new Bank();
        Customer mark = new Customer("Mark");

        bank.addCustomer(mark);
        bank.addCustomer(mark);
    }

    @Test
    public void TestBankAddCustomer_TwoCustomers_ShouldBeCorrect() {
        Bank bank = new Bank();
        Customer gosho = new Customer("Gosho");
        Customer pesho = new Customer("Pesho");

        bank.addCustomer(gosho);
        bank.addCustomer(pesho);

        assertEquals(2, bank.getNumberOfCustomers());
    }

    @Test
    public void TestTotalInterestPaid_ShouldBeCorrect() {
        Bank bank = new Bank();

        Customer bob = new Customer("Bob");
        Customer steve = new Customer("Steve");

        bank.addCustomer(bob);
        bank.addCustomer(steve);

        Account bob_checkingAccount = new Account(Account.CHECKING);
        Account bob_savingsAccount = new Account(Account.SAVINGS);
        Account steve_maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        bob.openAccount(bob_checkingAccount);
        bob.openAccount(bob_savingsAccount);
        steve.openAccount(steve_maxiSavingsAccount);

        bob_checkingAccount.deposit(1000);
        bob_savingsAccount.deposit(2250);
        steve_maxiSavingsAccount.deposit(1000);

        double annualInterest = 0.5 + 3.5 + 50.0;

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int days = calendar.get(Calendar.DAY_OF_YEAR);
        int daysInYear = calendar.isLeapYear(calendar.getWeekYear()) ? 366 : 365;

        double expectedInterest = Double.parseDouble(Account.decimalFormatter.format(annualInterest / daysInYear * days));

        String a = bank.totalInterestPaid();

        assertEquals(expectedInterest, bank.getTotalInterestPaid(), 2);
    }

}

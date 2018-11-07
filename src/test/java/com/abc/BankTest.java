package com.abc;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;
import com.abc.Account.MaxiSavingsAccount;
import com.abc.Account.SavingsAccount;
import com.abc.Utilities.TestingDateProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    TestingDateProvider provider;

    @Before
    public void setUp(){
        provider = TestingDateProvider.getInstance();
        DateProvider.setInstance(provider);
    }

    @Test
    public void customerSummaryTest() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.toString());
        john.openAccount(new SavingsAccount());
        assertEquals("Customer Summary\n - John (2 accounts)", bank.toString());
    }

    @Test
    public void multipleCustomersAndAccountsTest(){
        Bank bank = new Bank();
        Random rand = new Random();
        double total = 0.0;
        for(int i = 0; i < 200; i++){
            Customer customer = new Customer("Customer-"+i);
            bank.addCustomer(customer);
        }
        assertEquals(200, bank.getTotalCustomers());
        for(int i = 0; i < bank.getTotalCustomers(); i++){
            int numAccounts = rand.nextInt(9)+1;
            Customer customer = bank.getCustomer(i);
            for(int r = 0; r < numAccounts; r++){
                Account account;
                int accountType = rand.nextInt(3);
                switch (accountType){
                    default:
                        account = new CheckingAccount();
                        break;
                    case 1:
                        account = new SavingsAccount();
                        break;
                    case 2:
                        account = new MaxiSavingsAccount();
                        break;
                }
                customer.openAccount(account);
                int deposit = rand.nextInt(20000)+1000;
                account.deposit(deposit);
                total+=deposit;
            }
            assertEquals(numAccounts, customer.getNumberOfAccounts());
        }
        for(int i = 0; i < bank.getTotalCustomers(); i++){
            Customer customer = bank.getCustomer(i);
            for(int r = 0; r < customer.getNumberOfAccounts(); r++){
                Account account = customer.getAccount(r);
                int withdraw = rand.nextInt((int) account.getBalance()-500) +1;
                account.withdraw(withdraw);
                total-=withdraw;
            }
        }
        assertEquals(total, bank.getTotalBalance(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaidTest(){
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar");
        Customer bill = new Customer("Bill");
        bank.addCustomer(oscar);
        bank.addCustomer(bill);
        Account oscarAcc1 = new SavingsAccount();
        Account oscarAcc2 = new CheckingAccount();
        Account billAcc1 = new MaxiSavingsAccount();
        Account billAcc2 = new SavingsAccount();
        oscar.openAccount(oscarAcc1)
                .openAccount(oscarAcc2);
        bill.openAccount(billAcc1)
                .openAccount(billAcc2);
        oscarAcc1.deposit(3000.0);
        oscarAcc2.deposit(3000.0);
        billAcc1.deposit(3000.0);
        billAcc2.deposit(3000.0);
        bank.payInterestToAllCustomers();

        Transaction t1 = oscarAcc1.getLastTransaction();
        Transaction t2 = oscarAcc2.getLastTransaction();
        Transaction t3 = billAcc1.getLastTransaction();
        Transaction t4 = billAcc2.getLastTransaction();

        assertEquals(16.0/365.0,bank.totalInterestPaid(), DOUBLE_DELTA);
        assertEquals(5.0/365.0, t1.amount, DOUBLE_DELTA);
        assertEquals(3.0/365.0, t2.amount, DOUBLE_DELTA);
        assertEquals(3.0/365.0, t3.amount, DOUBLE_DELTA);
        assertEquals(5.0/365.0, t4.amount, DOUBLE_DELTA);
    }

    @Test
    public void checkingAccountTest() {
        Bank bank = new Bank();
        Account account = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(account);
        bank.addCustomer(bill);

        account.deposit(100.0);
        account.accrueDailyInterest();

        assertEquals(0.1/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountTest() {
        Bank bank = new Bank();
        Account account = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(account));

        account.deposit(1500.0);
        account.accrueDailyInterest();

        assertEquals(2.0/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountTest() {
        Bank bank = new Bank();
        Account account = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(account));

        provider.setTestingModeOn(10);
        account.deposit(3000.0);
        provider.setTestingModeOff();
        account.accrueDailyInterest();

        assertEquals(150.0/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountLowerRateTest() {
        Bank bank = new Bank();
        Account account = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(account));

        provider.setTestingModeOn(5);
        account.deposit(3000.0);
        provider.setTestingModeOff();
        account.accrueDailyInterest();

        assertEquals(3.0/365, bank.totalInterestPaid(), DOUBLE_DELTA);
    }


    @Test
    public void getFirstCustomerTest() {
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar");
        bank.addCustomer(oscar);
        bank.addCustomer(new Customer("Bill"));
        assertEquals("Oscar", bank.getFirstCustomer());
    }

    @Test
    public void getCustomerTestTest(){
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar");
        bank.addCustomer(oscar);
        bank.addCustomer(new Customer("Bill"));
        bank.addCustomer(new Customer("John"));
        assertEquals("Bill",bank.getCustomer(1).getName());
    }

    @Test
    public void getTotalCustomersTest(){
        Bank bank = new Bank();
        bank.addCustomer(new Customer("Oscar"));
        bank.addCustomer(new Customer("Bill"));
        bank.addCustomer(new Customer("John"));
        assertEquals(3,bank.getTotalCustomers());
    }

    @Test
    public void getTotalBalanceTest(){
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar");
        Customer bill = new Customer("Bill");
        bank.addCustomer(oscar);
        bank.addCustomer(bill);
        Account oscarAcc1 = new SavingsAccount();
        Account oscarAcc2 = new CheckingAccount();
        Account billAcc1 = new MaxiSavingsAccount();
        Account billAcc2 = new SavingsAccount();
        oscar.openAccount(oscarAcc1)
                .openAccount(oscarAcc2);
        bill.openAccount(billAcc1)
                .openAccount(billAcc2);
        oscarAcc1.deposit(3000.0);
        oscarAcc2.deposit(3000.0);
        billAcc1.deposit(3000.0);
        billAcc2.deposit(3000.0);

        assertEquals(12000.0, bank.getTotalBalance(), DOUBLE_DELTA);
    }
}

package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;

public class BankTest {
    //private static final BigDecimal DOUBLE_DELTA = new BigDecimal(1e-15);
    private static final double DOUBLE_DELTA =  1e-15;


    @Test
    public void oneCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        BigDecimal tenThousand = new BigDecimal(100000);
        checkingAccount.deposit(tenThousand);





        Assert.assertEquals( 0.2739726778009187, bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void twoCheckingAccounts() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Account checkingAccount2 = new CheckingAccount();

        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        Customer mandy = new Customer("Mandy").openAccount(checkingAccount2);

        bank.addCustomer(bill);
        bank.addCustomer(mandy);

        BigDecimal tenThousand = new BigDecimal(10000);
        checkingAccount.deposit(tenThousand);
        checkingAccount2.deposit(tenThousand);


        Assert.assertEquals((0.05479459560893225), bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void oneSavingsAccount() {
        Bank bank = new Bank();
        Account savings = new SavingsAccount();


        Customer bill = new Customer("Bill").openAccount(savings);


        bank.addCustomer(bill);


        BigDecimal oneThousandFiveHundred = new BigDecimal(1500);
        savings.deposit(oneThousandFiveHundred);



        Assert.assertEquals((0.00547945205479452), bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void numberOfAccounts() {
        Bank bank = new Bank();



        Account savings = new SavingsAccount();
        Account savings2 = new SavingsAccount();

        Customer bill = new Customer("Bill").openAccount(savings);

        Customer mandy = new Customer("Mandy").openAccount(savings2);

        bank.addCustomer(bill);
        bank.addCustomer(mandy);

        BigDecimal oneThousandFiveHundred = new BigDecimal(1500);
        savings.deposit(oneThousandFiveHundred);
        savings2.deposit(oneThousandFiveHundred);


        Assert.assertEquals("Customer Summary\n" +
                " - Bill (1 account)\n" +
                " - Mandy (1 account)", bank.getAllCustomerSummary());
    }


}

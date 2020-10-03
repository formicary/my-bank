package com.abc.service;

import com.abc.entity.impl.AccountImpl;
import com.abc.entity.Customer;
import com.abc.entity.AccountType;
import com.abc.entity.impl.CustomerImpl;
import com.abc.service.CustomerStatementService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerStatementServiceTest {

    private static Customer customer;
    private static AccountImpl currentAccount;
    private static AccountImpl savingsAccount;
    private static AccountImpl maxiSavingsAccount;


    @Before
    public void setup(){
        customer = new CustomerImpl("Customer A");

        currentAccount = new AccountImpl(customer, AccountType.CURRENT, "123");
        savingsAccount = new AccountImpl(customer, AccountType.SAVINGS, "124");
        maxiSavingsAccount = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "125");

    }


    @Test
    public void testSingleCurrentDepositStatement(){

        customer.deposit( new BigDecimal("10.99"), currentAccount);

        assertEquals("Statement for Customer A\n" +
                "\n" +
                "CURRENT: \n" +
                "  deposit $10.99\n" +
                "Total $10.99\n" +
                "\n" +
                "SAVINGS: \n" +
                "Total $0.00\n" +
                "\n" +
                "MAXI_SAVINGS: \n" +
                "Total $0.00\n" +
                "\n" +
                "Total In all Accounts $10.99", CustomerStatementService.generateStatement(customer));
    }

    @Test
    public void testSingleSavingsDepositStatement(){

        customer.deposit(new BigDecimal("10.99"), savingsAccount);

        assertEquals("Statement for Customer A\n" +
                "\n" +
                "CURRENT: \n" +
                "Total $0.00\n" +
                "\n" +
                "SAVINGS: \n" +
                "  deposit $10.99\n" +
                "Total $10.99\n" +
                "\n" +
                "MAXI_SAVINGS: \n" +
                "Total $0.00\n" +
                "\n" +
                "Total In all Accounts $10.99", CustomerStatementService.generateStatement(customer));
    }

    @Test
    public void testSingleMaxiSavingsDepositStatement(){

        customer.deposit(new BigDecimal("10.99"), maxiSavingsAccount);

        assertEquals("Statement for Customer A\n" +
                "\n" +
                "CURRENT: \n" +
                "Total $0.00\n" +
                "\n" +
                "SAVINGS: \n" +
                "Total $0.00\n" +
                "\n" +
                "MAXI_SAVINGS: \n" +
                "  deposit $10.99\n" +
                "Total $10.99\n" +
                "\n" +
                "Total In all Accounts $10.99", CustomerStatementService.generateStatement(customer));
    }

    @Test
    public void testDepositsToAlLAccountsStatement(){

        customer.deposit(new BigDecimal("10.99"), currentAccount);
        customer.deposit(new BigDecimal("10.99"), savingsAccount );
        customer.deposit(new BigDecimal("10.99"), maxiSavingsAccount);

        assertEquals("Statement for Customer A\n" +
                "\n" +
                "CURRENT: \n" +
                "  deposit $10.99\n" +
                "Total $10.99\n" +
                "\n" +
                "SAVINGS: \n" +
                "  deposit $10.99\n" +
                "Total $10.99\n" +
                "\n" +
                "MAXI_SAVINGS: \n" +
                "  deposit $10.99\n" +
                "Total $10.99\n" +
                "\n" +
                "Total In all Accounts $32.97", CustomerStatementService.generateStatement(customer));
    }
}

package com.abc;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.entity.impl.AccountType;
import com.abc.entity.impl.CustomerImpl;
import com.abc.service.CustomerStatementService;
import com.abc.service.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CustomerStatementServiceTest {

    private static Customer customer;
    private static CustomerStatementService statementService;
    private static TransactionManager transactionManager;
    private static Account currentAccount;
    private static Account savingsAccount;
    private static Account maxiSavingsAccount;


    @Before
    public void setup(){
        currentAccount = new Account(AccountType.CURRENT);
        savingsAccount = new Account(AccountType.SAVINGS);
        maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);

        customer = new CustomerImpl("Customer A");
        customer.addAccount(currentAccount);
        customer.addAccount(savingsAccount);
        customer.addAccount(maxiSavingsAccount);

        statementService = new CustomerStatementService(customer);
        transactionManager = new TransactionManager(customer);
    }


    @Test
    public void testSingleCurrentDepositStatement(){

        transactionManager.deposit(currentAccount, new BigDecimal("10.99"));

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
                "Total In all Accounts $10.99", statementService.customerStatement());
    }

    @Test
    public void testSingleSavingsDepositStatement(){

        transactionManager.deposit(savingsAccount, new BigDecimal("10.99"));

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
                "Total In all Accounts $10.99", statementService.customerStatement());
    }

    @Test
    public void testSingleMaxiSavingsDepositStatement(){

        transactionManager.deposit(maxiSavingsAccount, new BigDecimal("10.99"));

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
                "Total In all Accounts $10.99", statementService.customerStatement());
    }

    @Test
    public void testDepositsToAlLAccountsStatement(){

        transactionManager.deposit(currentAccount, new BigDecimal("10.99"));
        transactionManager.deposit(savingsAccount, new BigDecimal("10.99"));
        transactionManager.deposit(maxiSavingsAccount, new BigDecimal("10.99"));

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
                "Total In all Accounts $32.97", statementService.customerStatement());
    }
}

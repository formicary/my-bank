package com.abc.users;

import com.abc.Bank;
import com.abc.accounts.Account;
import com.abc.accounts.Checking;
import com.abc.accounts.MaxiSavings;
import com.abc.accounts.Savings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @project MyBank
 */
@DisplayName("Testing Manager")
public class ManagerTest {

    private Manager mockManager;
    private Bank mockBank;
    private Customer cain, steve, paul;
    private Account savings, maxiSavings, checkingsOne, checkingsTwo;

    @BeforeEach
    public void init() {
        mockBank = new Bank();
        mockManager = new Manager("Bob", mockBank);

        cain = new Customer("Cain");
        steve = new Customer("Steve");
        paul = new Customer("Paul");

        mockBank.addCustomer(cain);
        mockBank.addCustomer(steve);
        mockBank.addCustomer(paul);

        savings = new Savings();
        maxiSavings = new MaxiSavings();
        checkingsOne = new Checking();
        checkingsTwo = new Checking();

        cain.openAccount(savings);
        steve.openAccount(maxiSavings);
        steve.openAccount(checkingsOne);
        paul.openAccount(checkingsTwo);
    }

    @Test
    @DisplayName("When a summary of customers is requested, it should output their names and number of accounts owned")
    public void testGetCustomerSummary() {
        String expected = "Customer Summary\n" +
                " - Cain (1 account)\n" +
                " - Steve (2 accounts)\n" +
                " - Paul (1 account)";

        assertEquals(expected, mockManager.getCustomerSummary());
    }

    @Test
    @DisplayName("When a request for total interest paid by bank, a sum of all interest paid to customers should output")
    public void testTotalInterestPaid() {
        savings.deposit(1000);
        savings.updateAccount(savings.getDateOfLastUpdate().plusDays(1));
        maxiSavings.deposit(1000);
        maxiSavings.updateAccount(maxiSavings.getDateOfLastUpdate().plusDays(1));
        checkingsOne.deposit(1000);
        checkingsOne.updateAccount(checkingsOne.getDateOfLastUpdate().plusDays(1));
        checkingsTwo.deposit(1000);
        checkingsTwo.updateAccount(checkingsTwo.getDateOfLastUpdate().plusDays(1));

        double expected = 53.0;

        assertEquals(expected, mockManager.getTotalInterestPaid());

    }


}

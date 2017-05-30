package com.abc.concurrencyTests;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.exceptions.*;
import com.abc.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class concurrencyTests {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;

    @Before
    public void initializeBank() {
        bank = new Bank("Lloyds");
    }

    @Test
    public void concurrentDepositAndWithdrawTest() {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        Account account = TestUtils.createCheckingAccount(john);

        CompletableFuture<?>[] completableFutures = new CompletableFuture[10];
        int count = 0;

        for (int i = 0; i < 10; i++) {

            completableFutures[count] = CompletableFuture.supplyAsync(() -> {
                try {
                    return TestUtils.depositMoney(account, 1000);
                } catch (NonPositiveAmountException e) {
                    e.printStackTrace();
                }
                return null;
            }).thenRunAsync(() -> {
                try {
                    TestUtils.withdrawMoney(account, 1000);
                } catch (NonPositiveAmountException e) {
                    e.printStackTrace();
                } catch (ExceedsNegativeBalanceException e) {
                    e.printStackTrace();
                }

            });
            count++;
        }
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(completableFutures);

        try {
            allFuturesResult.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertEquals("Withdrawl the whole amount from the account", 0.0, account.getBalance());
    }


    @Test
    public void concurrentDepositAndTransferTest() {
        Customer john = TestUtils.createCustomer(bank, "Lloyds");
        Account account = TestUtils.createCheckingAccount(john);
        TestUtils.createCheckingAccount(john);

        CompletableFuture<?>[] completableFutures = new CompletableFuture[10];
        int count = 0;
        for (int i = 0; i < 10; i++) {

            completableFutures[count] = CompletableFuture.supplyAsync(() -> {
                try {
                    return TestUtils.depositMoney(account, 1000);
                } catch (NonPositiveAmountException e) {
                    e.printStackTrace();
                }
                return null;
            }).thenRunAsync(() -> {
                try {
                    TestUtils.transferMoney(bank, account, 1, 2, 1000);
                } catch (ExceedsNegativeBalanceException | CustomerNotExistException | AccountNotExistException | IdenticalAccountIDException | NonPositiveAmountException e) {
                    e.printStackTrace();
                }

            });
            count++;
        }
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(completableFutures);

        try {
            allFuturesResult.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertEquals("Transfer all the amount from account 1 to account 2", 0.0, account.getBalance());
    }

}

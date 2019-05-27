package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.intereststrategies.InterestStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class AccountMockitoTests {

    @Mock
    InterestStrategy interestStrategy;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void callsInterestBehaviorWithTransactionsList_WhenApplyDailyInterestCalled() {

        final List<Transaction> capturedTransactions = new ArrayList<>();

        // NOT IDEAL. We must capture the argument passed to the mock here because the list is mutated by the class under test immediately after.
        // Mockito will then think that the mock was called with the new mutated list and not the list actually passed, incorrectly failing a verify call.
        when(interestStrategy.getInterest(anyList())).thenAnswer(invocationOnMock -> {
            List<Transaction> argument = (List<Transaction>) invocationOnMock.getArguments()[0];
            capturedTransactions.addAll(argument);
            return DollarAmount.fromInt(10);
        });

        Account testAccount = new Account("testId", interestStrategy);

        Transaction transaction1 = new Transaction.Builder()
                .setDescription("Test")
                .setAmount(DollarAmount.fromDouble(51.6))
                .setType(Transaction.Type.INTEREST)
                .build();

        Transaction transaction2 = new Transaction.Builder()
                .setDescription("Test")
                .setAmount(DollarAmount.fromInt(10))
                .setType(Transaction.Type.INTEREST)
                .build();

        Transaction transaction3 = new Transaction.Builder()
                .setDescription("Test")
                .setAmount(DollarAmount.fromInt(12))
                .setType(Transaction.Type.DEPOSIT)
                .build();

        Transaction transaction4 = new Transaction.Builder()
                .setDescription("Test")
                .setAmount(DollarAmount.fromDouble(22.4))
                .setType(Transaction.Type.WITHDRAWAL)
                .build();

        // Apply list of transactions to account
        List<Transaction> transactionList = Stream.of(transaction1, transaction2, transaction3, transaction4).collect(Collectors.toList());
        transactionList.forEach(testAccount::applyTransaction);

        // Attempt to apply daily interest
        testAccount.applyDailyInterest();

        verify(interestStrategy, times(1)).getInterest(anyList());
        assertEquals(transactionList, capturedTransactions);
    }

}

package com.accenture;

import com.accenture.intereststrategies.FlatRateInterest;
import com.accenture.intereststrategies.MaxiSavingsInterestNoWithdrawals;
import com.accenture.intereststrategies.SavingsInterest;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class InterestStrategyTests {

    @Test
    public void FlatInterestRateReturnsCorrectValue() {

        FlatRateInterest flatRateInterest = FlatRateInterest.newInstance(0.001);

        Transaction deposit = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(100))
                .build();

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(deposit);


        BigDecimal expectedInterest = BigDecimal.valueOf(0.00027);
        DollarAmount actualInterest = flatRateInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }

    @Test
    public void FlatInterestRateReturnsCorrectValueWithMultipleTransactions () {

        FlatRateInterest flatRateInterest = FlatRateInterest.newInstance(0.001);

        Transaction transaction1 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(100))
                .build();

        Transaction transaction2 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .setAmount(DollarAmount.fromInt(120))
                .build();

        Transaction transaction3 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.WITHDRAWAL)
                .setAmount(DollarAmount.fromInt(34))
                .build();

        List<Transaction> transactionList = Stream.of(transaction1, transaction2, transaction3).collect(Collectors.toList());


        BigDecimal expectedInterest = BigDecimal.valueOf(0.00051);
        DollarAmount actualInterest = flatRateInterest.getInterest(transactionList);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }


    @Test
    public void SavingsInterestReturnCorrectValue_Bellow_1000() {

        SavingsInterest savingsInterest = new SavingsInterest();

        Transaction deposit = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(500))
                .build();

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(deposit);


        BigDecimal expectedInterest = BigDecimal.valueOf(0.00137);
        DollarAmount actualInterest = savingsInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }


    @Test
    public void SavingsInterestReturnCorrectValue_1000() {

        SavingsInterest savingsInterest = new SavingsInterest();

        Transaction deposit = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1000))
                .build();

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(deposit);


        BigDecimal expectedInterest = BigDecimal.valueOf(0.00274);
        DollarAmount actualInterest = savingsInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }


    @Test
    public void SavingsInterestReturnCorrectValue_1010() {

        SavingsInterest savingsInterest = new SavingsInterest();

        Transaction deposit = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1010))
                .build();

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(deposit);


        BigDecimal expectedInterest = BigDecimal.valueOf(0.00279);
        DollarAmount actualInterest = savingsInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }


    @Test
    public void SavingsInterestReturnCorrectValue_Multiple_Transaction() {

        SavingsInterest savingsInterest = new SavingsInterest();

        Transaction transaction1 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(100))
                .build();


        Transaction transaction2 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .setAmount(DollarAmount.fromInt(200))
                .build();

        Transaction transaction3 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.WITHDRAWAL)
                .setAmount(DollarAmount.fromInt(50))
                .build();

        Transaction transaction4 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(2000))
                .build();

        List<Transaction> transactions = Stream.of(transaction1, transaction2, transaction3, transaction4).collect(Collectors.toList());


        BigDecimal expectedInterest = BigDecimal.valueOf(0.00959);
        DollarAmount actualInterest = savingsInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }


    @Test
    public void MaxiSavingsAccountReturnsCorrectValue_Single_Transaction() {

        MaxiSavingsInterestNoWithdrawals maxiSavingsInterest = new MaxiSavingsInterestNoWithdrawals(10);

        Transaction transaction1 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1000))
                .build();

        List<Transaction> transactions = Stream.of(transaction1).collect(Collectors.toList());

        BigDecimal expectedInterest = BigDecimal.valueOf(0.13699);
        DollarAmount actualInterest = maxiSavingsInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }



    @Test
    public void MaxiSavingsAccountReturnsCorrectValue_Multiple_Transaction() {

        MaxiSavingsInterestNoWithdrawals maxiSavingsInterest = new MaxiSavingsInterestNoWithdrawals(10);

        Transaction transaction1 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1000))
                .build();

        Transaction transaction2 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .setAmount(DollarAmount.fromDouble(22.5))
                .build();

        Transaction transaction3 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(11))
                .build();

        List<Transaction> transactions = Stream.of(transaction1, transaction2, transaction3).collect(Collectors.toList());

        BigDecimal expectedInterest = BigDecimal.valueOf(0.14158);
        DollarAmount actualInterest = maxiSavingsInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }


    @Test
    public void MaxiSavingsAccountReturnsCorrectValue_Multiple_With_Withdrawal_In_Last_10_Days() {

        MaxiSavingsInterestNoWithdrawals maxiSavingsInterest = new MaxiSavingsInterestNoWithdrawals(10);

        Transaction transaction1 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1000))
                .build();

        Transaction transaction2 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .setAmount(DollarAmount.fromDouble(22.5))
                .build();

        Transaction transaction3 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(11))
                .build();

        Transaction transaction4 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.WITHDRAWAL)
                .setAmount(DollarAmount.fromInt(10))
                .build();

        List<Transaction> transactions = Stream.of(transaction1, transaction2, transaction3, transaction4).collect(Collectors.toList());

        BigDecimal expectedInterest = BigDecimal.valueOf(0.00280);
        DollarAmount actualInterest = maxiSavingsInterest.getInterest(transactions);

        assertEquals(0, expectedInterest.compareTo(actualInterest.getAmount()));
    }

    @Test
    public void MaxiSavingsAccountReturnsCorrectValue_Multiple_With_Withdrawal_In_Last_11_Days() {

        MaxiSavingsInterestNoWithdrawals maxiSavingsInterest = new MaxiSavingsInterestNoWithdrawals(10);

        Transaction transaction1 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(1000))
                .build();

        Transaction transaction2 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .setAmount(DollarAmount.fromDouble(22.5))
                .build();

        Transaction transaction3 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .setAmount(DollarAmount.fromInt(11))
                .build();


        Instant now = Instant.now();
        Instant elevenDaysAgo = now.minus(11, ChronoUnit.DAYS);

        Transaction transaction4 = new Transaction.Builder()
                .setDescription("Test")
                .setType(Transaction.Type.WITHDRAWAL)
                .setAmount(DollarAmount.fromInt(10))
                .setDate(elevenDaysAgo)
                .build();

        List<Transaction> transactions = Stream.of(transaction1, transaction2, transaction3, transaction4).collect(Collectors.toList());

        BigDecimal expectedInterest = BigDecimal.valueOf(0.14021);
        DollarAmount actualInterest = maxiSavingsInterest.getInterest(transactions);

        assertEquals(expectedInterest, actualInterest.getAmount());
    }
}

package com.accenture;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTests {


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// TRANSACTION CREATION VALIDATION LOGIC TESTS ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = Transaction.InvalidTransaction.class)
    public void transactionMustHaveADescription() {

        new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(100))
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }

    @Test(expected = Transaction.InvalidTransaction.class)
    public void desciprionMustNotBeNull() {

        new Transaction.Builder()
                .setDescription(null)
                .setAmount(DollarAmount.fromInt(100))
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }

    @Test(expected = Transaction.InvalidTransaction.class)
    public void descriptionMustBeNotBeAnEmptyString() {

        new Transaction.Builder()
                .setDescription("")
                .setAmount(DollarAmount.fromInt(100))
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }

    @Test(expected = Transaction.InvalidTransaction.class)
    public void descriptionMustBeGreaterThan1Character() {

        new Transaction.Builder()
                .setDescription("a")
                .setAmount(DollarAmount.fromInt(100))
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }

    @Test
    public void descriptionCanBeTwoCharacters() {

        new Transaction.Builder()
                .setDescription("ab")
                .setAmount(DollarAmount.fromInt(100))
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }

    @Test(expected = Transaction.InvalidTransaction.class)
    public void dollarAmountMustBeSet() {

        new Transaction.Builder()
                .setDescription("ab")
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }


    @Test(expected = Transaction.InvalidTransaction.class)
    public void dollarAmountMustNotBeNull() {

        new Transaction.Builder()
                .setAmount(null)
                .setDescription("ab")
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }


    @Test(expected = Transaction.InvalidTransaction.class)
    public void dollarAmountMustNotBeNegative() {

        new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(-100))
                .setDescription("ab")
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }


    @Test(expected = Transaction.InvalidTransaction.class)
    public void dollarAmountMustBeGreaterThan0() {

        new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(0))
                .setDescription("ab")
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }

    @Test()
    public void dollarAmountMustBeGreaterThanZero() {

        new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(1))
                .setDescription("ab")
                .setType(Transaction.Type.DEPOSIT)
                .build();
    }


    @Test(expected = Transaction.InvalidTransaction.class)
    public void typeMustBeSet() {

        new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(1))
                .setDescription("ab")
                .build();
    }

    @Test(expected = Transaction.InvalidTransaction.class)
    public void typeMustNotBeNull() {

        new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(1))
                .setDescription("ab")
                .setType(null)
                .build();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// TRANSACTION CREATION RETURN RESULT TESTS ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void builderReturnsCorrectTransaction_Deposit() {
        Transaction transaction = new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(125))
                .setDescription("Test")
                .setType(Transaction.Type.DEPOSIT)
                .build();

        assertEquals(Transaction.Type.DEPOSIT, transaction.getType());
        assertEquals("Test", transaction.getDescription());
        assertEquals(DollarAmount.fromInt(125), transaction.getAmount());
    }

    @Test
    public void builderReturnsCorrectTransaction_Interest() {
        Transaction transaction = new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(125))
                .setDescription("Test")
                .setType(Transaction.Type.INTEREST)
                .build();

        assertEquals(Transaction.Type.INTEREST, transaction.getType());
        assertEquals("Test", transaction.getDescription());
        assertEquals(DollarAmount.fromInt(125), transaction.getAmount());
    }

    @Test
    public void builderReturnsCorrectTransaction_Withdrawal() {
        Transaction transaction = new Transaction.Builder()
                .setAmount(DollarAmount.fromInt(125))
                .setDescription("Test")
                .setType(Transaction.Type.WITHDRAWAL)
                .build();

        assertEquals(Transaction.Type.WITHDRAWAL, transaction.getType());
        assertEquals("Test", transaction.getDescription());
        assertEquals(DollarAmount.fromInt(125), transaction.getAmount());
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////// TRANSACTION SUM HELPER FUNCTION TESTS ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void TransactionsSumReturnsCorrectValue_1_DEPOSIT() {

        DollarAmount amount = DollarAmount.fromInt(100);
        Transaction transaction = new Transaction.Builder().setDescription("Test").setAmount(amount)
                .setType(Transaction.Type.DEPOSIT).build();


        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);


        DollarAmount expectedResult = amount;
        DollarAmount actualResult = Transaction.sum(transactionList);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void TransactionsSumReturnsCorrectValue_1_WITHDRAWAL() {

        DollarAmount amount = DollarAmount.fromInt(100);
        Transaction transaction = new Transaction.Builder().setDescription("Test").setAmount(amount)
                .setType(Transaction.Type.WITHDRAWAL).build();


        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);


        DollarAmount expectedResult = DollarAmount.fromInt(-100);
        DollarAmount actualResult = Transaction.sum(transactionList);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void TransactionsSumReturnsCorrectValue_1_INTEREST() {

        DollarAmount amount = DollarAmount.fromInt(100);
        Transaction transaction = new Transaction.Builder().setDescription("Test").setAmount(amount)
                .setType(Transaction.Type.INTEREST).build();


        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);


        DollarAmount expectedResult = amount;
        DollarAmount actualResult = Transaction.sum(transactionList);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void TransactionsSumReturnsCorrectValue_Multiple() {

        Transaction transaction = new Transaction.Builder()
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

        List<Transaction> transactionList = Stream.of(transaction, transaction2, transaction3, transaction4).collect(Collectors.toList());

        DollarAmount expectedResult = DollarAmount.fromDouble(51.2);
        DollarAmount actualResult = Transaction.sum(transactionList);

        assertEquals(expectedResult, actualResult);

    }

}

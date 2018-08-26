package com.abc.transaction;

import com.abc.util.ZeroAmountException;

/**
 * Created by sameen on 26/08/2018.
 */
public class TransactionFactory {

    public TransactionFactory() {
    }

    /**
     *
     * @param type
     * @param amount
     * @return
     */
    public Transaction createTransaction(TransactionType type, double amount) {
        Transaction transaction = null;
        try {
            switch (type) {
                case CREDIT:
                    transaction = new CreditTransaction(type, amount);
                    break;
                case DEBIT:
                    transaction = new DebitTransaction(type, amount);
                    break;
            }
        } catch (ZeroAmountException e) {
            System.err.println("Transaction value must be 0.01 or above!");
        }
        return transaction;
    }
}

package com.accenture;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class Transaction {

    public enum Type {
        DEPOSIT, WITHDRAWAL, INTEREST,
    }

    private final String id;
    private final DollarAmount amount; // Is validated to be positive, non-zero
    private final String description;
    private final Instant transactionDate;
    private final Type type;

    // Can only be constructed through the Builder class
    private Transaction(DollarAmount amount, String description, Type type, Instant date) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.transactionDate = date;
    }


    public DollarAmount getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public Type getType() {
        return type;
    }


    // Helper methods for acquiring the effective dollar sum of a list of transactions.
    public static DollarAmount sum(List<Transaction> transactions) {
        return transactions.stream().map(transaction -> {
            DollarAmount amount = transaction.amount;
            if (transaction.getType() == Type.WITHDRAWAL)
                return amount.negate();
            else
                return amount;
        })
                .reduce(DollarAmount.fromInt(0), DollarAmount::plus);
    }


    @Override
    public String toString() {
        return "Transaction Type: " + type.name() + "  Amount:"  + amount.toString();
    }

    public static final class Builder {

        private DollarAmount amount; // Is validated to be positive, non-zero
        private String description;
        private Type type;
        private Instant date = Instant.now();


        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAmount(DollarAmount amount) {
            this.amount = amount;
            return this;
        }


        public Builder setType(Type type) {
            this.type = type;
            return this;
        }


        public Builder setDate(Instant date) {
            this.date = date;
            return this;
        }

        public Transaction build() {
            validateTransactionType();
            validateAmount();
            validateDescription();
            validateDate();
            return new Transaction(amount,description, type, date);
        }

        private void validateTransactionType() {
            if (type == null) {
                throw new InvalidTransaction("Transaction type must be set");
            }
        }


        private void validateAmount() {
            if (amount == null || amount.isMinus() || amount.isZero()) {
                throw new InvalidTransaction("Dollar amount must be non-null and greater than 0");
            }
        }


        private void validateDescription() {
            if (description == null || description.length() <= 1)
                throw new InvalidTransaction("Description of request must be set and be more than 1 character");
        }

        private void validateDate() {
            if (date == null)
                throw new InvalidTransaction("Date must be set and not null");
        }

        @Override
        public String toString() {
            return "Builder for Transaction";
        }
    }




    public static class InvalidTransaction extends RuntimeException {
        public InvalidTransaction() {
        }

        public InvalidTransaction(String message) {
            super(message);
        }

        public InvalidTransaction(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidTransaction(Throwable cause) {
            super(cause);
        }

        public InvalidTransaction(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

}

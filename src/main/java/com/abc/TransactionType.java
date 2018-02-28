package com.abc;

public enum TransactionType {
    DEPOSIT("deposit"), WITHDRAW("withdrawal"), INTEREST("interest");

    private final String reprensentation;   // in kilograms

    TransactionType(String reprensentation) {
        this.reprensentation = reprensentation;
    }

    @Override
    public String toString() {
        return reprensentation;
    }
}

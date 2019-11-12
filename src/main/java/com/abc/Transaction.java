package com.abc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@AllArgsConstructor
public class Transaction {

    @NonNull
    private final Double amount;
    @NonNull
    private LocalDateTime transactionDateTime;

    public Transaction(@NonNull Double amount) {
        this.amount = amount;
        this.transactionDateTime = now();
    }
}

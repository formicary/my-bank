package com.abc.account;

import com.abc.transaction.Transaction;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Account {
    @NonNull
    private AccountType accountType;

    private List<Transaction> transactions = new ArrayList<>();
}

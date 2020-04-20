package com.abc.transaction;

import com.abc.util.DateProvider;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class Transaction {

    @NonNull
    private  TransactionType transactionType;

    @NonNull
    private double amount;

    private Date transactionDate = DateProvider.getInstance().getCurrentDate();
}

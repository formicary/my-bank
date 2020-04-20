package com.abc.customer;

import com.abc.account.Account;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Customer {

    @NonNull
    private String name;

    private List<Account> accounts = new ArrayList<>();
}

package com.abc.utils;

import com.abc.core.Customer;

public class BankUtils {

    public static final String ACCOUNT = "account";
    public static final String CUSTOMER_SUMMARY = "Customer Summary";

    public static String formatCustomer(Customer customer) {
        var numAccounts = customer.getNumberOfAccounts();
        var accountsFormatted = String.format("%d %s", numAccounts, numAccounts == 1 ? ACCOUNT : ACCOUNT + "s");

        return String.format("\n - %s (%s)", customer.getName(), accountsFormatted);
    }

}

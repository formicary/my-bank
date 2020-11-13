package com.abc.core.bank;

import com.abc.core.customer.Customer;
import com.abc.core.account.Transaction;

import static java.lang.Math.abs;

public class BankUtils {

    public static final String ACCOUNT = "account";
    public static final String CUSTOMER_SUMMARY = "Customer Summary";

    public static String formatCustomer(Customer customer) {
        var numAccounts = customer.getNumberOfAccounts();
        var accountsFormatted = String.format("%d %s", numAccounts, numAccounts == 1 ? ACCOUNT : ACCOUNT + "s");

        return String.format("\n - %s (%s)", customer.getName(), accountsFormatted);
    }

    public static String formatAmount(double amount){
        return String.format("$%,.2f", abs(amount));
    }

    public static String formatTransaction(Transaction transaction) {
        return String.format("  %s %s\n",
                transaction.getAmount() > 0 ? "deposit" : "withdrawal",
                BankUtils.formatAmount(transaction.getAmount()));
    }

}

package com.abc;

import lombok.Getter;
import lombok.NonNull;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Getter
public class Bank {

    public static final DateTimeFormatter DATE = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    public static final DateTimeFormatter DATETIME = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    public static final NumberFormat PERCENT = NumberFormat.getPercentInstance(Locale.US);
    public static final NumberFormat DOLLAR = NumberFormat.getCurrencyInstance();

    static {
        DATE.withLocale(Locale.US);
        DATETIME.withLocale(Locale.US);
        PERCENT.setMinimumFractionDigits(1);
        DOLLAR.setCurrency(Currency.getInstance("USD"));
        DOLLAR.setMinimumFractionDigits(2);
    }

    @NonNull
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(@NonNull Customer customer) {
        customers.add(customer);
    }

    public String printCustomerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - " + c.getName() + " (" + c.getNumberOfAccounts() +  " account" + (c.getNumberOfAccounts() == 1 ? "" : "s" ) + ")");
        return summary.toString();
    }

    public Double getTotalInterestPaid() {
        return customers.stream().mapToDouble(Customer::getTotalInterestEarned).sum();
    }

}

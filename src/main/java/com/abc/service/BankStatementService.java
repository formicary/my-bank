package com.abc.service;

import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.exception.InputValidator;

public class BankStatementService {

    public static String bankCustomerReport(Bank bank) {
        InputValidator.validateBank(bank);
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer customer : bank.getCustomers())
            summary .append("\n - " + customer.getName() + " (" + format(customer.getAccounts().size(), "account") + ")");
        return summary.toString();
    }

    private static String format(int number, String word) {

        return number + " " + (number == 1 ? word : word + "s");
    }
}
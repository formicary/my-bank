package com.abc.service;

import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.exception.InputValidator;

/**
 * Bank statement service to generate reports on customers for the bank
 * @author aneesh
 */
public class BankStatementService {

    /**
     * return a bank report of each customer within the bank and the number of accounts they hold
     * @param bank bank to generate a report of
     * @return a report of the customers and number of accounts they have with the bank
     */
    public static String bankCustomerReport(Bank bank) {
        InputValidator.validateBank(bank);
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer customer : bank.getCustomers())
            summary.append("\n - " + customer.getName() + " (" + format(customer.getAccounts().size(), "account") + ")");
        return summary.toString();
    }

    private static String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
}
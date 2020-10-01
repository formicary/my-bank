package com.abc.service;

import com.abc.entity.Bank;
import com.abc.entity.impl.CustomerImpl;

public class ReportGenerator {

    public String bankCustomerSummary(Bank bank) {
        String summary = "Customer Summary";
        for (CustomerImpl c : bank.getCustomers())
            summary += "\n - " + c.getName() + " (" + format(c.getAccounts().size(), "account") + ")";
        return summary;
    }

    private String format(int number, String word) {

        return number + " " + (number == 1 ? word : word + "s");
    }
}
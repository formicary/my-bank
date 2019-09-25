package com.abc.users;

import com.abc.Bank;

import static com.abc.util.StringFormatter.numbOfAccountsFormat;

/**
 * @project MyBank
 */
public class Manager extends User {

    private Bank bank;

    public Manager(String name, Bank bank) {
        super(name);
        this.bank = bank;
    }

    public String getCustomerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : bank.getCustomers())
            summary.append("\n - ").append(c.getName())
                    .append(" (").append(numbOfAccountsFormat(c.getNumberOfAccounts(), "account")).append(")");
        return summary.toString();
    }

    public double getTotalInterestPaid() {
        double total = 0;
        for(Customer c: bank.getCustomers())
            total += c.totalInterestEarned();
        return total;
    }
}

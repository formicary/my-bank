package com.abc;

import java.util.*;

public class Bank {

    private List<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<Customer>();

        // Use a timer with a scheduled operation that will start at the next midnight and
        // repeat every day to add interest to all of the accounts in the bank
        Timer dailyTimer = new Timer();
        dailyTimer.scheduleAtFixedRate(new DailyInterestTask(), Utils.getTomorrowMidnight(), 24 * 60 * 60 * 1000);
    }

    public void addCustomer(Customer newCustomer) {
        this.customers.add(newCustomer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder();

        summary.append("Customer Summary:");
        for (Customer c : this.customers) {
            summary.append("\n - ").append(c.toString());
        }

        return summary.toString();
    }

    public double totalInterestPaid() {
        double total = 0.0;

        for (Customer c : this.customers) {
            total += c.totalInterestEarned();
        }

        return total;
    }

    public Customer getFirstCustomer() throws IndexOutOfBoundsException {
        if (this.customers.isEmpty()) {
            throw new IndexOutOfBoundsException("No customers found");
        } else {
            return this.customers.get(0);
        }
    }

    // The scheduled task that will run every day and will add interest to all customer accounts
    private class DailyInterestTask extends TimerTask {
        @Override
        public void run() {
            for (Customer c : Bank.this.customers) {
                for (Account a : c.getAccountsList()) {
                    a.addInterest();
                }
            }
        }
    }
}

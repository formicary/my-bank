package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Customer> customers;
    public static long customerId = 0;
    public static long accountNumber = 0;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customer.setCustomerId(customerId++);
        customers.add(customer);
    }

    public void accountSetup(Account account, Customer customer) {
        accountNumber ++;
        account.setAccountNumber(accountNumber);
        customer.openAccount(account);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     * @param number
     * @param word
     * @return
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public boolean doesCustomerHaveAccount(String name) {
        for(Customer c: customers) {
            if (c.getName() == name) {
                return true;
            }
        }
        return false;
    }
}

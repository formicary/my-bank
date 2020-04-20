package com.abc.bank;

import com.abc.customer.Customer;
import com.abc.customer.CustomerService;

import java.util.List;

public class BankService {

    private static BankService instance;
    private CustomerService customerService;

    public BankService() {
        customerService = CustomerService.getInstance();
    }

    /**
     * getInstance
     * @return
     */
    public static synchronized BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }

        return instance;
    }

    /**
     * addCustomer
     * @param bank
     * @param customer
     */
    public void addCustomer(Bank bank, Customer customer) {
        bank.getCustomers().add(customer);
    }

    /**
     * customerSummary
     * @param bank
     * @return
     */
    public String customerSummary(Bank bank) {
        String summary = "Customer Summary";
        for (Customer customer : bank.getCustomers()) {
            int numberOfAccounts = customerService.getNumberOfAccounts(customer);
            summary += "\n - " + customer.getName() + " (" + format(numberOfAccounts, "account") + ")";
        }
        return summary;
    }

    /**
     * format
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     *
     * @param number
     * @param word
     * @return
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * totalInterestPaid
     * @param bank
     * @return
     */
    public double totalInterestPaid(Bank bank) {
        double total = 0;
        for (Customer customer : bank.getCustomers()) {
            total += customerService.totalInterestEarned(customer);
        }
        return total;
    }

    /**
     * getFirstCustomerName
     * @param bank
     * @return
     */
    public String getFirstCustomerName(Bank bank) {
        List<Customer> customers = bank.getCustomers();
        if (customers.isEmpty() == false) {
            return customers.get(0).getName();
        } else {
            //log.warn("The bank has no customers");
            return null;
        }
    }
}

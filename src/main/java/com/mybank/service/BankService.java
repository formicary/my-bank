package com.mybank.service;

import com.mybank.entity.Bank;
import com.mybank.entity.Customer;
import com.mybank.util.FormatHelper;

import java.text.DecimalFormat;

public class BankService {

    private CustomerService customerService = new CustomerService();

    /**
     *
     * @param bank
     * @param customer
     */
    public void addCustomer(Bank bank, Customer customer) {
        bank.getCustomers().add(customer);
    }

    /***
     *
     * @param bank
     * @return
     */
    public String customerSummary(Bank bank) {
        String summary = "Customer Summary";
        for (Customer c : bank.getCustomers()) {
            summary += "\n - " + c.getName() + " (" + FormatHelper.format(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
    }

    /***
     *
     * @param bank
     * @return
     */
    public double totalInterestPaid(Bank bank) {
        double total = 0;
        for(Customer c: bank.getCustomers()) {
            total += customerService.totalInterestEarned(c);
        }
        return total;
    }

    /***
     *
     * @param bank
     * @return
     */
    public String totalInterestPaidReport(Bank bank) {
        double total = totalInterestPaid(bank);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        return "Total interest paid: " + decimalFormat.format(total);
    }

    /**
     *
     * @param bank
     * @return
     */
    public String getFirstCustomer(Bank bank) {
        if(bank.getCustomers().size() == 0) {
            return "No customers registered";
        }
        return bank.getCustomers().get(0).getName();
    }
}

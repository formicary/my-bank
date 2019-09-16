package com.abc.report;

import com.abc.account.Account;
import com.abc.account.interest.InterestRule;
import com.abc.customer.Customer;
import com.abc.Money;

import java.util.ArrayList;

public class InterestReport {

    private ArrayList<Customer> customers;

    public InterestReport(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    /**
     * @return total interest paid to all customers
     */
    public Money totalInterestPaid() {
        Money total = new Money("0");
        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                for (InterestRule r : a.getInterestRules()){
                    total = total.add(r.calculateInterest(a));
                }
            }
        }
        return total;
    }
}

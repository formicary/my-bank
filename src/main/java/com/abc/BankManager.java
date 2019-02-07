package com.abc;

import java.math.BigDecimal;
import java.util.List;

public class BankManager {
   private String name;
   private Bank bank;

    public BankManager(String name, Bank bank) {
       this.name = name;
        this.bank = bank;
    }
    
    public String getName() {
    	return name;
    }
  

    public String customerSummaryReport() {
    	List<Customer> customers = bank.getCustomerList();
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + addSIfMoreThanOne(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }


    private String addSIfMoreThanOne(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }


    public String totalInterestPaidReport() {
    	List<Customer> customers = bank.getCustomerList();
        BigDecimal total = new BigDecimal("0.00");
        for(Customer c: customers)
            total =total.add(c.totalInterestEarned());
        return "Total interest paid by the bank: $"+ total.toString();
    }


}

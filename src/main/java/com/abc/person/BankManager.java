package com.abc.person;

import com.abc.constants.AccountConstantsNew;
import com.abc.organisation.Customers;

import java.util.ArrayList;

/**
 * Created by vahizan on 09/08/2017.
 */
public class BankManager extends Employee{
    private Customers customerReport;
    private Name name;
    public BankManager(Name name, Customers customerReport) {
        super(name);
        this.customerReport=customerReport;
    }
    public String customerReport(){
        return customerReport.report();
    }
    public String interestPaidReport(){
        StringBuilder sb = new StringBuilder();
        for(AccountConstantsNew accountType:AccountConstantsNew.values()){
               sb.append(customerReport.interestReport(accountType)).append("\n");
        }
        return sb.toString();
    }
    public Name name() {
        return name;
    }
}

package com.abc;

import java.util.UUID;

/*This class implements a com.abc.Maxi_Savings_Account for the bank application
This class creates an account of a specific type for the customer.

 */
public class Maxi_Savings_Account extends  Account{

    private double interestRate;
    private String accountType;


    @Override
    public String toString() {
        return "com.abc.Maxi_Savings_Account{" +
                "interestRate=" + interestRate +
                ", accountType='" + accountType + '\'' +
                '}';
    }

    public Maxi_Savings_Account(Customer customer, double amount, UUID accountId) {
        super(customer, amount, accountId);
        this.interestRate = interestRate;
        this.accountType = "com.abc.Maxi_Savings_Account";
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class Customer {
    private String name; //
    private Map<String, Account> accountMap; // Does this make sense? Is this a sensible way of retrieving accounts? YOu have to scan the list?
    private final String emailAddress; // ReadOnly email address for unique account ID.

    Customer(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.accountMap = new HashMap<>();
    }

    final String getName() {
        return name;
    }

    final String getEmailAddress(){
        return this.emailAddress;
    }

    Customer addAccount(Account account) {
        if(accountMap.containsKey(account.getAccountType())){
            throw new IllegalArgumentException("Customer ID " + emailAddress + " tried to add a duplicate " +
                    account.getAccountType() + ".");
        }
        accountMap.put(account.getAccountType(),account);
        account.setCustomerID(emailAddress);
        return this;
    }
    int getNumberOfAccounts() {
        return accountMap.size();
    }

     BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Account a : accountMap.values())
            total = total.add(a.getInterestAccrued());
        return total;
    }
    void transferBetweenAccounts(Account transferFrom, Account transferTo, BigDecimal amountToTransfer){ //why always 50?
        if(transferFrom.getAccountBalance().compareTo(amountToTransfer) < 0){
            throw new IllegalArgumentException("Customer ID " + emailAddress + " tried to transfer money out of a "
                    + transferFrom.getAccountType() + " but didn't have sufficient funds for the transaction.");
        }
        if(amountToTransfer.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Customer ID " + emailAddress + " tried to transfer a negative or nil" +
                    " quantity from their " + transferFrom.getAccountType() + " to their "
                    + transferTo.getAccountType());
        }
        if(!(accountMap.containsValue(transferFrom) && accountMap.containsValue(transferTo))){
            throw new IllegalArgumentException("Customer ID " + emailAddress + " tried to transfer money to or from an " +
                    "account that they have not opened.");
        }
        else{
            transferFrom.outwardTransfer(transferTo, amountToTransfer);
            transferTo.inwardTransfer(transferFrom, amountToTransfer);
        }
    }
    String getTotalStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        BigDecimal total = BigDecimal.valueOf(0);
        for (Account account : accountMap.values()) {
            statement.append("\n").append(statementForAccount(account)).append("\n");
            total = total.add(account.getAccountBalance());
        }
        statement.append("\nTotal In All Accounts: ").append(BankUtils.toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account account) {
        return account.stringForAccountStatement();
    }
}

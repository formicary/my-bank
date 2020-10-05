package com.abc;

import com.abc.entity.*;
import com.abc.entity.impl.AccountImpl;
import com.abc.entity.impl.BankImpl;
import com.abc.entity.impl.CustomerImpl;
import com.abc.entity.impl.TransactionImpl;
import com.abc.util.AnnualInterestCalculator;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        runDemo();
    }

    private static void runDemo() {

        System.out.println("Creating bank...");
        Bank bank = new BankImpl();
        System.out.println("Bank created.");

        System.out.println("Creating customers...");
        Customer customer = new CustomerImpl("Alice");
        Customer customer2 = new CustomerImpl("Bob");
        System.out.println("Customer created.");

        bank.addCustomer(customer);
        bank.addCustomer(customer2);
        Account account1 = new AccountImpl(customer, AccountType.CURRENT, "12345");
        Account account2 = new AccountImpl(customer, AccountType.SAVINGS, "12346");
        Account account3 = new AccountImpl(customer, AccountType.MAXI_SAVINGS, "12347");
        Account account4 = new AccountImpl(customer, AccountType.MAXI_SAVINGS_ADD, "12348");
        Account account5 = new AccountImpl(customer2, AccountType.MAXI_SAVINGS, "12348");

        System.out.println("Customer has added 3 accounts:");
        for(Account account : customer.getAccounts().values()){
            System.out.println(account);
        }

        Transaction largeDeposit = new TransactionImpl(new BigDecimal("1000"));
        Transaction midDeposit = new TransactionImpl(new BigDecimal("500"));
        Transaction smallDeposit = new TransactionImpl(new BigDecimal("100"));
        Transaction midWithdrawal = new TransactionImpl(new BigDecimal("-500"));
        Transaction smallWithdrawal = new TransactionImpl(new BigDecimal("-100"));

        account1.addTransaction(largeDeposit);
        account2.addTransaction(smallDeposit);
        account3.addTransaction(midDeposit);
        account4.addTransaction(largeDeposit);
        account1.addTransaction(midDeposit);
        account1.addTransaction(midDeposit);
        account1.addTransaction(midWithdrawal);
        account2.addTransaction(smallWithdrawal);
        account5.addTransaction(largeDeposit);
        account5.addTransaction(largeDeposit);

        System.out.println("Customer has made transactions:");

        for(Account account : customer.getAccounts().values()){
            for(Transaction transaction : account.getTransactions()){
                System.out.println(transaction);
            }
        }

        System.out.println("Annual interest to be earned by customer provided no further transactions:");
        System.out.println(AnnualInterestCalculator.interestEarnedByCustomer(customer));
        System.out.println(AnnualInterestCalculator.interestEarnedByCustomer(customer2));

        System.out.println("Annual interest to be paid by the bank:");
        System.out.println(AnnualInterestCalculator.totalInterestPaidByBank(bank));

    }

    private static void setUpAccountsWithCustomer(Customer customer) {

    }
}

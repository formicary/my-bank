package com.accenture;

import com.accenture.accounts.Account;
import com.accenture.accounts.AccountFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Immutable class
public class Customer {

    // Account validation constants
    private static final int MINIMUM_AGE_OF_CUSTOMER = 18;
    private static final int MAXIMUM_AGE_OF_CUSTOMER = 120;
    private static final int MAXIMUM_NUMBER_OF_ACCOUNTS = 20;

    private final String id;
    private final String forename;
    private final String surname;
    private final int age;
    private final Instant timeCreated;
    private final List<Account> accounts;

    // Can only be constructed through the builder class
    private Customer(String id, String forename, String surname, int age, List<Account> accounts, Instant timeCreated) {
        this.id = id;
        this.timeCreated = timeCreated;
        this.forename = forename;
        this.surname = surname;
        this.age = age;
        this.accounts = accounts;
    }

    public String getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String openAccount(AccountFactory.AccountType accountType) {
        Account account = AccountFactory.createAccount(accountType, id);
        accounts.add(account);
        return account.getId();
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public DollarAmount getTotalInterestPaidToCustomer() {
        return accounts.stream()
                .map(Account::getTotalInterestPaid)
                .reduce(DollarAmount.fromInt(0), DollarAmount::plus);
    }

    public String generateStatement() {
        StringBuilder statement = new StringBuilder();

        statement.append("Statement for ").append(forename).append(" ").append(surname).append("\n");
        DollarAmount total = DollarAmount.fromInt(0);

        for (Account account : accounts) {
            total = total.plus(account.getAccountBalance());
            statement.append(account.generateStatement(false)).append("\n");
        }
        statement.append("\nTotal in all accounts: ").append(total);
        return statement.toString();
    }


    @Override
    public String toString() {
        StringBuilder currentString = new StringBuilder();
        currentString.append(forename).append(" ").append(surname).append(" (").append(format(getNumberOfAccounts(), "account")).append(")");
        return currentString.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }


    // Builder for Customer objects so that they are correctly validated and initialized
    public static class Builder {

        private String id;
        private String forename;
        private String surname;
        private int age;
        private List<Account> accounts = new ArrayList<>();
        private Instant timeCreated = Instant.now();

        public Builder() {
            id = UUID.randomUUID().toString();
        }

        public Builder(Customer customer) {
            id = customer.id;
            setForename(customer.forename);
            setSurname(customer.surname);
            setAge(customer.age);
            setAccounts(customer.accounts);
            timeCreated = customer.timeCreated;
        }

        public Builder setForename(String forename) {
            this.forename = forename;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setAccounts(List<Account> accounts) {
            this.accounts = new ArrayList<>();
            this.accounts.addAll(accounts);
            return this;
        }

        public Customer build() {
            validateName(forename);
            validateName(surname);
            validateAge(age);
            validateAccounts(accounts);
            return new Customer(id, forename, surname, age, accounts, timeCreated);
        }

        private void validateAge(int age) {
            if (age < MINIMUM_AGE_OF_CUSTOMER || age > MAXIMUM_AGE_OF_CUSTOMER) {
                throw new InvalidCustomerConfigurationException("age must be between: " + MINIMUM_AGE_OF_CUSTOMER + " and " + MAXIMUM_AGE_OF_CUSTOMER);
            }
        }

        private void validateName(String forename) {
            if (forename == null || forename.length() <= 1 || forename.length() > 30)
                throw new InvalidCustomerConfigurationException("forename must be set and be greater than 1 character but less than 30");
        }

        private void validateAccounts(List<Account> accounts) {
            if (accounts == null || accounts.size() > MAXIMUM_NUMBER_OF_ACCOUNTS)
                throw new InvalidCustomerConfigurationException("accounts must be set and be no larger than " + MAXIMUM_NUMBER_OF_ACCOUNTS + " in size");
        }

        @Override
        public String toString() {
            return "Builder for Customer objects";
        }
    }

    public static class InvalidCustomerConfigurationException extends RuntimeException {
        public InvalidCustomerConfigurationException() {
            super();
        }

        public InvalidCustomerConfigurationException(String s) {
            super(s);
        }

        public InvalidCustomerConfigurationException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public InvalidCustomerConfigurationException(Throwable throwable) {
            super(throwable);
        }
    }


}